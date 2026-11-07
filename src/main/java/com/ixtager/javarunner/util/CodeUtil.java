package com.ixtager.javarunner.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CodeUtil {
    @Value("${sandbox.dir}")
    private String sandboxDir;

    public String compileAndRun(String code) throws IOException, InterruptedException {
        Path sandbox = Path.of(sandboxDir).toAbsolutePath().normalize();
        Files.createDirectories(sandbox);

        Path runDir = Files.createTempDirectory(sandbox, "run-");
        try {
            Path src = runDir.resolve("Main.java");
            Files.writeString(src, code);

            ProcessBuilder pb = new ProcessBuilder("java", src.toString());
            pb.directory(runDir.toFile());
            pb.redirectErrorStream(true);

            Process process = pb.start();

            String output = readWithLimit(process.getInputStream());

            boolean finished = process.waitFor(10, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return "Timeout: execution exceeded 10s";
            }

            return output.isEmpty() ? "Successfully" : output;
        } finally {
            deleteRecursively(runDir);
        }
    }

    private String readWithLimit(InputStream in) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(in))) {
            String result = reader.lines().collect(Collectors.joining("\n"));
            return result.length() > 100000 ? result.substring(0, 100000) + "\n...truncated" : result;
        }
    }

    private void deleteRecursively(Path dir) {
        try (var walk = Files.walk(dir)) {
            walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                try { Files.deleteIfExists(p); } catch (IOException ignored) {}
            });
        } catch (IOException ignored) {}
    }
}
