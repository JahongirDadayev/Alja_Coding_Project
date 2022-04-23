package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<DbExercise, Long> {
    boolean existsByNameAndDbTheme_Id(String name, Long dbTheme_id);

    boolean existsByNameAndDbTheme_IdAndIdNot(String name, Long dbTheme_id, Long id);

    List<DbExercise> findAllByDbTheme_Id(Long dbTheme_id);
}
