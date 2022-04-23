package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbSolution;
import com.example.alja_coding_project.payload.SolutionDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/solution")
public class SolutionController {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    public ResponseEntity<DbSolution> getConditionSolution(@RequestParam Long exerciseId) {
        DbSolution solution = solutionService.getConditionSolution(exerciseId);
        return (solution != null) ? ResponseEntity.status(HttpStatus.OK).body(solution) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postSolution(@RequestBody SolutionDto solutionDto) {
        ApiResponse apiResponse = solutionService.postSolution(solutionDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateSolution(@PathVariable Long id, @RequestBody SolutionDto solutionDto) {
        ApiResponse apiResponse = solutionService.updateSolution(id, solutionDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteSolution(@PathVariable Long id) {
        ApiResponse apiResponse = solutionService.deleteSolution(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
