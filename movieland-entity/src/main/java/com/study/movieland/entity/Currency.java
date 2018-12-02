package com.study.movieland.entity;

import java.util.Objects;

public class Currency {

    private int r030;
    private String txt;
    private double rate;
    private String cc;

    public int getR030() {
        return r030;
    }

    public String getTxt() {
        return txt;
    }

    public double getRate() {
        return rate;
    }

    public String getCc() {
        return cc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return r030 == currency.r030 &&
                Double.compare(currency.rate, rate) == 0 &&
                Objects.equals(txt, currency.txt) &&
                Objects.equals(cc, currency.cc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(r030, txt, rate, cc);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "r030=" + r030 +
                ", txt='" + txt + '\'' +
                ", rate=" + rate +
                ", cc='" + cc + '\'' +
                '}';
    }

    public static class Builder {

        private Currency currency;

        public Builder() {
            currency = new Currency();
        }

        public Currency.Builder r030(int value) {
            currency.r030 = value;
            return this;
        }

        public Currency.Builder txt(String value) {
            currency.txt = value;
            return this;
        }

        public Currency.Builder rate(double value) {
            currency.rate = value;
            return this;
        }

        public Currency.Builder cc(String value) {
            currency.cc = value;
            return this;
        }

        public Currency build() {
            return currency;
        }
    }
}