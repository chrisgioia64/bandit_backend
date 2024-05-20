package org.example.bandit;

import org.example.distribution.Distribution;

/**
 * A collection of arms where each arm is an IID distribution of rewards
 */
public class StochasticBandit {

    private Distribution[] arms;

    private int bestArm = -1;

    public StochasticBandit(Distribution[] arms) {
        this.arms = arms;
    }

    public Distribution[] getArms() {
        return arms;
    }

    public int getBestArm() {
        if (bestArm == -1) {
            int i = -1;
            double bestMean = Double.MIN_VALUE;
            for (int j = 0; j < arms.length; j++) {
                double mean = arms[j].getMean();
                if (mean > bestMean) {
                    bestMean = mean;
                    i = j;
                }
            }
            bestArm = i;
        }
        return bestArm;
    }


}
