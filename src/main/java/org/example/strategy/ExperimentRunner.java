package org.example.strategy;

import org.example.algorithm.BanditAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditExperiment;
import org.example.bandit.StochasticBanditRun;
import org.example.bandit.StochasticBanditRunResult;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ExperimentRunner {

    public StochasticBanditExperiment getExperiment(BanditAlgorithm banditAlgorithm,
                                                           int numRuns,
                                                           int n,
                                                           StochasticBandit bandit) {
        List<StochasticBanditRunResult> results = new LinkedList<>();
        for (int i = 0; i < numRuns; i++) {
            StochasticBanditRun run = new StochasticBanditRun(bandit, n);
            StochasticBanditRunResult result = banditAlgorithm.execute(run);
            results.add(result);
        }
        StochasticBanditExperiment exp = new StochasticBanditExperiment(numRuns, bandit, n,
                results);
        return exp;
    }

}
