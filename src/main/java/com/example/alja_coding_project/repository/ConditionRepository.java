package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConditionRepository extends JpaRepository<DbCondition, Long> {
    Optional<DbCondition> findByDbExercise_Id(Long dbExercise_id);
}
