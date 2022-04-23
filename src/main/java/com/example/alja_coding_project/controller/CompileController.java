package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbCompile;
import com.example.alja_coding_project.payload.CompileDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.CompileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/compile")
public class CompileController {
    @Autowired
    CompileService compileService;

    @GetMapping
    public ResponseEntity<DbCompile> getCompile(@RequestParam Long exerciseId, @RequestParam Long userId) {
        DbCompile compile = compileService.getCompile(exerciseId, userId);
        return (compile != null) ? ResponseEntity.status(HttpStatus.OK).body(compile) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCompile(@RequestBody CompileDto compileDto) throws Exception {
        ApiResponse apiResponse = compileService.postCompile(compileDto);
        return (apiResponse.isSuccess())?ResponseEntity.status(HttpStatus.CREATED).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
