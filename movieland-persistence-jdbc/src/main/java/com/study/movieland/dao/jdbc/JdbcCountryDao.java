package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.study.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbcCountryDao implements CountryDao {

    private static final String GET_SQL = "SELECT c.id, c.name FROM country c WHERE c.id = ?";
    private static final String GET_SOME_SQL = "SELECT c.id, c.name FROM country c WHERE c.id IN ( SELECT UNNEST(?) ) ORDER BY c.name";
    private static final String GET_ALL_SQL = "SELECT c.id, c.name FROM country c ORDER BY c.name";
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public Country get(int id) {
        return jdbcTemplate.queryForObject(GET_SQL, COUNTRY_ROW_MAPPER, id);
    }

    public Collection<Country> getSome(int[] ids) {
        return jdbcTemplate.query(GET_SOME_SQL, COUNTRY_ROW_MAPPER, ids);
    }

    @Override
    public Collection<Country> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, COUNTRY_ROW_MAPPER);
    }

    @Override
    public Collection<Country> enrich(Collection<Country> countries) {
        int[] ids = new int[countries.size()];
        int idx = 0;
        for (Country country : countries) {
            ids[idx++] = country.getId();
        }
        return getSome(ids);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}