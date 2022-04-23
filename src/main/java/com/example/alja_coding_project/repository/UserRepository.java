package com.example.alja_coding_project.repository;

import com.example.alja_coding_project.entity.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DbUser, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<DbUser> findByEmailAndPassword(String email, String password);
}
