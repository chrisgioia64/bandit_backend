package org.example.algorithm;

import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;

import java.util.Arrays;

public class ETCAlgorithm implements BanditAlgorithm {

    private int m;

    public ETCAlgorithm(int m) {
        this.m = m;
    }

    @Override
    public StochasticBanditRunResult execute(StochasticBanditRun run) {
        int n = run.getN();
        int k = run.getBandit().getArms().length;
        int[] armsSelected = new int[n];
        double[] rewards = new double[n];
        int count = 0;
        double[] empiricalMeans = new double[k];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                if (count == n) {
                    break;
                }
                int idx = i * m + j;
                StochasticBanditRun.Reward reward = run.getTotalRewards()[count];
                double empiricalMean = reward.getRewards()[j];
                armsSelected[count] = j;
                rewards[count] = empiricalMean;
                int num_pulls = i + 1;
                double new_mean = (num_pulls - 1.0) / num_pulls * empiricalMeans[j] + (1.0 / num_pulls) * empiricalMean;
                empiricalMeans[j] = new_mean;
                count++;
            }
            if (count == n) {
                break;
            }
        }
        int bestEmpiricalArm = getBestArm(empiricalMeans);
        while (count < n) {
            armsSelected[count] = bestEmpiricalArm;
            rewards[count] = run.getTotalRewards()[count].getRewards()[bestEmpiricalArm];
            count++;
        }
        return new StochasticBanditRunResult(armsSelected, rewards, run);
    }

    private static int getBestArm(double[] empiricalMeans) {
        double bestValue = Double.MIN_VALUE;
        int bestIndex = -1;
        for (int i = 0; i < empiricalMeans.length; i++) {
            if (empiricalMeans[i] > bestValue) {
                bestIndex = i;
                bestValue = empiricalMeans[i];
            }
        }
        return bestIndex;
    }
}
