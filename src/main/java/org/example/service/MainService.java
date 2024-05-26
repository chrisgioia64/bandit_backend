package org.example.service;

import org.example.algorithm.BanditAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditExperiment;
import org.example.model.*;
import org.example.repository.AnalysisDataPointRepository;
import org.example.repository.BanditRepository;
import org.example.repository.ExperimentParameterRepository;
import org.example.strategy.ExperimentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class MainService {

    @Autowired
    private AnalysisDataPointRepository analysisDataPointRepository;

    @Autowired
    private ExperimentParameterRepository experimentParameterRepository;

    @Autowired
    private BanditRepository banditRepository;

    @Autowired
    private ExperimentRunner experimentRunner;

    public void saveAllDatapoints(List<AnalysisDataPointEntity> entities) {
        ExperimentParameterEntity experimentParameter = entities.get(0).getExperimentParameter();
        experimentParameterRepository.save(experimentParameter);
        analysisDataPointRepository.saveAll(entities);
    }

    public void runExperiments(List<BanditAlgorithm> banditAlgorithms, StochasticBandit bandit,
                               int numRuns, int n) {
        // Step 1 -- Save bandit
        BanditEntity banditEntity = EntityFactory.createBanditEntity(bandit);
        banditRepository.save(banditEntity);

        // Loop through each algorithm
        for (BanditAlgorithm banditAlgorithm : banditAlgorithms) {
            ExperimentParameterEntity experimentParameterEntity = new ExperimentParameterEntity();
            experimentParameterEntity.setBandit(banditEntity);
            AlgorithmEntity algorithmEntity = EntityFactory.createAlgorithmEntity(banditAlgorithm);
            experimentParameterEntity.setAlgorithm(algorithmEntity);
            System.out.println(experimentParameterEntity);
            experimentParameterRepository.save(experimentParameterEntity);
            StochasticBanditExperiment experiment = experimentRunner.getExperiment(banditAlgorithm, numRuns, n, bandit);
            List<AnalysisDataPointEntity> entities = EntityFactory.createEntity(experiment, experimentParameterEntity, n);
            analysisDataPointRepository.saveAll(entities);
        }
    }

}
