package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

@Entity
public class AnalysisDataPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int timeStep;

    private double cumulativeRegret;

    private double percentOptimal;

    private double varianceRegret;

    @ManyToOne
    @JoinColumn(name = "experiment_id")
    private ExperimentParameterEntity experimentParameter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public double getCumulativeRegret() {
        return cumulativeRegret;
    }

    public void setCumulativeRegret(double cumulativeRegret) {
        this.cumulativeRegret = cumulativeRegret;
    }

    public ExperimentParameterEntity getExperimentParameter() {
        return experimentParameter;
    }

    public void setExperimentParameter(ExperimentParameterEntity experimentParameter) {
        this.experimentParameter = experimentParameter;
    }

    public double getPercentOptimal() {
        return percentOptimal;
    }

    public void setPercentOptimal(double percentOptimal) {
        this.percentOptimal = percentOptimal;
    }

    public double getVarianceRegret() {
        return varianceRegret;
    }

    public void setVarianceRegret(double varianceRegret) {
        this.varianceRegret = varianceRegret;
    }
}
