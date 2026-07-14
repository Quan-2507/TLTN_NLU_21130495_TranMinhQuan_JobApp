# Dream Job - Job Finder Application 🚀

Welcome to **Dream Job**, a comprehensive and modern platform that connects candidates with their dream jobs and helps recruiters find the best talents. This project is built using a modern technology stack, ensuring high performance, scalability, and an excellent user experience.

---

## 🏗️ Architecture & Technology Stack

The project is divided into several modules, each serving a specific purpose:

- **Frontend (Mobile App):** Built with [Flutter](https://flutter.dev/) & Dart. 
  - *Location:* `/dream_job`
  - *Features:* Clean Minimalist UI (Black & White theme), Glassmorphism elements, fully responsive for Android and iOS.
- **Backend (API Server):** Built with [Spring Boot](https://spring.io/projects/spring-boot) (Java 17).
  - *Location:* `/apis`
  - *Features:* RESTful APIs, Spring Data JPA, Hibernate, JWT Authentication, integration with Cloudinary (Image Hosting) and PayPal (Sandbox).
- **Database:** MySQL.
  - *Location:* `/Scripts SQL` (Contains initialization scripts).
- **Admin Panel:**
  - *Location:* `/admin` & `/admin_web` (Management dashboards).

---

## 📂 Project Structure

```text
📦 TLTN_NLU_21130495_TranMinhQuan_JobApp
 ┣ 📂 apis/           # Spring Boot REST API source code
 ┣ 📂 dream_job/      # Flutter mobile application source code
 ┣ 📂 admin/          # Admin management application
 ┣ 📂 admin_web/      # Admin web dashboard
 ┣ 📂 Scripts SQL/    # Database schema and mock data scripts
 ┗ 📜 README.md       # Project documentation
```

---

## 🛠️ Setup & Installation Guide

Follow these steps to get the project up and running on your local machine.

### 1. Database Setup
1. Ensure you have **MySQL** installed and running on port `3306`.
2. Create a new database named `apis`.
3. Import the SQL scripts located in the `/Scripts SQL` folder into the `apis` database to initialize tables and sample data.

### 2. Backend Setup (Spring Boot)
1. Navigate to the `apis` directory:
   ```bash
   cd apis
   ```
2. Update the database configuration in `apis/src/main/resources/application.properties` if your MySQL username/password is different from the default (`root` / empty).
3. Run the Spring Boot application using Maven:
   ```bash
   # On Windows
   .\mvnw.cmd spring-boot:run
   
   # On Mac/Linux
   ./mvnw spring-boot:run
   ```
4. The backend server will start on `http://localhost:8080`. API documentation (Swagger) is available at `http://localhost:8080/swagger-ui-custom.html`.

### 3. Frontend Setup (Flutter)
1. Ensure you have the **Flutter SDK** installed (Version 3.x+).
2. Navigate to the `dream_job` directory:
   ```bash
   cd dream_job
   ```
3. Install dependencies:
   ```bash
   flutter pub get
   ```
4. Run the application on an Android Emulator or physical device:
   ```bash
   flutter run
   ```

---

## ✨ Key Features
- **Candidate Portal:** Browse jobs, search with filters, view company details, apply with CV, and save favorite jobs.
- **Modern UI/UX:** High-contrast Minimalist Black & White theme for a premium feel.
- **Recruiter Tools:** Post new jobs, manage applications, and review candidate profiles.
- **Secure Authentication:** JWT-based login and registration.

---

## 🤝 Contribution
If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.

## 📝 License
This project is for educational and graduation thesis purposes (TLTN - NLU). All rights reserved.
