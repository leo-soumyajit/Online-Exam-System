package com.soumyajit.Online.Exam.Portal.DTOS;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionDTO {
    private String content;
    private List<String> options;
    private String correctAnswer;
    private Long examId;
}
