package org.example.distribution;

public class DistributionParameter {

    private String parameterName;

    private double parameterValue;

    public DistributionParameter(String parameterName, double parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public double getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(double parameterValue) {
        this.parameterValue = parameterValue;
    }
}
