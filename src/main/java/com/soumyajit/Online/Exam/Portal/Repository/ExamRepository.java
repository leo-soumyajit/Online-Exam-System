package com.soumyajit.Online.Exam.Portal.Repository;

import com.soumyajit.Online.Exam.Portal.Entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByAccessToken(String accessToken);
}
