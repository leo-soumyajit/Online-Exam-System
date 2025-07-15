package com.soumyajit.Online.Exam.Portal.Entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime submitTime;

    private boolean submitted;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Exam exam;

    @ElementCollection
    private Map<Long, String> answers; // questionId -> selected answer
}
