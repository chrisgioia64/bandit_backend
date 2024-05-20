package org.example.distribution;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GaussianDistributionSeed implements Distribution {

    private double mean;
    private double sd;

    private Random randU;

    private Random randV;

    private List<DistributionParameter> parameterList;


    public GaussianDistributionSeed(double mean, double sd, int seed) {
        this.mean = mean;
        this.sd = sd;
        this.randU = new Random(seed);
        this.randV = new Random(seed);
        this.parameterList = new LinkedList<>();
        this.parameterList.add(new DistributionParameter("mean", mean));
        this.parameterList.add(new DistributionParameter("sd", sd));
    }

    @Override
    public double sample() {
        double u = randU.nextDouble();
        double v = randV.nextDouble();
        double x = Math.sqrt(-2 * Math.log(u)) * Math.cos(2 * Math.PI * v);
        return mean + sd * x;
    }

    @Override
    public double getMean() {
        return mean;
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
