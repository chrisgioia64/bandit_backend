package org.example.repository;

import org.example.model.ExperimentParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperimentParameterRepository extends JpaRepository<ExperimentParameterEntity, Long> {
}
