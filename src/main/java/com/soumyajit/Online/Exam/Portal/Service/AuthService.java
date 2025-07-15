package com.soumyajit.Online.Exam.Portal.Service;

import com.soumyajit.Online.Exam.Portal.Entities.Admin;
import com.soumyajit.Online.Exam.Portal.Entities.Student;
import com.soumyajit.Online.Exam.Portal.Repository.AdminRepository;
import com.soumyajit.Online.Exam.Portal.Repository.StudentRepository;
import com.soumyajit.Online.Exam.Portal.Security.JwtService;
import com.soumyajit.Online.Exam.Portal.DTOS.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepo;
    private final AdminRepository adminRepo;
    private final JwtService jwtService;

    public AuthResponse studentLogin(String regNumber, String dob) {
        Student student = studentRepo.findByRegNumberAndDob(regNumber, dob)
                .orElseThrow(() -> new RuntimeException("Invalid student credentials"));

        String token = jwtService.generateToken(regNumber, "STUDENT");
        return new AuthResponse(token, "STUDENT"); // Include role here
    }

    public AuthResponse adminLogin(String username, String password) {
        Admin admin = adminRepo.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid admin credentials"));

        String token = jwtService.generateToken(username, "ADMIN");
        return new AuthResponse(token, "ADMIN"); // Include role here
    }

}
