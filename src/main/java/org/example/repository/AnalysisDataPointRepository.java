package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.model.AnalysisDataPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisDataPointRepository extends JpaRepository<AnalysisDataPointEntity, Long> {

    @Query(value = "SELECT a.* FROM analysis_data_point_entity a INNER JOIN experiment_parameter_entity e " +
            " ON a.experiment_id = e.id WHERE e.bandit_id = ?1",
        nativeQuery = true)
    List<AnalysisDataPointEntity> getDatapointsByExperimentId(long id);


}
