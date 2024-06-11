package org.example.algorithm;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EpsilonGreedyAlgorithm implements BanditAlgorithm {

    private double epsilon;
    private UniformRealDistribution uniformRealDistribution;

    private List<AlgorithmParameter> list;

    private Logger logger = LoggerFactory.getLogger(EpsilonGreedyAlgorithm.class);

    public EpsilonGreedyAlgorithm(double epsilon) {
        this.epsilon = epsilon;
        this.uniformRealDistribution = new UniformRealDistribution();
        this.list = new ArrayList<>();
        list.add(new AlgorithmParameter("epsilon", epsilon));
    }

    @Override
    public StochasticBanditRunResult execute(StochasticBanditRun run) {
        int n = run.getN();
        int k = run.getBandit().getArms().length;
        UniformIntegerDistribution discreteDistribution = new UniformIntegerDistribution(0, k-1);
        int[] armsSelected = new int[n];
        double[] rewards = new double[n];
        double[] empiricalMeans = new double[k];
        int[] numPulls = new int[k];
        int t = 0;
        while (t < k && t < n) {
            StochasticBanditRun.Reward rewardDatum = run.getTotalRewards()[t];
            double reward = rewardDatum.getRewards()[t];
            numPulls[t] = 1;
            empiricalMeans[t] = reward;
            rewards[t] = reward;
            armsSelected[t] = t;
            t++;
        }
        while (t < n) {
            int idx = -1;
            double s = uniformRealDistribution.sample();
            if (s < (epsilon / t)) {
                // Randomly choose
                idx =  discreteDistribution.sample();
            } else {
                // Pick the argmax
                idx = getArgmax(empiricalMeans);
            }
            StochasticBanditRun.Reward reward = run.getTotalRewards()[t];
            double newReward = reward.getRewards()[idx];
            armsSelected[t] = idx;
            rewards[t] = newReward;
            int num_pulls = numPulls[idx] + 1;
            numPulls[idx]++;
            double new_mean = (num_pulls - 1.0) / num_pulls * empiricalMeans[idx] + (1.0 / num_pulls) * newReward;
            empiricalMeans[idx] = new_mean;
            t++;
        }
        return new StochasticBanditRunResult(armsSelected, rewards, run);
    }

    @Override
    public String getAlgorithmName() {
        return "EpsilonGreedy";
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return list;
    }

    private int getArgmax(double[] empiricalMeans) {
        int idx = 0;
        for (int i = 1; i < empiricalMeans.length; i++) {
            if (empiricalMeans[i] > empiricalMeans[idx]) {
                idx = i;
            }
        }
        return idx;
    }
}
