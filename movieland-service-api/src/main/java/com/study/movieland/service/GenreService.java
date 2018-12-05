package com.study.movieland.service;

import com.study.movieland.entity.Genre;

import java.util.ArrayList;
import java.util.Collection;

public interface GenreService {

    Genre get(int id);

    Collection<Genre> getAll();

    default Genre enrich(Genre genre) {
        return get(genre.getId());
    }

    default Collection<Genre> enrich(Collection<Genre> genres) {
        Collection<Genre> enrichedGenres = new ArrayList<>();
        for (Genre genre : genres) {
            enrichedGenres.add(enrich(genre));
        }
        return enrichedGenres;
    }
}
