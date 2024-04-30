package org.example.test;

import org.example.algorithm.ETCAlgorithm;
import org.example.algorithm.EpsilonGreedyAlgorithm;
import org.example.bandit.*;
import org.example.strategy.ExperimentRunner;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class GaussianBanditEpsGreedyTest {

    @Test
    public void test() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.0), 1);

        int n = 100;
        StochasticBanditRun run = new StochasticBanditRun(stoch, n);

        EpsilonGreedyAlgorithm epsGreedyAlgorithm = new EpsilonGreedyAlgorithm(0.2);
        StochasticBanditRunResult result = epsGreedyAlgorithm.execute(run);

        System.out.println("Arms Selected: " + Arrays.toString(result.getArmsSelected()));
        System.out.println("Rewards: " + Arrays.toString(result.getRewards()));
        System.out.println("Cum Regret: " + Arrays.toString(result.getCumulativeRegret()));
    }

    @Test
    public void test2() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.5, 0.0), 1);

        int n = 100;
        int numRuns = 500;

        ExperimentRunner runner = new ExperimentRunner();
        double[] mAry = new double[] {0.05, 0.1, 0.2, 0.3};
        for (double epsilon : mAry) {
            EpsilonGreedyAlgorithm etcAlgorithm = new EpsilonGreedyAlgorithm(epsilon);
            StochasticBanditExperiment experiment = runner.getExperiment(etcAlgorithm, numRuns, n, stoch);
            System.out.println("epsilon " + epsilon);
            System.out.println("% optimal: " + experiment.getPercentOptimal()[n-1]);
            System.out.println("Reward: " + experiment.getMeanRewards()[n-1]);
            System.out.println("Cum Regret: " + experiment.getCumulativeMeanRegret()[n-1]);
            System.out.println("Cum Std Regret: " + Math.sqrt(experiment.getCumulativeVarianceRegret()[n-1]));
            System.out.println("--------");
        }
    }

}
