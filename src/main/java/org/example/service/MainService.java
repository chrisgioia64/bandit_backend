package org.example.service;

import org.example.algorithm.BanditAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditExperiment;
import org.example.controller.dto.ExperimentDto;
import org.example.model.*;
import org.example.repository.AnalysisDataPointRepository;
import org.example.repository.BanditRepository;
import org.example.repository.ExperimentParameterRepository;
import org.example.strategy.ExperimentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public Map<Long, List<ExperimentParameterEntity>> getAllExperiments() {
        List<ExperimentParameterEntity> entities = experimentParameterRepository.findAll();
        Map<Long, List<ExperimentParameterEntity>> result = new HashMap<>();
        for (ExperimentParameterEntity entity : entities) {
            long id = entity.getBandit().getId();
            List<ExperimentParameterEntity> list = result.getOrDefault(id, new LinkedList<>());
            list.add(entity);
            result.put(id, list);
        }
        return result;
    }

    public Map<Long, ExperimentDto> getExperiments() {
        Map<Long, ExperimentDto> map = new HashMap<>();
        List<AnalysisDataPointEntity> datapoints = analysisDataPointRepository.findAll();
        for (AnalysisDataPointEntity datapoint : datapoints) {
            ExperimentParameterEntity experimentParameter = datapoint.getExperimentParameter();
            BanditEntity banditEntity = experimentParameter.getBandit();
            long banditEntityId = banditEntity.getId();
            ExperimentDto dto = map.get(banditEntityId);
            if (dto == null) {
                dto = new ExperimentDto();
                dto.setBanditEntity(banditEntity);
                dto.setDataPointMaps(new HashMap<>());
            }
            long algorithmId = experimentParameter.getAlgorithm().getId();
            List<AnalysisDataPointEntity> list = dto.getDataPointMaps().getOrDefault(algorithmId, new LinkedList<>());
            list.add(datapoint);
            dto.getDataPointMaps().put(algorithmId, list);
            map.put(banditEntityId, dto);
        }
        return map;
    }



}
