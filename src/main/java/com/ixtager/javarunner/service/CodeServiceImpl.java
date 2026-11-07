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
            codeUtil.sendCodeToMainJava(code);

            return codeUtil.runCodeMainJava();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Catching error while sending code: " + e.getMessage();
        }
    }
}
