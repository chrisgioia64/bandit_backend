package org.example.controller;

public class ExperimentParameters {

    private AlgorithmRequest algorithmRequest;

    private BanditRequest banditRequest;

    private int n;

    private int numTrials;

    public AlgorithmRequest getAlgorithmRequest() {
        return algorithmRequest;
    }

    public void setAlgorithmRequest(AlgorithmRequest algorithmRequest) {
        this.algorithmRequest = algorithmRequest;
    }

    public BanditRequest getBanditRequest() {
        return banditRequest;
    }

    public void setBanditRequest(BanditRequest banditRequest) {
        this.banditRequest = banditRequest;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getNumTrials() {
        return numTrials;
    }

    public void setNumTrials(int numTrials) {
        this.numTrials = numTrials;
    }
}
