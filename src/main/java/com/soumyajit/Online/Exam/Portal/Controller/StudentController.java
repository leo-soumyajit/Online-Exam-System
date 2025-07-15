package com.soumyajit.Online.Exam.Portal.Controller;

import com.soumyajit.Online.Exam.Portal.DTOS.ExamSubmissionDTO;
import com.soumyajit.Online.Exam.Portal.DTOS.QuestionViewDTO;
import com.soumyajit.Online.Exam.Portal.Entities.Exam;
import com.soumyajit.Online.Exam.Portal.Service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    public String submitExam(Authentication authentication) {
        String regNumber = authentication.getName(); // Extracted from JWT
        examService.submitExam(regNumber);
        return "Exam submitted successfully";
    }



    @PostMapping("/answer")
    public ResponseEntity<?> submitAnswer(@RequestBody Map<String, Object> payload, Principal principal) {
        String regNumber = principal.getName();
        Long questionId = Long.parseLong(payload.get("questionId").toString());
        String answer = payload.get("answer").toString();

        examService.saveAnswer(regNumber, questionId, answer);
        return ResponseEntity.ok("Answer saved");
    }

    @GetMapping("/exam-questions/{examId}")
    public List<QuestionViewDTO> getExamQuestions(@PathVariable Long examId) {
        return examService.getQuestionsForExam(examId);
    }
}
