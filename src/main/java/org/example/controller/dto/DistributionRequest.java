package org.example.controller.dto;

public class DistributionRequest {

    private DistributionType distributionType;

    /** Parameter p of success. Applicable for Bernoulli random variables. */
    private double p;

    /** The mean (applicable for Normal distribution). */
    private double mean;

    /** The standard deviation (applicable for Normal distribution). */
    private double sd;

    public DistributionType getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(DistributionType distributionType) {
        this.distributionType = distributionType;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getSd() {
        return sd;
    }

    public void setSd(double sd) {
        this.sd = sd;
    }
}
