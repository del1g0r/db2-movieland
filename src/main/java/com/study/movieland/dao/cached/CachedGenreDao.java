package com.study.movieland.dao.cached;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("cachedGenreDao")
public class CachedGenreDao implements GenreDao {

    private GenreDao genreDao;
    private int cachePeriod;
    LocalDateTime refreshTime = LocalDateTime.MIN;
    List<Genre> genres;

    @Override
    public List<Genre> getAll() {
        if (refreshTime.isBefore(LocalDateTime.now())) {
            synchronized (this) {
                if (refreshTime.isBefore(LocalDateTime.now())) {
                    genres = genreDao.getAll();
                    refreshTime = LocalDateTime.now().plusMinutes(cachePeriod);
                }
            }
        }
        return genres;
    }

    @Autowired
    @Qualifier("jdbcGenreDao")
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Value("${dao.genre.cachePeriod:240}")
    public void setCachePeriod(int cachePeriod) {
        this.cachePeriod = cachePeriod;
    }
}