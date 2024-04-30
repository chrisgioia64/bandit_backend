package org.example.distribution;

import java.util.Random;

public class BernoulliDistribution implements Distribution {

    private double p;
    private Random rand;

    public BernoulliDistribution(double p) {
        this.p = p;
        this.rand = new Random();
    }

    @Override
    public double sample() {
        double l = this.rand.nextDouble();
        if (l <= p) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public double getMean() {
        return p;
    }

    @Override
    public double getVariance() {
        return p * (1 - p);
    }
}
