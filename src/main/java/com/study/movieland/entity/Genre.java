package com.study.movieland.entity;

import java.util.Objects;

public class Genre {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Movie{" +
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
            genre.setId(value);
            return this;
        }

        public Builder name(String value) {
            genre.setName(value);
            return this;
        }

        public Genre build() {
            return genre;
        }
    }
}
