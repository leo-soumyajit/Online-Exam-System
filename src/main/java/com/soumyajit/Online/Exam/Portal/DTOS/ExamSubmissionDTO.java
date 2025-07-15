package com.soumyajit.Online.Exam.Portal.DTOS;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubmissionDTO {
    private Long examId;
    private Map<Long, String> answers;
}
