package com.soumyajit.Online.Exam.Portal.Service;

import com.soumyajit.Online.Exam.Portal.DTOS.*;
import com.soumyajit.Online.Exam.Portal.Entities.*;
import com.soumyajit.Online.Exam.Portal.Entities.Enum.Role;
import com.soumyajit.Online.Exam.Portal.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ExamSessionRepository sessionRepo;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepo;
    private final QuestionRepository questionRepo;

    private final AdminRepository adminRepository;

    private void ensureAdminAccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Unauthorized: Not authenticated");
        }

        String username = auth.getName();
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ADMIN".equalsIgnoreCase(String.valueOf(admin.getRole()))) {
            throw new RuntimeException("Forbidden: Admin access required");
        }
    }


    public Student addStudent(CreateStudentDTO dto) {
        ensureAdminAccess();

        Student s = new Student();
        s.setName(dto.getName());
        s.setRegNumber(dto.getRegNumber());
        s.setDob(dto.getDob());
        s.setRole(Role.STUDENT);
        return studentRepository.save(s);
    }

    public Exam createExam(CreateExamDTO dto) {
        ensureAdminAccess();

        Exam e = new Exam();
        e.setTitle(dto.getTitle());
        e.setAccessToken(dto.getAccessToken());
        e.setDurationMinutes(dto.getDurationMinutes());
        e.setQuestions(new ArrayList<>());
        return examRepo.save(e);
    }

    public Question addQuestion(CreateQuestionDTO dto) {
        ensureAdminAccess();

        Exam exam = examRepo.findById(dto.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Question q = new Question();
        q.setContent(dto.getContent());
        q.setCorrectAnswer(dto.getCorrectAnswer());
        q.setOptions(dto.getOptions());
        q.setExam(exam);

        return questionRepo.save(q);
    }


    public List<ExamSubmissionViewDTO> getAllSubmissionsForExam(Long examId) {
        List<ExamSession> sessions = sessionRepo.findByExamId(examId);

        return sessions.stream().map(session -> {
            ExamSubmissionViewDTO dto = new ExamSubmissionViewDTO();
            dto.setStudentName(session.getStudent().getName());
            dto.setStudentRegNumber(session.getStudent().getRegNumber());
            dto.setExamId(session.getExam().getId());
            dto.setExamTitle(session.getExam().getTitle());
            dto.setAnswers(session.getAnswers());
            dto.setSubmitted(session.isSubmitted());
            return dto;
        }).collect(Collectors.toList());
    }
}
