package org.example.distribution;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.text.Normalizer;

public class GaussianDistribution implements Distribution {

    private double u;
    private double sd;

    private NormalDistribution dist;

    public GaussianDistribution(double u, double sd) {
        this.dist = new NormalDistribution(u, sd);
        this.u = u;
        this.sd = sd;
    }

    @Override
    public double sample() {
        return this.dist.sample();
    }

    @Override
    public double getMean() {
        return u;
    }

    @Override
    public double getVariance() {
        return sd * sd;
    }
}
