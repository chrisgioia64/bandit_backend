package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.math3.analysis.function.Exp;
import org.example.algorithm.BanditAlgorithm;
import org.example.aws.MyS3Service;
import org.example.aws.PublishTextSMS;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditExperiment;
import org.example.controller.dto.ExperimentDto;
import org.example.model.*;
import org.example.repository.AnalysisDataPointRepository;
import org.example.repository.BanditRepository;
import org.example.repository.ExperimentParameterRepository;
import org.example.strategy.ExperimentRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void runExperiments(List<BanditAlgorithm> banditAlgorithms, StochasticBandit bandit,
                               int numRuns, int n) {
        // Step 1 -- Save bandit
        BanditEntity banditEntity = EntityFactory.createBanditEntity(bandit);
        banditRepository.save(banditEntity);

        // Loop through each algorithm
        List<AnalysisDataPointEntity> totalEntites = new LinkedList<>();
        for (BanditAlgorithm banditAlgorithm : banditAlgorithms) {
            ExperimentParameterEntity experimentParameterEntity = new ExperimentParameterEntity();
            experimentParameterEntity.setBandit(banditEntity);
            AlgorithmEntity algorithmEntity = EntityFactory.createAlgorithmEntity(banditAlgorithm);
            experimentParameterEntity.setAlgorithm(algorithmEntity);
            experimentParameterRepository.save(experimentParameterEntity);
            StochasticBanditExperiment experiment = experimentRunner.getExperiment(banditAlgorithm, numRuns, n, bandit);
            List<AnalysisDataPointEntity> entities = EntityFactory.createEntity(experiment, experimentParameterEntity, n);
            totalEntites.addAll(entities);
//            analysisDataPointRepository.saveAll(entities);
        }
        saveAllInOneTransaction(totalEntites);
    }

    public void saveAllInOneTransaction(List<AnalysisDataPointEntity> entities) {

        String sql = "INSERT INTO analysis_data_point_entity (cumulative_regret, experiment_id, percent_optimal, " +
                "time_step, variance_regret) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, entities, 50, (ps, entity) -> {
            ps.setDouble(1, entity.getCumulativeRegret());
            ps.setLong(2, entity.getExperimentParameter().getId());
            ps.setDouble(3, entity.getPercentOptimal());
            ps.setInt(4, entity.getTimeStep());
            ps.setDouble(5, entity.getVarianceRegret());
        });

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

    public List<BanditEntity> getExperiments() {
        List<BanditEntity> bandits = banditRepository.findAll();
//        List<AnalysisDataPointEntity> datapoints = analysisDataPointRepository.findAll();
//        return getExperiments(datapoints);
        return bandits;
    }

    public ExperimentDto getExperimentById(long id) {
        List<AnalysisDataPointEntity> datapoints = analysisDataPointRepository.getDatapointsByExperimentId(id);
        Map<Long, ExperimentDto> map = getExperiments(datapoints);
        return map.get(id);
    }

    public Map<Long, ExperimentDto> getExperiments(List<AnalysisDataPointEntity> datapoints) {
        Map<Long, ExperimentDto> map = new HashMap<>();
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
