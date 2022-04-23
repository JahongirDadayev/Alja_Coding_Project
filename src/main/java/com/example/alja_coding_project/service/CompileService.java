package com.example.alja_coding_project.service;

import com.example.alja_coding_project.compiler.JavaCodeCompiler;
import com.example.alja_coding_project.entity.*;
import com.example.alja_coding_project.payload.CompileDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompileService {
    @Autowired
    CompileRepository compileRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConditionRepository conditionRepository;

    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    JavaCodeCompiler javaCodeCompiler;

    public DbCompile getCompile(Long exerciseId, Long userId) {
        if (exerciseRepository.existsById(exerciseId) && userRepository.existsById(userId)) {
            Optional<DbCompile> optionalDbCompile = compileRepository.findByDbCondition_DbExercise_IdAndDbUser_Id(exerciseId, userId);
            return optionalDbCompile.orElse(null);
        } else {
            return null;
        }
    }

    public ApiResponse postCompile(CompileDto compileDto) throws Exception {
        Optional<DbCondition> optionalDbCondition = conditionRepository.findById(compileDto.getConditionId());
        if (optionalDbCondition.isPresent()) {
            Optional<DbSolution> optionalDbSolution = solutionRepository.findByDbCondition_Id(compileDto.getConditionId());
            if (optionalDbSolution.isPresent()) {
                DbCompile saveCompile = null;
                Optional<DbUser> optionalDbUser = userRepository.findById(compileDto.getUserId());
                if (optionalDbUser.isPresent()) {
                    Optional<DbCompile> optionalDbCompile = compileRepository.findByDbCondition_IdAndDbUser_Id(compileDto.getConditionId(), compileDto.getUserId());
                    saveCompile = optionalDbCompile.orElseGet(DbCompile::new);
                }
                DbSolution solution = optionalDbSolution.get();
                String solutionCode = solution.getCode();
                String solutionCode_1 = solutionCode.substring(0, solutionCode.indexOf("class"));
                String solutionCode_2 = solutionCode.substring(solutionCode.indexOf("class"));
                String code = compileDto.getCode();
                String code_1 = code.substring(0, code.indexOf("class"));
                String code_2 = code.substring(code.indexOf("class"));
                String parameter = solution.getParameters();
                String collectedCode = "import java.util.*;\n" + code_1 + "\n" + solutionCode_1 + "\n" + code_2 + "\n" + solutionCode_2 + "\n";
                collectedCode = collectedCode.concat(
                        "public class Compile {\n" +
                                parameter + "\n" +
                                "public List<Map<String, Object>> compile(){\n" +
                                "        List<Map<String, Object>> singularCheckList = new ArrayList<>();\n" +
                                "        Main main = new Main();\n" +
                                "        SolutionMain solutionMain = new SolutionMain();\n" +
                                "        for (int i = 0; i < list.size(); i++) {\n" +
                                "            Map<String, Object> singularCheck = new HashMap<>();\n" +
                                "            if (main.solution(list.get(i)).equals(solutionMain.solution(list.get(i)))){\n" +
                                "            singularCheck.put(\"isChecked\", true);\n" +
                                "            }else {\n" +
                                "            singularCheck.put(\"isChecked\", false);\n" +
                                "            }\n" +
                                "            singularCheck.put(\"run\", String.valueOf(main.solution(list.get(i))));\n" +
                                "            singularCheck.put(\"expected\", String.valueOf(solutionMain.solution(list.get(i))));\n" +
                                "            singularCheckList.add(singularCheck);\n" +
                                "        }\n" +
                                "        return singularCheckList;\n" +
                                "    }\n" +
                                "}\n");
                String error = null;
                List<DbSingularCheck> singularChecks = new ArrayList<>();
                System.out.println(collectedCode);
                List<Object> objects = javaCodeCompiler.compileCode(collectedCode, "Compile", "compile", error);
                List<Map<String, Object>> compileSingularCheckList = (List<Map<String, Object>>) objects.get(0);
                error = (String) objects.get(1);
                for (Map<String, Object> compileSingularCheck : compileSingularCheckList) {
                    singularChecks.add(parseSingularCheck(compileSingularCheck));
                }
                DbCompile compile = new DbCompile();
                compile.setCode(compileDto.getCode());
                compile.setDbCondition(optionalDbCondition.get());
                if (optionalDbUser.isPresent()) {
                    compile.setDbUser(optionalDbUser.get());
                } else {
                    compile.setDbUser(null);
                }
                if (error != null) {
                    compile.setDbSingularChecks(null);
                    compile.setError(error);
                } else {
                    compile.setDbSingularChecks(singularChecks);
                    compile.setError(null);
                }
                if (saveCompile != null) {
                    compile.setId(saveCompile.getId());
                    compileRepository.save(compile);
                    return new ApiResponse("Saved compile information", compile, true);
                } else {
                    return new ApiResponse("Not saved compile information", compile, true);
                }
            } else {
                return new ApiResponse("No solution matching the condition you entered", null, false);
            }
        } else {
            return new ApiResponse("Could not find condition that matches the id you entered", null, false);
        }
    }

    private DbSingularCheck parseSingularCheck(Map<String, Object> compileSingularCheck) {
        DbSingularCheck singularCheck = new DbSingularCheck();
        singularCheck.setRun((String) compileSingularCheck.get("run"));
        singularCheck.setExpected((String) compileSingularCheck.get("expected"));
        singularCheck.setIsChecked((Boolean) compileSingularCheck.get("isChecked"));
        return singularCheck;
    }
}
