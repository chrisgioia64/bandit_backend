package org.example.algorithm;

import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;

import java.util.List;

public interface BanditAlgorithm {
    StochasticBanditRunResult execute(StochasticBanditRun run);

    public String getAlgorithmName();

    public List<AlgorithmParameter> getAlgorithmParameters();

}
