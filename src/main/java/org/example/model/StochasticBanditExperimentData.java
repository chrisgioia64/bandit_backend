package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StochasticBanditExperimentData {

    private double[] cumulativeMeanRegret;

    private String experimentName;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    public double[] getCumulativeMeanRegret() {
        return cumulativeMeanRegret;
    }

    public void setCumulativeMeanRegret(double[] cumulativeMeanRegret) {
        this.cumulativeMeanRegret = cumulativeMeanRegret;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
