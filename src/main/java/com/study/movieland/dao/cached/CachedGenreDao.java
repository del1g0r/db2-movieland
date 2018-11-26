package com.study.movieland.dao.cached;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class CachedGenreDao implements GenreDao {

    private static final int DEFAULT_DELAY = 4 * 60 * 60 * 1000;

    private GenreDao genreDao;
    private volatile List<Genre> genres;

    @PostConstruct
    @Scheduled(fixedDelay = DEFAULT_DELAY, initialDelay = DEFAULT_DELAY)
    public void refresh() {
        this.genres = genreDao.getAll();
    }

    @Override
    public List<Genre> getAll() {
        return new ArrayList<>(this.genres);
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}