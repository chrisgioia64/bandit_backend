package org.example.test;

import org.example.algorithm.ETCAlgorithm;
import org.example.bandit.*;
import org.example.distribution.DistributionUtils;
import org.example.strategy.ExperimentRunner;
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

    @Test
    public void test3() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.5, 0.0), 1);

        int n = 100;
        int numRuns = 500;

        ExperimentRunner runner = new ExperimentRunner();
        int[] mAry = new int[] {1, 2, 3, 4, 5, 10, 15, 20, 40};
        for (int m : mAry) {
            ETCAlgorithm etcAlgorithm = new ETCAlgorithm(m);
            StochasticBanditExperiment experiment = runner.getExperiment(etcAlgorithm, numRuns, n, stoch);
            System.out.println("m " + m);
            System.out.println("% optimal: " + experiment.getPercentOptimal()[n-1]);
            System.out.println("Reward: " + experiment.getMeanRewards()[n-1]);
            System.out.println("Cum Regret: " + experiment.getCumulativeMeanRegret()[n-1]);
            System.out.println("Cum Std Regret: " + Math.sqrt(experiment.getCumulativeVarianceRegret()[n-1]));
            System.out.println("--------");
        }
    }


}
