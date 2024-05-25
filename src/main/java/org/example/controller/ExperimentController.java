package org.example.controller;

import org.example.algorithm.BanditAlgorithm;
import org.example.algorithm.UCBAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditCreator;
import org.example.bandit.StochasticBanditExperiment;
import org.example.model.AnalysisDataPointEntity;
import org.example.model.EntityFactory;
import org.example.service.MainService;
import org.example.strategy.ExperimentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExperimentController {

    @Autowired
    private ExperimentRunner experimentRunner;

    @Autowired
    private MainService mainService;

    @GetMapping("/runExperiment2")
    private StochasticBanditExperiment runExperiment2() {
        StochasticBandit stoch = StochasticBanditCreator.createOneGaussianSeedBandits(List.of(1.0, 0.5, 0.0), 1);
        int n = 100;
        int numRuns = 1000;

        ExperimentRunner runner = new ExperimentRunner();
        UCBAlgorithm ucbAlgorithm = new UCBAlgorithm();
        StochasticBanditExperiment experiment = runner.getExperiment(ucbAlgorithm, numRuns, n, stoch);
        return experiment;
    }

    @PostMapping("/runExperiment")
    private StochasticBanditExperiment runExperiment(@RequestBody ExperimentParameters parameters) {
        BanditAlgorithm algorithm = AlgorithmFactory.createAlgorithm(parameters.getAlgorithmRequest());
        StochasticBandit bandit = BanditFactory.createBandit(parameters.getBanditRequest());
        int n = parameters.getN();
        int numRuns = parameters.getNumTrials();
        StochasticBanditExperiment experiment = experimentRunner.getExperiment(algorithm,
                numRuns, n, bandit);
        List<AnalysisDataPointEntity> entities = EntityFactory.createEntity(experiment, algorithm, bandit, n);
        mainService.saveAllDatapoints(entities);
        return experiment;
    }

    @PostMapping("/runExperiment3")
    private ExperimentParameters runExperiment3(@RequestBody ExperimentParameters parameters) {
        return parameters;
    }
}
