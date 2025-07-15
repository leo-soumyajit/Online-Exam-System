package com.soumyajit.Online.Exam.Portal.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
}
