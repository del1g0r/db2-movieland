package com.study.movieland.dao;

import com.study.movieland.entity.Genre;
import com.study.movieland.entity.User;

import java.util.ArrayList;
import java.util.Collection;

public interface GenreDao {

    Genre get(int id);

    Collection<Genre> getAll();

    default Genre enrich(Genre genre) {
        return get(genre.getId());
    }

    default Collection<Genre> enrich(Collection<Genre> genres) {
        Collection<Genre> enrichedGenres = new ArrayList<>();
        for (Genre genre : genres) {
            enrichedGenres.add(get(genre.getId()));
        }
        return enrichedGenres;
    }
}