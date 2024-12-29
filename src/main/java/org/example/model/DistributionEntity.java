package org.example.model;

import jakarta.persistence.*;
import lombok.ToString;
import org.example.distribution.DistributionParameter;

import java.util.List;

@Entity
public class DistributionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String distributionName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DistributionParameter> parameters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public List<DistributionParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<DistributionParameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return distributionName;
    }
}
