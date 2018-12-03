package com.study.movieland.entity;

import java.util.Objects;

public class Genre {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return id == genre.id &&
                Objects.equals(name, genre.name);
    }

    public static class Builder {

        private Genre genre;

        public Builder() {
            genre = new Genre();
        }

        public Builder id(int value) {
            genre.id = value;
            return this;
        }

        public Builder name(String value) {
            genre.name = value;
            return this;
        }

        public Genre build() {
            return genre;
        }
    }
}
