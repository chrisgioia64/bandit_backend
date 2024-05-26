package org.example.model;

import jakarta.persistence.*;
import org.example.controller.Algorithm;

@Entity
public class ExperimentParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private AlgorithmEntity algorithm;

    @ManyToOne
    private BanditEntity bandit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AlgorithmEntity getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmEntity algorithm) {
        this.algorithm = algorithm;
    }

    public BanditEntity getBandit() {
        return bandit;
    }

    public void setBandit(BanditEntity bandit) {
        this.bandit = bandit;
    }

    @Override
    public String toString() {
        return "ExperimentParameterEntity{" +
                "id=" + id +
                ", algorithm=" + algorithm +
                ", bandit=" + bandit +
                '}';
    }
}
