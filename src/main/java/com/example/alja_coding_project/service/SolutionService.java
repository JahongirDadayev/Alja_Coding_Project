package com.example.alja_coding_project.service;

import com.example.alja_coding_project.entity.DbCondition;
import com.example.alja_coding_project.entity.DbSolution;
import com.example.alja_coding_project.payload.SolutionDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.ConditionRepository;
import com.example.alja_coding_project.repository.ExerciseRepository;
import com.example.alja_coding_project.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SolutionService {
    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    ConditionRepository conditionRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    public DbSolution getConditionSolution(Long exerciseId) {
        Optional<DbSolution> optionalDbSolution = solutionRepository.findByDbCondition_DbExercise_Id(exerciseId);
        return optionalDbSolution.orElse(null);
    }

    public ApiResponse postSolution(SolutionDto solutionDto) {
        return saveInformation(new DbSolution(), solutionDto, "Saved solution information");
    }

    public ApiResponse updateSolution(Long id, SolutionDto solutionDto) {
        Optional<DbSolution> optionalDbSolution = solutionRepository.findById(id);
        return optionalDbSolution.map(solution -> saveInformation(solution, solutionDto, "Update solution information")).orElseGet(() -> new ApiResponse("No solution matching the id you entered", null, false));
    }

    public ApiResponse deleteSolution(Long id) {
        Optional<DbSolution> optionalDbSolution = solutionRepository.findById(id);
        if (optionalDbSolution.isPresent()) {
            try {
                solutionRepository.delete(optionalDbSolution.get());
                return new ApiResponse("Delete solution information", null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("The data was not deleted due to errors", null, false);
            }
        } else {
            return new ApiResponse("No solution matching the id you entered", null, false);
        }
    }

    private ApiResponse saveInformation(DbSolution solution, SolutionDto solutionDto, String text) {
        Optional<DbCondition> optionalDbCondition = conditionRepository.findById(solutionDto.getConditionId());
        if (optionalDbCondition.isPresent()) {
            solution.setCode(solutionDto.getCode());
            solution.setParameters(solutionDto.getParameters());
            solution.setDbCondition(optionalDbCondition.get());
            solutionRepository.save(solution);
            return new ApiResponse(text, solution, true);
        } else {
            return new ApiResponse("No condition matching the id you entered was found", null, false);
        }
    }
}
