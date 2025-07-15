package com.soumyajit.Online.Exam.Portal.Repository;

import com.soumyajit.Online.Exam.Portal.Entities.Exam;
import com.soumyajit.Online.Exam.Portal.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExam(Exam exam);
}
