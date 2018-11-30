package com.study.movieland.dao;

import com.study.movieland.entity.Genre;

import java.util.Collection;

public interface GenreDao {

    Genre get(int id);

    Collection<Genre> getAll();

    Collection<Genre> getSome(int[] ids);
}
