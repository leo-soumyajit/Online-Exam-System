package com.soumyajit.Online.Exam.Portal.Service;

import com.soumyajit.Online.Exam.Portal.DTOS.ExamSubmissionDTO;
import com.soumyajit.Online.Exam.Portal.DTOS.QuestionViewDTO;
import com.soumyajit.Online.Exam.Portal.Entities.*;
import com.soumyajit.Online.Exam.Portal.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepo;
    private final ExamSessionRepository sessionRepo;
    private final StudentRepository studentRepo;
    private final QuestionRepository questionRepository;

    public Exam joinExam(String regNumber, String accessToken) {
        Exam exam = examRepo.findByAccessToken(accessToken)
                .orElseThrow(() -> new RuntimeException("Invalid exam access token"));

        Student student = studentRepo.findByRegNumber(regNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (sessionRepo.existsByStudentAndExam(student, exam)) {
            throw new RuntimeException("You have already attempted this exam");
        }

        ExamSession session = new ExamSession();
        session.setExam(exam);
        session.setStudent(student);

        LocalDateTime startTime = LocalDateTime.now();
        session.setStartTime(startTime);
        session.setEndTime(startTime.plusMinutes(exam.getDurationMinutes()));
        session.setSubmitted(false);

        sessionRepo.save(session);
        return exam;
    }

    public void submitExam(String regNumber, ExamSubmissionDTO dto) {
        Exam exam = examRepo.findById(dto.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Student student = studentRepo.findByRegNumber(regNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ExamSession session = sessionRepo.findByStudentAndExam(student, exam)
                .orElseThrow(() -> new RuntimeException("Exam session not found"));

        if (session.isSubmitted()) {
            throw new RuntimeException("Exam already submitted");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = session.getStartTime().plusMinutes(exam.getDurationMinutes());

        Map<Long, String> submittedAnswers = dto.getAnswers() != null ? dto.getAnswers() : new HashMap<>();

        if (now.isAfter(endTime)) {
            // Auto-submit whatever answers are present
            session.setSubmitted(true);
            session.setSubmitTime(now);
            session.setAnswers(submittedAnswers); // partial or full
            sessionRepo.save(session);
            return;
        }

        // Validate submitted answers
        Map<Long, String> validatedAnswers = new HashMap<>();

        for (Map.Entry<Long, String> entry : submittedAnswers.entrySet()) {
            Long questionId = entry.getKey();
            String selectedOption = entry.getValue();

            Question question = exam.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid question ID: " + questionId));

            if (!question.getOptions().contains(selectedOption)) {
                throw new RuntimeException("Invalid option '" + selectedOption + "' for question ID: " + questionId);
            }

            validatedAnswers.put(questionId, selectedOption);
        }

        // Save only validated answers
        session.setAnswers(validatedAnswers);
        session.setSubmitted(true);
        session.setSubmitTime(now);
        sessionRepo.save(session);
    }


    public List<QuestionViewDTO> getQuestionsForExam(Long examId) {
        Exam exam = examRepo.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        List<Question> questions = questionRepository.findByExam(exam);

        return questions.stream()
                .map(q -> new QuestionViewDTO(q.getId(), q.getContent(), q.getOptions()))
                .toList();
    }
}
