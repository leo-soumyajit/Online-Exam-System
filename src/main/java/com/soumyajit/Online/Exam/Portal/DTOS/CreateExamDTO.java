package com.soumyajit.Online.Exam.Portal.DTOS;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamDTO {
    private String title;
    private String accessToken;
    private int durationMinutes;
}
