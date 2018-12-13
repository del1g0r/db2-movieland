package com.study.movieland.data;

import java.util.EnumSet;
import java.util.Set;

public enum MovieEnricherType {

    COUNTRY, GENRES, REVIEWS;

    public static Set<MovieEnricherType> ALL = EnumSet.of(COUNTRY, GENRES, REVIEWS);
}
