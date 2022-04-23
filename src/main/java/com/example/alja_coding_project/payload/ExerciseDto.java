package com.example.alja_coding_project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    private String name;

    private String text;

    private String code;

    private String solutionCode;

    private String parameters;

    private Long themeId;
}
