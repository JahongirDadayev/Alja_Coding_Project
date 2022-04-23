package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbExercise;
import com.example.alja_coding_project.payload.ExerciseDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<DbExercise>> getThemeExercises(@RequestParam Long themeId, @RequestParam(defaultValue = "0") Long userId) {
        List<DbExercise> exerciseList = exerciseService.getThemeExercises(themeId, userId);
        return (exerciseList != null) ? ResponseEntity.status(HttpStatus.OK).body(exerciseList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postExercise(@RequestBody ExerciseDto exerciseDto) {
        ApiResponse apiResponse = exerciseService.postExercise(exerciseDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateExercise(@PathVariable Long id, @RequestBody ExerciseDto exerciseDto) throws CloneNotSupportedException {
        ApiResponse apiResponse = exerciseService.updateExercise(id, exerciseDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteExercise(@PathVariable Long id){
        ApiResponse apiResponse = exerciseService.deleteExercise(id);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
