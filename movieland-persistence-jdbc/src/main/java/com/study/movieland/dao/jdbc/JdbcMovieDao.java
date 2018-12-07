package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieDetailedRowMapper;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.dao.jdbc.sql.SqlGenerator;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final String GET_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, m.description, p.poster_url, ARRAY(SELECT mg.genre_id FROM movie_genre mg WHERE mg.movie_id = m.id) genre_ids, ARRAY(SELECT mc.country_id FROM movie_country mc WHERE mc.movie_id = m.id) country_ids FROM movie m LEFT JOIN poster p ON p.movie_id = m.id WHERE m.id = :movieId";
    private static final String CREATE_SQL = "WITH m AS (INSERT INTO movie(name, original_name, year, price, description) VALUES (:nameRussian, :nameNative, :yearOfRelease, :price, :description) RETURNING id movie_id), c AS (INSERT INTO movie_country(movie_id, country_id) SELECT m.movie_id, UNNEST(:countryIds) FROM m), g AS (INSERT INTO movie_genre(movie_id, genre_id) SELECT m.movie_id, UNNEST(:genreIds) FROM m) INSERT INTO poster(movie_id, poster_url) SELECT m.movie_id, :posterUrl FROM m";
    private static final String UPDATE_SQL = "WITH cd AS (DELETE FROM movie_country WHERE movie_id = :movieId), gd AS (DELETE FROM movie_genre WHERE movie_id = :movieId), c AS (INSERT INTO movie_country(movie_id, country_id) SELECT :movieId, UNNEST(:countryIds) ), g AS (INSERT INTO movie_genre(movie_id, genre_id) SELECT :movieId, UNNEST(:genreIds) ), p AS (UPDATE poster SET poster_url = :posterUrl WHERE movie_id = :movieId) UPDATE movie SET name = :nameRussian, original_name = :nameNative WHERE id = :movieId";
    private static final String GET_ALL_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id";
    private static final String GET_RANDOM_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id ORDER BY RANDOM() LIMIT :count";
    private static final String GET_BY_GENRE_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, p.poster_url FROM movie m JOIN movie_genre mg ON mg.movie_id = m.id LEFT JOIN poster p ON p.movie_id = m.id WHERE mg.genre_id = :genreId";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final MovieDetailedRowMapper MOVIE_DETAILED_ROW_MAPPER = new MovieDetailedRowMapper();

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SqlGenerator sqlGenerator;

    @Override
    public Movie get(int id) {
        return jdbcTemplate.queryForObject(GET_SQL
                , new MapSqlParameterSource().addValue("movieId", id)
                , MOVIE_DETAILED_ROW_MAPPER
        );
    }

    @Override
    public void create(Movie movie) {
        jdbcTemplate.update(CREATE_SQL, new MapSqlParameterSource()
                .addValue("nameRussian", movie.getNameRussian())
                .addValue("nameNative", movie.getNameNative())
                .addValue("yearOfRelease", movie.getYearOfRelease())
                .addValue("price", movie.getPrice())
                .addValue("description", movie.getDescription())
                .addValue("posterUrl", movie.getPicturePath())
                .addValue("genreIds", getGenreIds(movie.getGenres()))
                .addValue("countryIds", getCountryIds(movie.getCountries()))
        );
    }

    @Override
    public void update(Movie movie) {
        jdbcTemplate.update(UPDATE_SQL, new MapSqlParameterSource()
                .addValue("movieId", movie.getId())
                .addValue("nameRussian", movie.getNameRussian())
                .addValue("nameNative", movie.getNameNative())
                .addValue("posterUrl", movie.getPicturePath())
                .addValue("genreIds", getGenreIds(movie.getGenres()))
                .addValue("countryIds", getCountryIds(movie.getCountries()))
        );
    }

    @Override
    public Collection<Movie> getAll(RequestParams requestParams) {
        return jdbcTemplate.query(sqlGenerator.getSQL(GET_ALL_SQL, requestParams), MOVIE_ROW_MAPPER);
    }

    @Override
    public Collection<Movie> getRandom(int count) {
        return jdbcTemplate.query(GET_RANDOM_SQL
                , new MapSqlParameterSource().addValue("count", count)
                , MOVIE_ROW_MAPPER
        );
    }

    @Override
    public Collection<Movie> getByGenre(int genreId, RequestParams requestParams) {
        return jdbcTemplate.query(sqlGenerator.getSQL(GET_BY_GENRE_SQL, requestParams)
                , new MapSqlParameterSource().addValue("genreId", genreId)
                , MOVIE_ROW_MAPPER
        );
    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setSqlGenerator(SqlGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }

    private int[] getCountryIds(Collection<Country> countries) {
        int[] countryIds = new int[countries.size()];
        int idx = 0;
        for (Country country : countries) {
            countryIds[idx++] = country.getId();
        }
        return countryIds;
    }

    private int[] getGenreIds(Collection<Genre> genres) {
        int[] genreIds = new int[genres.size()];
        int idx = 0;
        for (Genre genre : genres) {
            genreIds[idx++] = genre.getId();
        }
        return genreIds;
    }
}