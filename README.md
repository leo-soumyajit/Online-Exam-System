# 🧠 Online Exam Portal

This is the **backend** of an Online Exam Portal built using **Spring Boot**. It handles authentication, exam creation, question management, and student participation, all powered by a secure JWT-based access system.

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

| Layer     | Technology                  |
|----------|-----------------------------|
| Backend  | Spring Boot, Spring Security|
| Auth     | JSON Web Tokens (JWT)       |
| Database | MySQL / PostgreSQL          |
| Build    | Maven                       |
| Tooling  | Postman, Swagger (optional) |

---

## ⚙️ Setup & Installation

### Prerequisites

- Java 17+
- Maven
- MySQL (or any JDBC-compatible DB)

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/online-exam-portal.git
cd online-exam-portal
