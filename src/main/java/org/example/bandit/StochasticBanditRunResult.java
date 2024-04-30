package org.example.bandit;

/**
 * The result of applying an algorithm on a Stochastic Bandit Run
 * Includes the arm selected and reward for each iteration in time
 */
public class StochasticBanditRunResult {

    private int[] armsSelected;
    private double[] rewards;
    private StochasticBanditRun run;

    public StochasticBanditRunResult(int[] armsSelected, double[] rewards, StochasticBanditRun run) {
        this.armsSelected = armsSelected;
        this.rewards = rewards;
        this.run = run;
    }

    public int[] getArmsSelected() {
        return armsSelected;
    }

    public double[] getRewards() {
        return rewards;
    }

    public StochasticBanditRun getRun() {
        return run;
    }

    public double[] getCumulativeRegret() {
        int bestArm = run.getBandit().getBestArm();
        double[] result = new double[armsSelected.length];
//        System.out.println("Best arm: " + bestArm);
        for (int i = 0; i < run.getN(); i++) {
            double goodReward = run.getTotalRewards()[i].getRewards()[bestArm];
            double chosenReward = rewards[i];
            double difference = (goodReward - chosenReward);
            if (i == 0) {
                result[i] = difference;
            } else {
                result[i] = result[i-1] + difference;
            }
        }
        return result;
    }

    public int[] getArmsSelectedDistribution() {
        int k = run.getBandit().getArms().length;
        int[] selected = new int[k];
        for (int i = 0; i < armsSelected.length; i++) {
            int arm = armsSelected[i];
            selected[arm]++;
        }
        return selected;
    }
}
