package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbCondition;
import com.example.alja_coding_project.payload.ConditionDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/condition")
public class ConditionController {
    @Autowired
    ConditionService conditionService;

    @GetMapping
    public ResponseEntity<DbCondition> getExerciseCondition(@RequestParam Long exerciseId, @RequestParam Long userId) {
        DbCondition condition = conditionService.getExerciseCondition(exerciseId, userId);
        return (condition != null) ? ResponseEntity.status(HttpStatus.OK).body(condition) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCondition(@RequestBody ConditionDto conditionDto) {
        ApiResponse apiResponse = conditionService.postCondition(conditionDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateCondition(@PathVariable Long id, @RequestBody ConditionDto conditionDto) {
        ApiResponse apiResponse = conditionService.updateCondition(id, conditionDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteCondition(@PathVariable Long id) {
        ApiResponse apiResponse = conditionService.deleteCondition(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
