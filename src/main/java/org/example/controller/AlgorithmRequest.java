package org.example.controller;

public class AlgorithmRequest {

    private Algorithm algorithm;

    private double epsilon;

    private int m;

    private double ucbFraction;

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public double getUcbFraction() {
        return ucbFraction;
    }

    public void setUcbFraction(double ucbFraction) {
        this.ucbFraction = ucbFraction;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}
