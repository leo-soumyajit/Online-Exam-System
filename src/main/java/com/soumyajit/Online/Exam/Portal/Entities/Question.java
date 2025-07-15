package com.soumyajit.Online.Exam.Portal.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String correctAnswer;

    @ElementCollection
    private List<String> options;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
}

