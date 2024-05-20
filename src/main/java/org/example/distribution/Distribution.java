package org.example.distribution;

import java.util.List;

public interface Distribution {

    public double sample();

    public double getMean();

    public double getVariance();

    public String getDistributionName();

    public List<DistributionParameter> getDistributionParameters();

}
