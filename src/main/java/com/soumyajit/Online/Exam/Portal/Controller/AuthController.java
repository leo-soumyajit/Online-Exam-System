package com.soumyajit.Online.Exam.Portal.Controller;

import com.soumyajit.Online.Exam.Portal.Service.AuthService;
import com.soumyajit.Online.Exam.Portal.DTOS.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/student-login")
    public AuthResponse studentLogin(@RequestParam String regNumber, @RequestParam String dob) {
        return authService.studentLogin(regNumber, dob);
    }

    @PostMapping("/admin-login")
    public AuthResponse adminLogin(@RequestParam String username, @RequestParam String password) {
        return authService.adminLogin(username, password);
    }
}
