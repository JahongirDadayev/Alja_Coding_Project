package com.example.alja_coding_project.service;

import com.example.alja_coding_project.entity.DbCompile;
import com.example.alja_coding_project.entity.DbCondition;
import com.example.alja_coding_project.entity.DbExercise;
import com.example.alja_coding_project.payload.ConditionDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.CompileRepository;
import com.example.alja_coding_project.repository.ConditionRepository;
import com.example.alja_coding_project.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConditionService {
    @Autowired
    ConditionRepository conditionRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    CompileRepository compileRepository;

    public DbCondition getExerciseCondition(Long exerciseId, Long userId) {
        Optional<DbCondition> optionalDbCondition = conditionRepository.findByDbExercise_Id(exerciseId);
        if (optionalDbCondition.isPresent()) {
            DbCondition condition = optionalDbCondition.get();
            Optional<DbCompile> optionalDbCompile = compileRepository.findByDbCondition_IdAndDbUser_Id(condition.getId(), userId);
            if (optionalDbCompile.isPresent()) {
                DbCompile compile = optionalDbCompile.get();
                condition.setCode(compile.getCode());
                conditionRepository.save(condition);
            }
            return condition;
        } else {
            return null;
        }
    }

    public ApiResponse postCondition(ConditionDto conditionDto) {
        return saveInformation(new DbCondition(), conditionDto, "Saved condition information");
    }

    public ApiResponse updateCondition(Long id, ConditionDto conditionDto) {
        Optional<DbCondition> optionalDbCondition = conditionRepository.findById(id);
        return optionalDbCondition.map(condition -> saveInformation(condition, conditionDto, "Update condition information")).orElseGet(() -> new ApiResponse("No condition matching the id you entered was found", null, false));
    }

    public ApiResponse deleteCondition(Long id) {
        Optional<DbCondition> optionalDbCondition = conditionRepository.findById(id);
        if (optionalDbCondition.isPresent()) {
            try {
                conditionRepository.delete(optionalDbCondition.get());
                return new ApiResponse("Delete condition information", null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("The theme you are entering is already included", null, false);
            }
        } else {
            return new ApiResponse("No condition matching the id you entered", null, false);
        }
    }

    private ApiResponse saveInformation(DbCondition condition, ConditionDto conditionDto, String text) {
        Optional<DbExercise> optionalDbExercise = exerciseRepository.findById(conditionDto.getExerciseId());
        if (optionalDbExercise.isPresent()) {
            condition.setText(conditionDto.getText());
            condition.setCode(conditionDto.getCode());
            condition.setDbExercise(optionalDbExercise.get());
            conditionRepository.save(condition);
            return new ApiResponse(text, condition, true);
        } else {
            return new ApiResponse("No exercise matching the id you entered", null, false);
        }
    }
}
