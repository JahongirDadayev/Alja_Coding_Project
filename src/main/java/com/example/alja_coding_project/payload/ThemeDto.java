package com.example.alja_coding_project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private String name;

    private String description;

    private Long programLangId;
}
