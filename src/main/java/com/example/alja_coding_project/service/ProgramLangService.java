package com.example.alja_coding_project.service;

import com.example.alja_coding_project.entity.DbProgramLang;
import com.example.alja_coding_project.payload.ProgramLangDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.ProgramLangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramLangService {
    @Autowired
    ProgramLangRepository programLangRepository;

    public List<DbProgramLang> getProgramLang() {
        return programLangRepository.findAll();
    }

    public ApiResponse postProgramLang(ProgramLangDto programLangDto) {
        if (!programLangRepository.existsByName(programLangDto.getName())) {
            return saveProgramLang(new DbProgramLang(), programLangDto, "Saved programing language information");
        } else {
            return new ApiResponse("The programming language you are entering is already added", null, false);
        }
    }

    public ApiResponse updateProgramLang(Long id, ProgramLangDto programLangDto) {
        Optional<DbProgramLang> optionalDbProgramLang = programLangRepository.findById(id);
        if (optionalDbProgramLang.isPresent()) {
            if (!programLangRepository.existsByNameAndIdNot(programLangDto.getName(), id)) {
                return saveProgramLang(optionalDbProgramLang.get(), programLangDto, "Update programming language information");
            } else {
                return new ApiResponse("The programming language you are entering is already added", null, false);
            }
        } else {
            return new ApiResponse("No programming language matching the id you entered was found", null, false);
        }
    }

    public ApiResponse deleteProgramLang(Long id) {
        if (programLangRepository.existsById(id)) {
            try {
                programLangRepository.deleteById(id);
                return new ApiResponse("Delete programming language information", null, true);
            }catch (Exception e){
                return new ApiResponse("The data was not deleted due to errors", null, false);
            }
        } else {
            return new ApiResponse("No programming language matching the id you entered was found", null, false);
        }
    }

    private ApiResponse saveProgramLang(DbProgramLang programLang, ProgramLangDto programLangDto, String text) {
        programLang.setName(programLangDto.getName());
        programLangRepository.save(programLang);
        return new ApiResponse(text, programLang, true);
    }
}
