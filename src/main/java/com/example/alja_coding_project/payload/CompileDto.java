package com.example.alja_coding_project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompileDto {
    private String code;

    private Long conditionId;

    private Long userId;
}
