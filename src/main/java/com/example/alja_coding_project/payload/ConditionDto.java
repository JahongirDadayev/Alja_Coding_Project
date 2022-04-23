package com.example.alja_coding_project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionDto {
    private String text;

    private String code;

    private Long exerciseId;
}
