package org.example.distribution;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;

public class GaussianDistribution implements Distribution {

    private double u;
    private double sd;

    private List<DistributionParameter> parameterList;

    private NormalDistribution dist;

    public GaussianDistribution(double u, double sd) {
        this.dist = new NormalDistribution(u, sd);
        this.u = u;
        this.sd = sd;
        this.parameterList = new LinkedList<>();
        this.parameterList.add(new DistributionParameter("mean", u));
        this.parameterList.add(new DistributionParameter("sd", sd));
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

    @Override
    public String getDistributionName() {
        return "Gaussian";
    }

    @Override
    public List<DistributionParameter> getDistributionParameters() {
        return parameterList;
    }

}
