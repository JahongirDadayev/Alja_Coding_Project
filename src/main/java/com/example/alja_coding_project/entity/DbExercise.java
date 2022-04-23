package com.example.alja_coding_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "db_theme_id"})})
public class DbExercise implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private DbTheme dbTheme;

    private Boolean correct = false;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
