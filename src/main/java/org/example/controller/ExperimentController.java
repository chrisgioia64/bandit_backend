package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.math3.analysis.function.Exp;
import org.example.algorithm.BanditAlgorithm;
import org.example.algorithm.UCBAlgorithm;
import org.example.aws.APILogEntry;
import org.example.aws.CloudWatchLogEntry;
import org.example.aws.MyCloudwatchService;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditCreator;
import org.example.bandit.StochasticBanditExperiment;
import org.example.controller.dto.AlgorithmFactory;
import org.example.controller.dto.BanditFactory;
import org.example.controller.dto.ExperimentDto;
import org.example.controller.dto.ExperimentParameters;
import org.example.model.ExperimentParameterEntity;
import org.example.service.CloudWatchProxyService;
import org.example.service.MainService;
import org.example.strategy.ExperimentRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ExperimentController {

    private Logger logger = LoggerFactory.getLogger(ExperimentController.class);

    @Autowired
    private MainService mainService;

    @Autowired
    private CloudWatchProxyService cloudWatchProxyService;

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
    private ResponseEntity<?> runExperiment(@RequestBody ExperimentParameters parameters,
                                            @RequestHeader Map<String, String> headers) {
        int n = parameters.getN();
        int numRuns = parameters.getNumTrials();
        List<BanditAlgorithm> banditAlgorithms = AlgorithmFactory.createAlgorithms(n);
        StochasticBandit bandit = BanditFactory.createBandit(parameters.getBanditRequest());

        cloudWatchProxyService.runExperiment(banditAlgorithms, bandit, numRuns, n, headers);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/getExperiments")
    private ResponseEntity<?> getAllExperimentData(@RequestHeader Map<String, String> headers) throws JsonProcessingException {
        Map<Long, ExperimentDto> experiments = cloudWatchProxyService.getAllExperiments(headers);
        return ResponseEntity.ok(experiments);
    }

    @GetMapping("/getExperimentById/{id}")
    private ResponseEntity<?> getExperimentById(@PathVariable int id, @RequestHeader Map<String, String> headers) {
        ExperimentDto dto = cloudWatchProxyService.getExperimentById(id, headers);
        return ResponseEntity.ok(dto);
    }
}
