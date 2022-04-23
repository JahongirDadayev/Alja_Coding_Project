package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbProgramLang;
import com.example.alja_coding_project.payload.ProgramLangDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.ProgramLangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/programLang")
public class ProgramLangController {
    @Autowired
    ProgramLangService programLangService;

    @GetMapping
    public ResponseEntity<List<DbProgramLang>> getProgramLang() {
        List<DbProgramLang> programLangList = programLangService.getProgramLang();
        return ResponseEntity.status(HttpStatus.OK).body(programLangList);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postProgramLang(@RequestBody ProgramLangDto programLangDto) {
        ApiResponse apiResponse = programLangService.postProgramLang(programLangDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateProgramLang(@PathVariable Long id, @RequestBody ProgramLangDto programLangDto) {
        ApiResponse apiResponse = programLangService.updateProgramLang(id, programLangDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteProgramLang(@PathVariable Long id) {
        ApiResponse apiResponse = programLangService.deleteProgramLang(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
