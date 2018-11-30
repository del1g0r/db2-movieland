package com.study.movieland.service;

import com.study.movieland.entity.Genre;

import java.util.Collection;

public interface GenreService {

    int[] getIds(Collection<Genre> genres);

    Genre get(int id);

    Collection<Genre> getSome(int[] ids);

    Collection<Genre> getAll();
}
