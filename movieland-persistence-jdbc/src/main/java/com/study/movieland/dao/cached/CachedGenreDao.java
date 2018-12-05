package com.study.movieland.dao.cached;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Primary
public class CachedGenreDao implements GenreDao {

    private GenreDao genreDao;
    private volatile Map<Integer, Genre> genres;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduled.genreFixedDelay:14400000}", initialDelayString = "${scheduled.genreFixedDelay:14400000}")
    public void refresh() {
        HashMap<Integer, Genre> genres = new HashMap<>();
        for (Genre genre : genreDao.getAll()) {
            genres.put(genre.getId(), genre);
        }
        this.genres = genres;
    }

    @Override
    public Genre get(int id) {
        return genres.get(id);
    }

    @Override
    public Collection<Genre> getAll() {
        return new ArrayList<>(this.genres.values());
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}