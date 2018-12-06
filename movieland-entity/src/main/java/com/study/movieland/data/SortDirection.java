package com.study.movieland.data;

public enum SortDirection {

    ASC, DESC;

    static SortDirection DEFAULT = ASC;

    public static SortDirection get(String value) {
        if (value == null || value.isEmpty()) {
            return DEFAULT;
        }
        return SortDirection.valueOf(value.toUpperCase());
    }
}
