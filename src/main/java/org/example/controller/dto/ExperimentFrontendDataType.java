package org.example.controller.dto;

import java.util.List;

public class ExperimentFrontendDataType {
    private String name;
    private int timeHorizon;
    private List<Distribution> distributions;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeHorizon() {
        return timeHorizon;
    }

    public void setTimeHorizon(int timeHorizon) {
        this.timeHorizon = timeHorizon;
    }

    public List<Distribution> getDistributions() {
        return distributions;
    }

    public void setDistributions(List<Distribution> distributions) {
        this.distributions = distributions;
    }

    @Override
    public String toString() {
        return "ExperimentRequest{" +
                "name='" + name + '\'' +
                ", timeHorizon=" + timeHorizon +
                ", distributions=" + distributions +
                '}';
    }

    public static class Distribution {
        private String type;
        private Double p; // For Bernoulli
        private Double mean; // For Normal
        private Double stdDev; // For Normal

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Double getP() {
            return p;
        }

        public void setP(Double p) {
            this.p = p;
        }

        public Double getMean() {
            return mean;
        }

        public void setMean(Double mean) {
            this.mean = mean;
        }

        public Double getStdDev() {
            return stdDev;
        }

        public void setStdDev(Double stdDev) {
            this.stdDev = stdDev;
        }

        @Override
        public String toString() {
            return "Distribution{" +
                    "type='" + type + '\'' +
                    ", p=" + p +
                    ", mean=" + mean +
                    ", stdDev=" + stdDev +
                    '}';
        }
    }
}