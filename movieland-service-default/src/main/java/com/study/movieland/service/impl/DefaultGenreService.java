package com.study.movieland.service.impl;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultGenreService implements GenreService {

    private GenreDao genreDao;

    @Override
    public int[] getIds(Collection<Genre> genres) {
        int[] ids = new int[genres.size()];
        int id = 0;
        for (Genre genre : genres) {
            ids[id++] = genre.getId();
        }
        return ids;
    }

    @Override
    public Genre get(int id) {
        return genreDao.get(id);
    }

    @Override
    public Collection<Genre> getSome(int[] ids) {
        return genreDao.getSome(ids);
    }

    @Override
    public Collection<Genre> getAll() {
        return genreDao.getAll();
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}
