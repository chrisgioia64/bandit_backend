package org.example.algorithm;

import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;

@FunctionalInterface
public interface BanditAlgorithm {
    StochasticBanditRunResult execute(StochasticBanditRun run);

}
