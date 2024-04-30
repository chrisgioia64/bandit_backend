package org.example.test;

import org.example.algorithm.ETCAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditCreator;
import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;
import org.example.distribution.DistributionUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class GaussianBanditETCTest {

    @Test
    public void test() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.5), 3);

        int n = 100;
        StochasticBanditRun run = new StochasticBanditRun(stoch, n);

        double[] arm1Rewards = new double[n];
        double[] arm2Rewards = new double[n];
        for (int i = 0; i < run.getTotalRewards().length; i++) {
            StochasticBanditRun.Reward reward = run.getTotalRewards()[i];
            arm1Rewards[i] = reward.getRewards()[0];
            arm2Rewards[i] = reward.getRewards()[1];
        }

        System.out.println("Mean 1: " + DistributionUtils.getMean(arm1Rewards));
        System.out.println("Var 1: " + DistributionUtils.getVariance(arm1Rewards));
        System.out.println("Mean 2: " + DistributionUtils.getMean(arm2Rewards));
        System.out.println("Var 2: " + DistributionUtils.getVariance(arm2Rewards));
    }

    @Test
    public void test2() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.0), 1);

        int n = 50;
        StochasticBanditRun run = new StochasticBanditRun(stoch, n);

        ETCAlgorithm etcAlgorithm = new ETCAlgorithm(20);
        StochasticBanditRunResult result = etcAlgorithm.execute(run);

        System.out.println(Arrays.toString(result.getArmsSelected()));
        System.out.println(Arrays.toString(result.getRewards()));
        System.out.println(Arrays.toString(result.getCumulativeRegret()));
    }


}
