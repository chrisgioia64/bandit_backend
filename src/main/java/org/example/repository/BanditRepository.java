package org.example.repository;

import org.example.bandit.StochasticBandit;
import org.example.model.BanditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanditRepository extends JpaRepository<BanditEntity, Long> {
}
