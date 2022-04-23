package com.example.alja_coding_project.payload.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String text;

    private Object object;

    private boolean success;
}
