package org.example.algorithm;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.example.bandit.StochasticBanditExperiment;
import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;

import java.util.Arrays;
import java.util.List;

public class UCBAlgorithm implements BanditAlgorithm {

    @Override
    public StochasticBanditRunResult execute(StochasticBanditRun run) {
        int n = run.getN();
        int k = run.getBandit().getArms().length;
        UniformIntegerDistribution discreteDistribution = new UniformIntegerDistribution(0, k-1);
        int[] armsSelected = new int[n];
        double[] rewards = new double[n];
        double[] empiricalMeans = new double[k];
        double[] ucbs = new double[k];
        int[] numPulls = new int[k];
        int t = 0;
        while (t < k) {
            StochasticBanditRun.Reward rewardDatum = run.getTotalRewards()[t];
            double reward = rewardDatum.getRewards()[t];
            numPulls[t] = 1;
            empiricalMeans[t] = reward;
            ucbs[t] = empiricalMeans[t] + Math.sqrt(Math.log(2 * n * n) / numPulls[t]);
            armsSelected[t] = t;
            rewards[t] = reward;
            t++;
        }
        while (t < n) {
            StochasticBanditRun.Reward rewardDatum = run.getTotalRewards()[t];
            int maxUcb = getMaxUcbs(ucbs);
            double reward = rewardDatum.getRewards()[maxUcb];
            numPulls[maxUcb]++;
            int p = numPulls[maxUcb];
            empiricalMeans[maxUcb] = empiricalMeans[maxUcb] * (p - 1.0) / p + reward / p;
            armsSelected[t] = maxUcb;
            ucbs[maxUcb] = empiricalMeans[maxUcb] * Math.sqrt(Math.log(2 * n * n) / numPulls[maxUcb]);
            rewards[t] = reward;
            t++;
        }
        return new StochasticBanditRunResult(armsSelected, rewards, run);
    }

    @Override
    public String getAlgorithmName() {
        return "UCB";
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return Arrays.asList();
    }

    public static int getMaxUcbs(double[] ucbs) {
        int maxIndex = 0;
        for (int i = 1; i < ucbs.length; i++) {
            if (ucbs[i] > ucbs[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
