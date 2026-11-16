package com.example.securepass;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class PasswordController {

    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @GetMapping("/generate")
    public Map<String, String> generate(@RequestParam(defaultValue = "16") int length) {
        if (length < 8 || length > 64) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "length must be between 8 and 64");
        }
        return Map.of("password", service.generate(length));
    }

    @PostMapping("/check")
    public CheckResult check(@RequestBody CheckRequest request) {
        if (request.password() == null || request.password().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password is required");
        }
        return service.check(request.password());
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("name", "SecurePass API", "version", "1.0.0");
    }
}
