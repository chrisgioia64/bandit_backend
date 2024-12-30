package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.algorithm.BanditAlgorithm;
import org.example.aws.APILogEntry;
import org.example.aws.CloudWatchLogEntry;
import org.example.aws.MyCloudwatchService;
import org.example.bandit.StochasticBandit;
import org.example.controller.dto.AlgorithmFactory;
import org.example.controller.dto.BanditFactory;
import org.example.controller.dto.ExperimentDto;
import org.example.controller.dto.ExperimentParameters;
import org.example.model.BanditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class CloudWatchProxyService {

    @Autowired
    private MainService mainService;

    @Autowired
    private MyCloudwatchService myCloudwatchService;

    public List<BanditEntity> getAllExperiments(Map<String, String> headers) {
        long start = System.currentTimeMillis();
        List<BanditEntity> bandits = mainService.getExperiments();
        long end = System.currentTimeMillis();
        double timeTaken = (end - start) / 1000.0;

        APILogEntry logEntry = new APILogEntry(CloudWatchLogEntry.CloudWatchLogType.API, CloudWatchLogEntry.Level.INFO,
                "API Call for /getExperiments", "Returned " + bandits.size() + " experiments");
        logEntry.setUserAgent(headers.get("user-agent"));
        logEntry.setSecondsTaken(timeTaken);

        try {
            myCloudwatchService.sendLogEvent(logEntry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return bandits;
    }

    public ExperimentDto getExperimentById(int id, Map<String, String> headers) {
        long start = System.currentTimeMillis();
        ExperimentDto dto = mainService.getExperimentById(id);
        long end = System.currentTimeMillis();
        double timeTaken = (end - start) / 1000.0;

        APILogEntry logEntry = new APILogEntry(CloudWatchLogEntry.CloudWatchLogType.API, CloudWatchLogEntry.Level.INFO,
                "API Call for /getExperimentById/" + id, "Bandit Distributions " + dto.getBanditEntity().toString());
        logEntry.setUserAgent(headers.get("user-agent"));
        logEntry.setSecondsTaken(timeTaken);

        try {
            myCloudwatchService.sendLogEvent(logEntry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return dto;
    }

    public void runExperiment(List<BanditAlgorithm> banditAlgorithms, StochasticBandit bandit,
                               int numRuns, int n, Map<String, String> headers) {
        long start = System.currentTimeMillis();
        mainService.runExperiments(banditAlgorithms, bandit, numRuns, n);
        long end = System.currentTimeMillis();
        double timeTaken = (end - start) / 1000.0;

        APILogEntry logEntry = new APILogEntry(CloudWatchLogEntry.CloudWatchLogType.API, CloudWatchLogEntry.Level.INFO,
                "API Call for /runExperiment/", "Bandit Distributions " + bandit.toString());
        logEntry.setUserAgent(headers.get("user-agent"));
        logEntry.setSecondsTaken(timeTaken);

        try {
            myCloudwatchService.sendLogEvent(logEntry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
