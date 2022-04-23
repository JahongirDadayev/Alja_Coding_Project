package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbProgramLang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramLangRepository extends JpaRepository<DbProgramLang, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
