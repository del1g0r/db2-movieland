package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Currency {

    @JsonProperty("r030")
    private int id;
    @JsonProperty("txt")
    private String name;
    private double rate;
    @JsonProperty("cc")
    private String code;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return id == currency.id &&
                Double.compare(currency.rate, rate) == 0 &&
                Objects.equals(name, currency.name) &&
                Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rate, code);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "r030=" + id +
                ", txt='" + name + '\'' +
                ", rate=" + rate +
                ", cc='" + code + '\'' +
                '}';
    }

    public static class Builder {

        private Currency currency;

        public Builder() {
            currency = new Currency();
        }

        public Builder id(int value) {
            currency.id = value;
            return this;
        }

        public Builder name(String value) {
            currency.name = value;
            return this;
        }

        public Builder rate(double value) {
            currency.rate = value;
            return this;
        }

        public Builder code(String value) {
            currency.code = value;
            return this;
        }

        public Currency build() {
            return currency;
        }
    }
}