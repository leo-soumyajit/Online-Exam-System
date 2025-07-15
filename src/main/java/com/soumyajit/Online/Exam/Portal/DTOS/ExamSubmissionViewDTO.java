package com.soumyajit.Online.Exam.Portal.DTOS;


import lombok.Data;

import java.util.Map;

@Data
public class ExamSubmissionViewDTO {
    private String studentName;
    private String studentRegNumber;
    private Long examId;
    private String examTitle;
    private Map<Long, String> answers; // questionId -> selected answer
    private boolean submitted;
}
