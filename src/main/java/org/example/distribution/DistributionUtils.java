package org.example.distribution;

public class DistributionUtils {

    public static double getMean(double[] vals) {
        double total = 0;
        for (double val : vals) {
            total += val;
        }
        return total / vals.length;
    }

    public static double getVariance(double[] vals) {
        double mean = getMean(vals);
        double total = 0;
        for (double val : vals) {
            double error = (val - mean) * (val - mean);
            total += error;
        }
        return total / vals.length;
    }

}
