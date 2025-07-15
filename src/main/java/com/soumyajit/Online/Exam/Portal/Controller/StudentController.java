package com.soumyajit.Online.Exam.Portal.Controller;

import com.soumyajit.Online.Exam.Portal.DTOS.ExamSubmissionDTO;
import com.soumyajit.Online.Exam.Portal.DTOS.QuestionViewDTO;
import com.soumyajit.Online.Exam.Portal.Entities.Exam;
import com.soumyajit.Online.Exam.Portal.Service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final ExamService examService;

    @PostMapping("/join-exam")
    public Exam joinExam(@RequestParam String accessToken, Authentication authentication) {
        String regNumber = authentication.getName(); // Extracted from JWT
        return examService.joinExam(regNumber, accessToken);
    }

    @PostMapping("/submit-exam")
    public String submitExam(@RequestBody ExamSubmissionDTO dto, Authentication authentication) {
        String regNumber = authentication.getName(); // Extracted from JWT
        examService.submitExam(regNumber, dto);
        return "Exam submitted successfully";
    }

    @GetMapping("/exam-questions/{examId}")
    public List<QuestionViewDTO> getExamQuestions(@PathVariable Long examId) {
        return examService.getQuestionsForExam(examId);
    }
}
