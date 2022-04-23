package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbCompile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompileRepository extends JpaRepository<DbCompile, Long> {
    Optional<DbCompile> findByDbCondition_DbExercise_IdAndDbUser_Id(Long dbCondition_dbExercise_id, Long dbUser_id);

    Optional<DbCompile> findByDbCondition_IdAndDbUser_Id(Long dbCondition_id, Long dbUser_id);
}
