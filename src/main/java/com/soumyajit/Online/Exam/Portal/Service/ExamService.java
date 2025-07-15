package com.soumyajit.Online.Exam.Portal.Service;

import com.soumyajit.Online.Exam.Portal.DTOS.ExamSubmissionDTO;
import com.soumyajit.Online.Exam.Portal.DTOS.QuestionViewDTO;
import com.soumyajit.Online.Exam.Portal.Entities.*;
import com.soumyajit.Online.Exam.Portal.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    public void submitExam(String regNumberFromToken) {
        Student student = studentRepo.findByRegNumber(regNumberFromToken)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        ExamSession session = sessionRepo.findByStudentAndSubmittedFalse(student)
                .orElseThrow(() -> new RuntimeException("No active exam session found"));
        if (session.isSubmitted()) {
            throw new RuntimeException("Exam already submitted");
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(session.getEndTime())) {
            session.setSubmitted(true);
            session.setSubmitTime(now);
            sessionRepo.save(session);
            return;
        }

        Exam exam = session.getExam();
        Map<Long, String> currentAnswers = session.getAnswers() != null ? session.getAnswers() : new HashMap<>();
        Map<Long, String> validatedAnswers = new HashMap<>();

        for (Map.Entry<Long, String> entry : currentAnswers.entrySet()) {
            Long questionId = entry.getKey();
            String selectedOption = entry.getValue();

            Question question = exam.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst()
                    .orElse(null);

            if (question != null && question.getOptions().contains(selectedOption)) {
                validatedAnswers.put(questionId, selectedOption);
            }
        }

        session.setAnswers(validatedAnswers);
        session.setSubmitted(true);
        session.setSubmitTime(now);
        sessionRepo.save(session);
    }



    public void saveAnswer(String regNumber, Long questionId, String answer) {
        Student student = studentRepo.findByRegNumber(regNumber)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ExamSession session = sessionRepo.findByStudentAndSubmittedFalse(student)
                .orElseThrow(() -> new RuntimeException("No active session"));

        if (LocalDateTime.now().isAfter(session.getEndTime())) {
            autoSubmit(session);
            throw new RuntimeException("Time’s up. Auto-submitted.");
        }


        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Invalid question"));

        if (!question.getOptions().contains(answer)) {
            throw new RuntimeException("Invalid option for question ID: " + questionId);
        }

        session.getAnswers().put(questionId, answer);
        sessionRepo.save(session);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void autoSubmit(ExamSession session) {
        session.setSubmitted(true);
        session.setSubmitTime(session.getEndTime());
        sessionRepo.save(session);
    }


    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    public void autoSubmitExpiredSessions() {
        List<ExamSession> expiredSessions = sessionRepo
                .findBySubmittedFalseAndEndTimeBefore(LocalDateTime.now());

        for (ExamSession session : expiredSessions) {
            session.setSubmitted(true);
            session.setSubmitTime(LocalDateTime.now());
            if (session.getAnswers() == null) {
                session.setAnswers(new HashMap<>());
            }

            sessionRepo.save(session);
        }
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
