package com.study.movieland.data;

public enum SortDirection {

    ASC, DESC;

    static SortDirection DEFAULT_DIRECTION = ASC;

    public static SortDirection getByString(String value) {
        if (value == null || value.isEmpty()) {
            return DEFAULT_DIRECTION;
        }
        return SortDirection.valueOf(value.toUpperCase());
    }
}
