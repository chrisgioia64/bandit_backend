package org.example.model;

import org.example.algorithm.BanditAlgorithm;
import org.example.bandit.StochasticBandit;
import org.example.bandit.StochasticBanditExperiment;
import org.example.distribution.Distribution;

import java.util.LinkedList;
import java.util.List;

public class EntityFactory {

    public static BanditEntity createBanditEntity(StochasticBandit bandit) {
        BanditEntity banditEntity = new BanditEntity();
        for (Distribution arm : bandit.getArms()) {
            DistributionEntity distributionEntity = new DistributionEntity();
            distributionEntity.setDistributionName(arm.getDistributionName());
            distributionEntity.setParameters(arm.getDistributionParameters());
            banditEntity.getDistributions().add(distributionEntity);
        }
        return banditEntity;
    }

    public static AlgorithmEntity createAlgorithmEntity(BanditAlgorithm algorithm) {
        AlgorithmEntity algorithmEntity = new AlgorithmEntity();
        algorithmEntity.setAlgorithmName(algorithm.getAlgorithmName());
        algorithmEntity.setParameters(algorithm.getAlgorithmParameters());

        return algorithmEntity;
    }

    public static List<AnalysisDataPointEntity> createEntity(
            StochasticBanditExperiment experiment, ExperimentParameterEntity experimentInfo,
            int n) {
        List<AnalysisDataPointEntity> datapoints = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            AnalysisDataPointEntity entity = new AnalysisDataPointEntity();
            entity.setExperimentParameter(experimentInfo);
            entity.setTimeStep(i + 1);
            entity.setCumulativeRegret(experiment.getCumulativeMeanRegret()[i]);
            entity.setPercentOptimal(experiment.getPercentOptimal()[i]);
            entity.setVarianceRegret(experiment.getCumulativeVarianceRegret()[i]);
            datapoints.add(entity);
        }
        return datapoints;
    }

}
