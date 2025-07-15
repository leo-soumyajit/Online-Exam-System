package com.soumyajit.Online.Exam.Portal.Entities;

import com.soumyajit.Online.Exam.Portal.Entities.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String regNumber;

    private String dob;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
}
