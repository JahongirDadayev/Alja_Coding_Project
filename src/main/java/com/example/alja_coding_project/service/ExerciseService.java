package com.example.alja_coding_project.service;

import com.example.alja_coding_project.entity.*;
import com.example.alja_coding_project.payload.ConditionDto;
import com.example.alja_coding_project.payload.ExerciseDto;
import com.example.alja_coding_project.payload.SolutionDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    CompileRepository compileRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    ConditionRepository conditionRepository;

    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    ConditionService conditionService;

    @Autowired
    SolutionService solutionService;

    @Autowired
    UserRepository userRepository;

    public List<DbExercise> getThemeExercises(Long themeId, Long userId) {
        if (!themeRepository.existsById(themeId) || (userId == 0) || userRepository.existsById(userId)) {
            List<DbExercise> exercises = exerciseRepository.findAllByDbTheme_Id(themeId);
            return generationCorrect(exercises, userId);
        } else {
            return null;
        }
    }
    
    public ApiResponse postExercise(ExerciseDto exerciseDto) {
        if (!exerciseRepository.existsByNameAndDbTheme_Id(exerciseDto.getName(), exerciseDto.getThemeId())) {
            Optional<DbTheme> optionalDbTheme = themeRepository.findById(exerciseDto.getThemeId());
            if (optionalDbTheme.isPresent()) {
                DbExercise exercise = new DbExercise();
                exercise.setName(exerciseDto.getName());
                exercise.setDbTheme(optionalDbTheme.get());
                exerciseRepository.save(exercise);
                ConditionDto conditionDto = new ConditionDto(exerciseDto.getText(), exerciseDto.getCode(), exercise.getId());
                ApiResponse conditionApiResponse = conditionService.postCondition(conditionDto);
                if (conditionApiResponse.isSuccess()) {
                    SolutionDto solutionDto = new SolutionDto(exerciseDto.getSolutionCode(), exerciseDto.getParameters(), ((DbCondition) conditionApiResponse.getObject()).getId());
                    ApiResponse solutionApiResponse = solutionService.postSolution(solutionDto);
                    if (solutionApiResponse.isSuccess()) {
                        return new ApiResponse("Saved exercise information", exercise, true);
                    } else {
                        exerciseRepository.delete(exercise);
                        conditionRepository.delete((DbCondition) conditionApiResponse.getObject());
                        return solutionApiResponse;
                    }
                } else {
                    exerciseRepository.delete(exercise);
                    return conditionApiResponse;
                }
            } else {
                return new ApiResponse("No theme matching the id you entered", null, false);
            }
        } else {
            return new ApiResponse("The exercise you are entering is included before", null, false);
        }
    }

    public ApiResponse updateExercise(Long id, ExerciseDto exerciseDto) throws CloneNotSupportedException {
        Optional<DbExercise> optionalDbExercise = exerciseRepository.findById(id);
        if (optionalDbExercise.isPresent()) {
            if (!exerciseRepository.existsByNameAndDbTheme_IdAndIdNot(exerciseDto.getName(), exerciseDto.getThemeId(), id)) {
                Optional<DbTheme> optionalDbTheme = themeRepository.findById(exerciseDto.getThemeId());
                if (optionalDbTheme.isPresent()) {
                    DbExercise exercise = optionalDbExercise.get();
                    DbExercise oldExercise = (DbExercise) exercise.clone();
                    exercise.setName(exerciseDto.getName());
                    exercise.setDbTheme(optionalDbTheme.get());
                    exerciseRepository.save(exercise);
                    ConditionDto conditionDto = new ConditionDto(exerciseDto.getText(), exerciseDto.getCode(), exercise.getId());
                    Optional<DbCondition> optionalDbCondition = conditionRepository.findByDbExercise_Id(id);
                    if (optionalDbCondition.isPresent()) {
                        DbCondition condition = optionalDbCondition.get();
                        DbCondition oldCondition = (DbCondition) condition.clone();
                        ApiResponse conditionApiResponse = conditionService.updateCondition(condition.getId(), conditionDto);
                        if (conditionApiResponse.isSuccess()) {
                            SolutionDto solutionDto = new SolutionDto(exerciseDto.getSolutionCode(), exerciseDto.getParameters(), ((DbCondition) conditionApiResponse.getObject()).getId());
                            Optional<DbSolution> optionalDbSolution = solutionRepository.findByDbCondition_Id(((DbCondition) conditionApiResponse.getObject()).getId());
                            if (optionalDbSolution.isPresent()) {
                                ApiResponse solutionApiResponse = solutionService.updateSolution(optionalDbSolution.get().getId(), solutionDto);
                                if (solutionApiResponse.isSuccess()) {
                                    return new ApiResponse("Update exercise information", exercise, true);
                                } else {
                                    exerciseRepository.save(oldExercise);
                                    conditionRepository.save(oldCondition);
                                    return solutionApiResponse;
                                }
                            } else {
                                exerciseRepository.save(oldExercise);
                                conditionRepository.save(oldCondition);
                                return new ApiResponse("No solution found for the exercise you entered", null, false);
                            }
                        } else {
                            exerciseRepository.save(oldExercise);
                            return conditionApiResponse;
                        }
                    } else {
                        return new ApiResponse("No condition found for the exercise you entered", null, false);
                    }
                } else {
                    return new ApiResponse("No theme matching the id you entered", null, false);
                }
            } else {
                return new ApiResponse("The exercise you are entering is included before", null, false);
            }
        } else {
            return new ApiResponse("No exercise matching the id you entered", null, false);
        }
    }

    public ApiResponse deleteExercise(Long id) {
        if (exerciseRepository.existsById(id)) {
            try {
                Optional<DbSolution> optionalDbSolution = solutionRepository.findByDbCondition_DbExercise_Id(id);
                if (optionalDbSolution.isPresent()) {
                    solutionRepository.delete(optionalDbSolution.get());
                    return new ApiResponse("Delete exercise information", null, true);
                } else {
                    return new ApiResponse("No information found", null, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("The data was not deleted due to errors", null, false);
            }
        } else {
            return new ApiResponse("No exercise matching the id you entered", null, false);
        }
    }

    private List<DbExercise> generationCorrect(List<DbExercise> exercises, Long userId) {
        for (DbExercise exercise : exercises) {
            Optional<DbCompile> optionalDbCompile = compileRepository.findByDbCondition_DbExercise_IdAndDbUser_Id(exercise.getId(), userId);
            if (optionalDbCompile.isPresent()) {
                DbCompile compile = optionalDbCompile.get();
                List<DbSingularCheck> singularChecks = compile.getDbSingularChecks();
                int check = 0;
                for (DbSingularCheck singularCheck : singularChecks) {
                    if (singularCheck.getIsChecked()) {
                        check++;
                    }
                }
                exercise.setCorrect(singularChecks.size() == check);
            } else {
                exercise.setCorrect(false);
            }
            exerciseRepository.save(exercise);
        }
        return exercises;
    }
}
