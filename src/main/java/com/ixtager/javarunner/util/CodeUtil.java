package com.ixtager.javarunner.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class CodeUtil {
    @Value("${main.file.path}")
    private String mainFilePath;

    public void sendCodeToMainJava(String code) throws IOException {
        Path path = Path.of(mainFilePath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        Files.writeString(path, code);
    }

    public String runCodeMainJava() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("java", mainFilePath);

        pb.redirectErrorStream(true);

        Process process = pb.start();

        String output;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.lines().collect(Collectors.joining("\n"));
        }

        process.waitFor();

        return output.isEmpty() ? "Successfully" : output;
    }
}
