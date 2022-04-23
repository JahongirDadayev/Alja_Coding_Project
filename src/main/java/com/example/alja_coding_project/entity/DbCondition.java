package com.example.alja_coding_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DbCondition implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String code;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private DbExercise dbExercise;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
