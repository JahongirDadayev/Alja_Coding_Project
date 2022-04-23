package com.example.alja_coding_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"db_condition_id", "db_user_id"})})
public class DbCompile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @ManyToOne(optional = false)
    private DbCondition dbCondition;

    @ManyToOne(optional = false)
    private DbUser dbUser;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DbSingularCheck> dbSingularChecks;

    private String error;
}
