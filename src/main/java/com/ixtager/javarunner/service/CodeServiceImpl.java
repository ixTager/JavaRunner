package com.ixtager.javarunner.service;

import com.ixtager.javarunner.util.CodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final CodeUtil codeUtil;

    @Override
    public String saveAndRun(String code) {
        try {
            codeUtil.compileAndRun(code);

            return codeUtil.compileAndRun(code);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Catching error while sending code: " + e.getMessage();
        }
    }
}
