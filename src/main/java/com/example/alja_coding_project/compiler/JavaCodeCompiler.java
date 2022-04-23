package com.example.alja_coding_project.compiler;

import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JavaCodeCompiler {

    private String error;

    public List<Object> compileCode(String code, String className, String functionName, String myError) throws Exception {
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("src/main/resources/class").toURI().toURL()});
        error = myError;
        Class<?> helloWorld = compile(className, code, classLoader);
        List<Object> list = new ArrayList<>();
        list.add((helloWorld != null) ? helloWorld.getDeclaredMethod(functionName).invoke(helloWorld.newInstance()) : null);
        list.add(error);
        return list;
    }

    private Class<?> compile(String className, String sourceCode, URLClassLoader classLoader) throws Exception {
        return compileHelper(className, classLoader, Collections.singletonList(new JavaSourceFromString(className, sourceCode)));
    }

    private Class<?> compileHelper(String className, URLClassLoader classLoader, Iterable<? extends JavaFileObject> compilationUnits) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task;
        boolean success;
        List<String> options = new ArrayList<>(Arrays.asList("-d", "src/main/resources/class"));
        task = compiler.getTask(null, null, diagnostics, options, null, compilationUnits);
        success = task.call();
        if (success) {
            return Class.forName(className.replace(".", "/"), true, classLoader);
        } else {
            error = printDiagnostics(diagnostics);
            return null;
        }
    }

    private String printDiagnostics(DiagnosticCollector<JavaFileObject> diagnostics) {
        String message = "";
        for (Diagnostic<?> d : diagnostics.getDiagnostics()) {
            message = message.concat("Error: " + d + "\n");

        }
        return message;
    }
}

