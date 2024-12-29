package org.example.controller.dto;

/**
 * Persisted in the database
 */
public class AlgorithmRequest {

    /**
     * An enumeration describing the type of algorithm (e.g. ETC, UCB, EpsilonGreedy)
     */
    private Algorithm algorithm;

    /**
     * The epsilon (percent) to perform an exploration action
     */
    private double epsilon;

    /**
     * Used in ETC algorithm to determine the number of times to choose an arm in the exploration phase
     */
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
