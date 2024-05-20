package org.example.test;

import org.example.algorithm.EpsilonGreedyAlgorithm;
import org.example.algorithm.UCBAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditCreator;
import org.example.bandit.StochasticBanditExperiment;
import org.example.strategy.ExperimentRunner;
import org.testng.annotations.Test;

import java.util.List;

public class GaussianBanditUCBTest {

    @Test
    public void test1() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.5, 0.0), 1);

        int n = 100;
        int numRuns = 500;

        ExperimentRunner runner = new ExperimentRunner();
        UCBAlgorithm ucbAlgorithm = new UCBAlgorithm();
        StochasticBanditExperiment experiment = runner.getExperiment(ucbAlgorithm, numRuns, n, stoch);

        System.out.println("% optimal: " + experiment.getPercentOptimal()[n-1]);
        System.out.println("Reward: " + experiment.getMeanRewards()[n-1]);
        int[] arr = new int[] {0, 1, 5, 10, 20, 50, 99};
        for (int a : arr) {
            System.out.println("Cum Regret: " + experiment.getCumulativeMeanRegret()[a]);
        }
        System.out.println("Cum Std Regret: " + Math.sqrt(experiment.getCumulativeVarianceRegret()[n-1]));
        System.out.println("--------");
    }

    @Test
    public void test2() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.5, 0.0), 1);

        int n = 100;
        int numRuns = 500;

        ExperimentRunner runner = new ExperimentRunner();
        EpsilonGreedyAlgorithm ucbAlgorithm = new EpsilonGreedyAlgorithm(0.05);
        StochasticBanditExperiment experiment = runner.getExperiment(ucbAlgorithm, numRuns, n, stoch);

        System.out.println("% optimal: " + experiment.getPercentOptimal()[n-1]);
        System.out.println("Reward: " + experiment.getMeanRewards()[n-1]);
        int[] arr = new int[] {0, 1, 5, 10, 20, 50, 99};
        for (int a : arr) {
            System.out.println("Cum Regret: " + experiment.getCumulativeMeanRegret()[a]);
        }
        System.out.println("Cum Std Regret: " + Math.sqrt(experiment.getCumulativeVarianceRegret()[n-1]));
        System.out.println("--------");
    }


}
