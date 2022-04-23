package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbSolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolutionRepository extends JpaRepository<DbSolution, Long> {
    Optional<DbSolution> findByDbCondition_Id(Long dbCondition_id);

    Optional<DbSolution> findByDbCondition_DbExercise_Id(Long dbCondition_dbExercise_id);
}
