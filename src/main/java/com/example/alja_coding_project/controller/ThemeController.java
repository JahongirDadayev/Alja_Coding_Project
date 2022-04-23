package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbTheme;
import com.example.alja_coding_project.payload.ThemeDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/theme")
public class ThemeController {
    @Autowired
    ThemeService themeService;

    @GetMapping
    public ResponseEntity<List<DbTheme>> getProgramLangThemes(@RequestParam Long programLangId, @RequestParam(defaultValue = "0") Long userId) {
        List<DbTheme> themeList = themeService.getProgramLangThemes(programLangId, userId);
        return (themeList != null) ? ResponseEntity.status(HttpStatus.OK).body(themeList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postTheme(@RequestBody ThemeDto themeDto) {
        ApiResponse apiResponse = themeService.postTheme(themeDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateTheme(@PathVariable Long id, @RequestBody ThemeDto themeDto) {
        ApiResponse apiResponse = themeService.updateTheme(id, themeDto);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteTheme(@PathVariable Long id) {
        ApiResponse apiResponse = themeService.deleteTheme(id);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
