package org.example.model;

import jakarta.persistence.*;
import org.example.algorithm.AlgorithmParameter;

import java.util.List;

@Entity
public class AlgorithmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String algorithmName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AlgorithmParameter> parameters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public List<AlgorithmParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AlgorithmParameter> parameters) {
        this.parameters = parameters;
    }
}
