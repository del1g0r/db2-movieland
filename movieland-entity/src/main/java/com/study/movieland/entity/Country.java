package com.study.movieland.entity;

import java.util.Objects;

public class Country {

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
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return id == country.id &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static class Builder {

        private Country country;

        public Builder() {
            country = new Country();
        }

        public Country.Builder id(int value) {
            country.id = value;
            return this;
        }

        public Country.Builder name(String value) {
            country.name = value;
            return this;
        }

        public Country build() {
            return country;
        }
    }
}
