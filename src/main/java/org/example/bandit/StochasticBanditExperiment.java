package org.example.bandit;

import java.util.Iterator;
import java.util.List;

public class StochasticBanditExperiment {

    private int numRuns;
    private StochasticBandit bandit;
    private int n;

    private List<StochasticBanditRunResult> runResults;

    private double[] meanRewards;

    private double[] varianceRewards;

    /** The percent of the time that the action chosen is the optimal one. */
    private double[] percentOptimal;

    private double[] cumulativeMeanRegret;

    private double[] cumulativeVarianceRegret;

    public StochasticBanditExperiment(int numRuns, StochasticBandit bandit, int n,
                                      List<StochasticBanditRunResult> results) {
        this.numRuns = numRuns;
        this.bandit = bandit;
        this.n = n;
        this.runResults = results;
        this.evaluateStats();
    }

    public double[] getMeanRewards() {
        return meanRewards;
    }

    public double[] getVarianceRewards() {
        return varianceRewards;
    }

    public double[] getPercentOptimal() {
        return percentOptimal;
    }

    public double[] getCumulativeMeanRegret() {
        return cumulativeMeanRegret;
    }

    public double[] getCumulativeVarianceRegret() {
        return cumulativeVarianceRegret;
    }

    private void evaluateStats() {
        this.meanRewards = new double[n];
        double frac = 1.0 / this.numRuns;
        for (StochasticBanditRunResult runResult : runResults) {
            double[] rewards = runResult.getRewards();
            for (int i = 0; i < rewards.length; i++) {
                meanRewards[i] += frac * rewards[i];
            }
        }
        this.varianceRewards = new double[n];
        for (StochasticBanditRunResult runResult : runResults) {
            double[] rewards = runResult.getRewards();
            for (int i = 0; i < rewards.length; i++) {
                varianceRewards[i] += frac * (rewards[i] * rewards[i]);
            }
        }
        for (int i = 0; i < n; i++) {
            this.varianceRewards[i] = this.varianceRewards[i] - (this.meanRewards[i] * this.meanRewards[i]);
        }

        this.percentOptimal = new double[n];
        int bestArm = bandit.getBestArm();
        for (StochasticBanditRunResult runResult : runResults) {
            int[] armsSelected = runResult.getArmsSelected();
            for (int i = 0; i < n; i++) {
                if (bestArm == armsSelected[i]) {
                    percentOptimal[i]++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            percentOptimal[i] = percentOptimal[i] / (numRuns + 0.0);
        }

        this.cumulativeMeanRegret = new double[n];
        for (StochasticBanditRunResult runResult : runResults) {
            double[] cumulativeRegret = runResult.getCumulativeRegret();
            for (int i = 0; i < cumulativeRegret.length; i++) {
                double regret = cumulativeRegret[i];
                cumulativeMeanRegret[i] += frac * regret;
            }
        }
        this.cumulativeVarianceRegret = new double[n];
        for (StochasticBanditRunResult runResult : runResults) {
            double[] cumulativeRegret = runResult.getCumulativeRegret();
            for (int i = 0; i < cumulativeRegret.length; i++) {
                double regret = cumulativeRegret[i];
                cumulativeVarianceRegret[i] += frac * regret * regret;
            }
        }
        for (int i = 0; i < n; i++) {
            this.cumulativeVarianceRegret[i] = this.cumulativeVarianceRegret[i]
                    - (this.cumulativeMeanRegret[i] * this.cumulativeMeanRegret[i]);
        }
    }
}
