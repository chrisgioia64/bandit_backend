package org.example.controller.dto;

import org.example.model.AnalysisDataPointEntity;
import org.example.model.BanditEntity;
import org.example.model.ExperimentParameterEntity;

import java.util.List;
import java.util.Map;

public class ExperimentDto {

    private BanditEntity banditEntity;

    private Map<Long, List<AnalysisDataPointEntity>> dataPointMaps;

    public BanditEntity getBanditEntity() {
        return banditEntity;
    }

    public void setBanditEntity(BanditEntity banditEntity) {
        this.banditEntity = banditEntity;
    }

    public Map<Long, List<AnalysisDataPointEntity>> getDataPointMaps() {
        return dataPointMaps;
    }

    public void setDataPointMaps(Map<Long, List<AnalysisDataPointEntity>> dataPointMaps) {
        this.dataPointMaps = dataPointMaps;
    }
}
