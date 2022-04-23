package com.example.alja_coding_project.service;

import com.example.alja_coding_project.entity.DbExercise;
import com.example.alja_coding_project.entity.DbProgramLang;
import com.example.alja_coding_project.entity.DbTheme;
import com.example.alja_coding_project.payload.ThemeDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.ExerciseRepository;
import com.example.alja_coding_project.repository.ProgramLangRepository;
import com.example.alja_coding_project.repository.ThemeRepository;
import com.example.alja_coding_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    ProgramLangRepository programLangRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseService exerciseService;

    public List<DbTheme> getProgramLangThemes(Long programLangId, Long userId) {
        if (!programLangRepository.existsById(programLangId) || (userId == 0) || userRepository.existsById(userId)) {
            List<DbTheme> themes = themeRepository.findAllByDbProgramLang_Id(programLangId);
            return generationStarsRating(themes, userId);
        } else {
            return null;
        }
    }

    public ApiResponse postTheme(ThemeDto themeDto) {
        if (!themeRepository.existsByNameAndDbProgramLang_Id(themeDto.getName(), themeDto.getProgramLangId())) {
            return saveInformation(new DbTheme(), themeDto, "Saved theme information");
        } else {
            return new ApiResponse("The theme you are entering is already included", null, false);
        }
    }

    public ApiResponse updateTheme(Long id, ThemeDto themeDto) {
        Optional<DbTheme> optionalDbTheme = themeRepository.findById(id);
        if (optionalDbTheme.isPresent()) {
            if (!themeRepository.existsByNameAndDbProgramLang_IdAndIdNot(themeDto.getName(), themeDto.getProgramLangId(), id)) {
                return saveInformation(optionalDbTheme.get(), themeDto, "Update theme information");
            } else {
                return new ApiResponse("The theme you are entering is already included", null, false);
            }
        } else {
            return new ApiResponse("No theme matching the id you entered", null, false);
        }
    }

    public ApiResponse deleteTheme(Long id) {
        if (themeRepository.existsById(id)) {
            try {
                themeRepository.deleteById(id);
                return new ApiResponse("Delete theme information", null, true);
            } catch (Exception e) {
                return new ApiResponse("The data was not deleted due to errors", null, false);
            }
        } else {
            return new ApiResponse("No theme matching the id you entered", null, false);
        }
    }

    private ApiResponse saveInformation(DbTheme theme, ThemeDto themeDto, String text) {
        Optional<DbProgramLang> optionalDbProgramLang = programLangRepository.findById(themeDto.getProgramLangId());
        if (optionalDbProgramLang.isPresent()) {
            theme.setName(themeDto.getName());
            theme.setDescription(themeDto.getDescription());
            theme.setDbProgramLang(optionalDbProgramLang.get());
            themeRepository.save(theme);
            return new ApiResponse(text, theme, true);
        } else {
            return new ApiResponse("No programming language matching the id you entered was found", null, false);
        }
    }

    private List<DbTheme> generationStarsRating(List<DbTheme> themes, Long userId) {
        for (DbTheme theme : themes) {
            List<DbExercise> exercises = exerciseService.getThemeExercises(theme.getId(), userId);
            if (exercises != null && exercises.size() != 0) {
                float checkCorrect = 0;
                for (DbExercise exercise : exercises) {
                    if (exercise.getCorrect()) {
                        checkCorrect++;
                    }
                }
                theme.setStarsRating(Float.parseFloat(new DecimalFormat("#.##").format((checkCorrect / exercises.size()) / 2 * 10)));
                themeRepository.save(theme);
            }
        }
        return themes;
    }
}
