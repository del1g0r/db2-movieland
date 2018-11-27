package com.study.movieland.data;

public enum SortDirection {

    ASC, DESC;

    static SortDirection DEFAULT_DIRECTION = ASC;

    public static SortDirection getByName(String name) {
        if (name == null || name.isEmpty()) {
            return DEFAULT_DIRECTION;
        }
        for (SortDirection sortDirection : values()) {
            if (sortDirection.toString().equalsIgnoreCase(name)) {
                return sortDirection;
            }
        }
        throw new IllegalArgumentException("Is unknown direction: " + name);
    }
}
