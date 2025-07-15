package com.soumyajit.Online.Exam.Portal.DTOS;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionViewDTO {
    private Long id;
    private String content;
    private List<String> options;
}

