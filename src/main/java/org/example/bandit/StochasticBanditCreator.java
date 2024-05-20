package org.example.bandit;

import org.example.distribution.BernoulliDistribution;
import org.example.distribution.Distribution;
import org.example.distribution.GaussianDistribution;
import org.example.distribution.GaussianDistributionSeed;

import java.util.List;

public class StochasticBanditCreator {

    public static StochasticBandit createBernoulliBandits(List<Double> probs) {
        Distribution[] dists = new Distribution[probs.size()];
        for (int i = 0; i < probs.size(); i++) {
            double prob = probs.get(i);
            Distribution dist = new BernoulliDistribution(prob);
            dists[i] = dist;
        }
        return new StochasticBandit(dists);
    }

    public static StochasticBandit createOneGaussianBandits(List<Double> means) {
        Distribution[] dists = new Distribution[means.size()];
        for (int i = 0; i < means.size(); i++) {
            double mean = means.get(i);
            Distribution dist = new GaussianDistribution(mean, 1.0);
            dists[i] = dist;
        }
        return new StochasticBandit(dists);
    }

    public static StochasticBandit createOneGaussianSeedBandits(List<Double> means, int seed) {
        Distribution[] dists = new Distribution[means.size()];
        for (int i = 0; i < means.size(); i++) {
            double mean = means.get(i);
            Distribution dist = new GaussianDistributionSeed(mean, 1.0, seed + i);
            dists[i] = dist;
        }
        return new StochasticBandit(dists);
    }

    public static StochasticBandit createGaussianSeedBandits(List<Double> means, double sd, int seed) {
        Distribution[] dists = new Distribution[means.size()];
        for (int i = 0; i < means.size(); i++) {
            double mean = means.get(i);
            Distribution dist = new GaussianDistributionSeed(mean, sd, seed + i);
            dists[i] = dist;
        }
        return new StochasticBandit(dists);
    }

}
