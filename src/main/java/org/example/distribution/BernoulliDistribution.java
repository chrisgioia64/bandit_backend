package org.example.distribution;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BernoulliDistribution implements Distribution {

    private double p;
    private Random rand;

    private List<DistributionParameter> parameterList;

    public BernoulliDistribution(double p) {
        this.p = p;
        this.rand = new Random();
        this.parameterList = new LinkedList<>();
        this.parameterList.add(new DistributionParameter("p", p));
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

    @Override
    public String getDistributionName() {
        return "Bernoulli";
    }

    @Override
    public List<DistributionParameter> getDistributionParameters() {
        return parameterList;
    }

}
