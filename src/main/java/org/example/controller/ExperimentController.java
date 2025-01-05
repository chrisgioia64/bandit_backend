package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.algorithm.BanditAlgorithm;
import org.example.algorithm.UCBAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditCreator;
import org.example.bandit.StochasticBanditExperiment;
import org.example.controller.dto.*;
import org.example.model.BanditEntity;
import org.example.service.CloudWatchProxyService;
import org.example.service.MainService;
import org.example.strategy.ExperimentRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<BanditEntity> experiments = cloudWatchProxyService.getAllExperiments(headers);
        return ResponseEntity.ok(experiments);
    }

    @GetMapping("/getExperimentById/{id}")
    private ResponseEntity<?> getExperimentById(@PathVariable int id, @RequestHeader Map<String, String> headers) {
        ExperimentDto dto = cloudWatchProxyService.getExperimentById(id, headers);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/success")
    public ResponseEntity<?> success() {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/submitExperiment")
    public ResponseEntity<?> createExperiment(@RequestBody ExperimentFrontendDataType experimentRequest,
                                              @RequestHeader Map<String, String> headers) {
        // Log or process the incoming request
        logger.info("Received Experiment Request: " + experimentRequest);

        int n = experimentRequest.getTimeHorizon();
        int numRuns = 1000;
        List<BanditAlgorithm> banditAlgorithms = AlgorithmFactory.createAlgorithms(n);

        BanditRequest request = BanditFactory.getFromExperimentFrontendDataType(experimentRequest);
        StochasticBandit bandit = BanditFactory.createBandit(request);

        cloudWatchProxyService.runExperiment(banditAlgorithms, bandit, numRuns, n, headers);

        // Respond with a success response and the processed data
        return ResponseEntity.ok("OK");
    }

}
