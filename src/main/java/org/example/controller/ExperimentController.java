package org.example.controller;

import org.apache.commons.math3.analysis.function.Exp;
import org.example.algorithm.BanditAlgorithm;
import org.example.algorithm.UCBAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditCreator;
import org.example.bandit.StochasticBanditExperiment;
import org.example.controller.dto.AlgorithmFactory;
import org.example.controller.dto.BanditFactory;
import org.example.controller.dto.ExperimentDto;
import org.example.controller.dto.ExperimentParameters;
import org.example.model.ExperimentParameterEntity;
import org.example.service.MainService;
import org.example.strategy.ExperimentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ExperimentController {


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
    private ResponseEntity<?> runExperiment(@RequestBody ExperimentParameters parameters) {
        int n = parameters.getN();
        int numRuns = parameters.getNumTrials();
        List<BanditAlgorithm> banditAlgorithms = AlgorithmFactory.createAlgorithms(n);
        StochasticBandit bandit = BanditFactory.createBandit(parameters.getBanditRequest());

        mainService.runExperiments(banditAlgorithms, bandit, numRuns, n);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/getExperiments")
    private ResponseEntity<?> getAllExperimentData() {
        Map<Long, ExperimentDto> experiments = mainService.getExperiments();
        return ResponseEntity.ok(experiments);
    }

    @GetMapping("/getExperimentById/{id}")
    private ResponseEntity<?> getExperimentById(@PathVariable int id) {
        ExperimentDto dto = mainService.getExperimentById(id);
        return ResponseEntity.ok(dto);
    }
}
