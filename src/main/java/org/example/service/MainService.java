package org.example.service;

import org.example.model.AnalysisDataPointEntity;
import org.example.model.ExperimentParameterEntity;
import org.example.repository.AnalysisDataPointRepository;
import org.example.repository.ExperimentParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    private AnalysisDataPointRepository analysisDataPointRepository;

    @Autowired
    private ExperimentParameterRepository experimentParameterRepository;

    public void saveAllDatapoints(List<AnalysisDataPointEntity> entities) {
        ExperimentParameterEntity experimentParameter = entities.get(0).getExperimentParameter();
        experimentParameterRepository.save(experimentParameter);
        analysisDataPointRepository.saveAll(entities);
    }

}
