package com.soumyajit.Online.Exam.Portal.Repository;

import com.soumyajit.Online.Exam.Portal.Entities.Exam;
import com.soumyajit.Online.Exam.Portal.Entities.ExamSession;
import com.soumyajit.Online.Exam.Portal.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
    boolean existsByStudentAndExam(Student student, Exam exam);
    List<ExamSession> findByExamId(Long examId);
    Optional<ExamSession> findByStudentAndExam(Student student, Exam exam);
    Optional<ExamSession> findByStudentAndSubmittedFalse(Student student);
    List<ExamSession> findBySubmittedFalseAndEndTimeBefore(LocalDateTime time);



}

