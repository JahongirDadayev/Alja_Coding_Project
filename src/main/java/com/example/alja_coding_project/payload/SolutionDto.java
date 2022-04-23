package com.example.alja_coding_project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDto {
    private String code;

    //"private List<> list = new ArrayList<>(Arrays.asList(\"Jahongir\", \"Jahongir\"));"
    private String parameters;

    private Long conditionId;
}
