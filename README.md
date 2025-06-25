# 📚 Blog Full-Stack App

A complete blog management system built using **Java Spring Boot** (Backend) and **HTML, Tailwind CSS, and JavaScript** (Frontend). It includes secure JWT authentication, user role handling, and CRUD operations for blog posts.

---

## 🚀 Features

- 🔐 User Registration & Login with JWT Auth
- 🧑‍💻 Author role to create and manage posts
- 📄 View all public blog posts (no login required)
- 📝 Create, Update, Delete own posts
- 🌓 Light/Dark mode toggle
- 📦 RESTful APIs with clean architecture
- 🌐 Responsive design with Tailwind CSS
- 🛡️ Role-based access control (AUTHOR, ADMIN)

---

## 🧰 Tech Stack

### Frontend
- HTML5
- Tailwind CSS
- JavaScript (Vanilla)

### Backend
- Java 17+
- Spring Boot
- Spring Security (JWT)
- MySQL
- Lombok
- Maven

---

## 📁 Folder Structure
blog-fullstack-app/
├── backend/ # Spring Boot backend
│ ├── src/main/java/com/blog/
│ │ ├── controller/
│ │ ├── entity/
│ │ ├── repository/
│ │ ├── security/
│ │ ├── service/
│ ├── resources/
│ │ └── application.properties
│ └── pom.xml
│
├── frontend/ # Static frontend files
│ ├── index.html
│ ├── dashboard.html
│ ├── create_post.html
│ ├── login.html
│ ├── register.html
│ ├── my_posts.html
│ └── style.css (if any)
└── README.md

---

## 🔧 How to Run

### Prerequisites

- Java 17+
- MySQL
- Maven
- VS Code / IntelliJ
- Postman (for testing APIs)

### Backend Setup

```bash
cd backend
# configure your application.properties (DB credentials, JWT secret)
mvn spring-boot:run
Frontend Setup
Just open the frontend/index.html in your browser or use Live Server extension in VS Code.
```
🧪 API Endpoints
Auth
POST /api/auth/register

POST /api/auth/login

Posts
GET /api/posts/all — Public

GET /api/posts/user?email={email} — Requires token

POST /api/posts — Requires token

PUT /api/posts/{id} — Requires token

DELETE /api/posts/{id} — Requires token

👨‍💻 Author
Jim Jeffry
Java Full Stack Developer | GitHub | LinkedIn

📜 License
This project is open-source and available under the MIT License.
