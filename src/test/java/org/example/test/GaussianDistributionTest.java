package org.example.test;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.example.distribution.GaussianDistribution;
import org.testng.annotations.Test;

public class GaussianDistributionTest {

    @Test
    public void testGaussian1() {
        GaussianDistribution dist = new GaussianDistribution(1, 1);
        double total = 0;
        for (int i = 0; i < 100; i++) {
            total += dist.sample();
        }
        System.out.println(total / 100.0);
    }

    @Test
    public void test2() {
        UniformIntegerDistribution dist = new UniformIntegerDistribution(1, 4);
        for (int i = 0; i < 10; i++) {
            System.out.println(dist.sample());
        }
    }

}
