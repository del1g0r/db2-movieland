package com.study.movieland.service;

import com.study.movieland.entity.Genre;

import java.util.ArrayList;
import java.util.Collection;

public interface GenreService {

    Genre get(int id);

    Collection<Genre> getAll();

    default Collection<Genre> enrich(Collection<Genre> genries) {
        Collection<Genre> enrichedGenres = new ArrayList<>();
        for (Genre genre : genries) {
            enrichedGenres.add(get(genre.getId()));
        }
        return enrichedGenres;
    }
}
