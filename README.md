# 🧠 Online Exam Portal

This is the backend for an **Online Exam Portal** developed using **Spring Boot**. It provides a complete solution for managing online examinations with secure student participation and admin control.
The system is designed to streamline the entire exam lifecycle — from exam creation and question management by admins, to live participation, answer saving, and submission tracking for students.

---

## 🚀 Features

## 🧑‍🎓 Student Exam Flow

#### 🔗 Join Exam
**POST** `/student/join-exam?accessToken=xxxx`  
Uses JWT to extract student details and register a session.

#### ✏️ Answer Question (Live Save)
**POST** `/student/answer`  
Saves one answer at a time by questionId.

#### 📤 Submit Exam (Manual)
**POST** `/student/submit-exam`  
Final submission (also auto-handled on timeout).

#### ⚙️ Auto-Submission
A background scheduled job runs every minute:
- Checks if any exam session `submitted = false` and `endTime < now`
- Submits any remaining answers automatically
- Marks the session as submitted


## 🛠️ Admin Flow

### ➕ Create Exam
**POST** `/admin/create-exam`  
Creates a new exam with title, duration, and access token.

### 📥 Add Questions to Exam
**POST** `/admin/add-question`  
Adds MCQ-type questions to an existing exam.  
Includes:
- Question content
- List of options
- Correct answer
- Target exam ID

### 📄 View Submissions
**GET** `/admin/exam-submissions/{examId}`  
Fetches all submissions for a specific exam.  
Returns:
- Student name
- Registration number
- Submitted answers
- Whether the exam was submitted manually or auto-submitted

### 👤 Add Students  
**POST** `/admin/add-student`  
Registers a student to the platform.  
Fields:
- Name  
- Registration number  
- Date of Birth (used as password)  


---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)


---

## ⚙️ Setup & Run Locally

### 📦 Clone the Repository

```bash
git clone https://github.com/leo-soumyajit/Online-Exam-System.git
cd Online-Exam-System
```
🛠 Configure Database Connection
Edit the application.properties file:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/<your_db_name>
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```
▶ Run the Application
```bash
./mvnw spring-boot:run
```

🐦 Try Endpoints in Postman:
> 🔗 View the full API reference in [Postman Collection](https://www.postman.com/newsly-0222/workspace/online-exam-portal)


### 📄 Access Swagger API Docs
Once the server is running, open in browser:
```bash
http://localhost:7000/swagger-ui/index.html
```
---
