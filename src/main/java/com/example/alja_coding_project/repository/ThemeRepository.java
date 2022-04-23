package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<DbTheme, Long> {
    List<DbTheme> findAllByDbProgramLang_Id(Long dbProgramLang_id);

    boolean existsByNameAndDbProgramLang_Id(String name, Long dbProgramLang_id);

    boolean existsByNameAndDbProgramLang_IdAndIdNot(String name, Long dbProgramLang_id, Long id);
}
