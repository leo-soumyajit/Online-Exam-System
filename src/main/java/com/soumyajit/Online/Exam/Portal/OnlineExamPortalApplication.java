package com.soumyajit.Online.Exam.Portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnlineExamPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineExamPortalApplication.class, args);
	}

}
