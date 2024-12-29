package org.example.model;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class BanditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DistributionEntity> distributions = new LinkedList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<DistributionEntity> getDistributions() {
        return distributions;
    }

    public void setDistributions(List<DistributionEntity> distributions) {
        this.distributions = distributions;
    }

    public String toString() {
        return distributions.toString();
    }
}
