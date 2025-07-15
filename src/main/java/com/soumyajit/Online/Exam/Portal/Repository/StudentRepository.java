package com.soumyajit.Online.Exam.Portal.Repository;

import com.soumyajit.Online.Exam.Portal.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRegNumberAndDob(String regNumber, String dob);
    Optional<Student> findByRegNumber(String regNumber);
}

