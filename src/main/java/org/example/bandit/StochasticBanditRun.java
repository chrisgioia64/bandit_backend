package org.example.bandit;

/**
 * A single run with rewards of applying the Stochastic Bandit
 */
public class StochasticBanditRun {

    private StochasticBandit bandit;

    private int n;

    private Reward[] totalRewards;

    public StochasticBanditRun(StochasticBandit bandit, int n) {
        this.bandit = bandit;
        this.totalRewards = new Reward[n];
        this.n = n;
        for (int i = 0; i < n; i++) {
            int m = bandit.getArms().length;
            double[] rew = new double[m];
            for (int j = 0; j < m; j++) {
                rew[j] = bandit.getArms()[j].sample();
            }
            totalRewards[i] = new Reward(rew);
        }
    }

    public StochasticBandit getBandit() {
        return bandit;
    }

    public int getN() {
        return n;
    }

    public Reward[] getTotalRewards() {
        return totalRewards;
    }

    /**
     * The rewards for a single time step
     */
    public static class Reward {
        private double[] rewards;

        public Reward(double[] rewards) {
            this.rewards = rewards;
        }

        public double[] getRewards() {
            return rewards;
        }
    }

}
