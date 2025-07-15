package com.soumyajit.Online.Exam.Portal.DTOS;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDTO {
    private String name;
    private String regNumber;
    private String dob;
}
