package com.soumyajit.Online.Exam.Portal.Controller;

import com.soumyajit.Online.Exam.Portal.DTOS.*;
import com.soumyajit.Online.Exam.Portal.Entities.*;
import com.soumyajit.Online.Exam.Portal.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add-student")
    public Student addStudent(@RequestBody CreateStudentDTO dto) {
        return adminService.addStudent(dto);
    }

    @PostMapping("/create-exam")
    public Exam createExam(@RequestBody CreateExamDTO dto) {
        return adminService.createExam(dto);
    }

    @PostMapping("/add-question")
    public Question addQuestion(@RequestBody CreateQuestionDTO dto) {
        return adminService.addQuestion(dto);
    }
    @GetMapping("/exam/{examId}/submissions")
    public List<ExamSubmissionViewDTO> getExamSubmissions(@PathVariable Long examId) {
        return adminService.getAllSubmissionsForExam(examId);
    }
}
