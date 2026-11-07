package com.ixtager.javarunner.controller;

import com.ixtager.javarunner.request.SendCodeRequest;
import com.ixtager.javarunner.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/code")
public class CodeController {
    private final CodeService codeService;

    @GetMapping
    public ResponseEntity<String> getDefaultCode() {
        return ResponseEntity.ok("class Main {\n" +
                "    public static void main(String[] args) {\n " +
                "       System.out.println(\"Hello World!\");\n" +
                "    }\n" +
                "}");
    }

    @PostMapping
    public ResponseEntity<String> sendCode(@RequestBody SendCodeRequest sendCodeRequest) {
        String code = sendCodeRequest.getCode();
        String result = codeService.saveAndRun(code);
        return ResponseEntity.ok(result);
    }
}
