# Course Recovery System (CRS)

A comprehensive Java-based system for managing course recovery, academic enrollment, and user management. This unified application provides role-based access for administrators, instructors, and students to manage academic operations and course recovery processes.

## 📋 Quick Overview

The CRS is a multi-module Java application that consolidates academic management, course recovery, and user management into a single integrated GUI platform. It supports:

- **Role-Based Access Control** - Different interfaces for Admin, Instructor, and Student roles
- **User Authentication** - Secure login with activity logging
- **Course Management** - Track courses, grades, and course recovery
- **Academic Records** - Manage enrollments, attempts, and assessments
- **Password Recovery** - Self-service password reset functionality

## 🚀 Quick Start

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- Git

### Running the Application (Recommended)

```bash
cd CRS_Unified
mvn clean package
java -jar target/CRS_Unified-1.0-SNAPSHOT.jar
# Or on Windows: ./run.bat
```

### Initial Login Credentials

**Admin Account:**
- Username: `admin`
- Password: `admin123`

**Instructor Account:**
- Username: `instructor`
- Password: `instr123`

**Student Account:**
- Username: `student`
- Password: `stud123`

See [ACCOUNT_ACTIVATION_GUIDE.md](CRS_Unified/ACCOUNT_ACTIVATION_GUIDE.md) for more details.

## 📁 Project Structure

```
CRS/
├── AcademicEngine/                    # Core academic engine
│   └── src/Academicengine/
│       ├── attempt/                   # Course attempt tracking
│       ├── course/                    # Course management
│       ├── enrollment/                # Student enrollments
│       ├── grade/                     # Grade calculations
│       └── submission/                # Assignment submissions
│
├── CourseProjectSystem/               # Project management module
│   └── src/
│       ├── academicengine/
│       ├── com/                       # Project components
│       └── crs/                       # CRS integration
│
├── CourseRecoverySystem/              # Maven-based recovery core (Primary backend)
│   ├── src/main/java/
│   │   ├── model/                     # Data models
│   │   ├── recovery/                  # Recovery logic
│   │   ├── storage/                   # Data persistence
│   │   └── ui/                        # UI components
│   └── pom.xml
│
├── CRS.UserManagement/                # User authentication & management
│   └── src/CRS/
│       ├── User.java                  # User model
│       ├── UserManager.java           # User management
│       ├── AuthService.java           # Authentication
│       ├── PasswordRecovery.java      # Password reset
│       └── ActivityLog.java           # Activity logging
│
├── CRS_Unified/                       # ⭐ Main GUI Application (Entry Point)
│   ├── src/main/java/com/crs/
│   │   ├── academic/                  # Academic operations
│   │   ├── enrollment/                # Enrollment UI
│   │   ├── recovery/                  # Recovery operations
│   │   ├── storage/                   # Data persistence
│   │   ├── ui/                        # GUI components
│   │   ├── user/                      # User management UI
│   │   └── MainApplication.java       # Application entry point
│   ├── pom.xml
│   └── target/CRS_Unified-1.0-SNAPSHOT.jar  # Compiled application
│
├── README.md                          # This file
├── README_PROJECT_STRUCTURE.md        # Detailed structure documentation
└── .gitignore                         # Git ignore rules

```

## 🔧 Building the Project

### Build All Modules
```bash
# Build all Maven projects
cd CourseRecoverySystem && mvn clean package && cd ..
cd CRS_Unified && mvn clean package && cd ..

# Build Ant projects (optional - CRS_Unified is the main app)
cd AcademicEngine && ant clean build && cd ..
cd CRS.UserManagement && ant clean build && cd ..
```

### Build Only Main Application
```bash
cd CRS_Unified
mvn clean package -DskipTests
```

## ▶️ Running the Application

### GUI Mode (Recommended)
```bash
cd CRS_Unified
java -jar target/CRS_Unified-1.0-SNAPSHOT.jar
```

### Console Mode (Testing)
```bash
cd CRS_Unified
./console_mode.bat  # Windows
bash console_mode.sh  # Linux/Mac
```

## 📚 Module Details

### CRS_Unified (Main Application)
The primary entry point and integrated GUI application combining all modules.
- **Location:** `CRS_Unified/`
- **Build:** Maven
- **Documentation:** [CRS_Unified/README.md](CRS_Unified/README.md)
- **Run:** `java -jar target/CRS_Unified-1.0-SNAPSHOT.jar`

### CourseRecoverySystem (Backend Core)
Maven-based backend handling recovery logic and data persistence.
- **Location:** `CourseRecoverySystem/`
- **Build:** Maven
- **Models:** Student, User, Administrator, AcademicOfficer, CourseGrade, FailedCourse
- **Key Features:** Recovery calculation, grade management

### CRS.UserManagement (Authentication)
Standalone user management and authentication module.
- **Location:** `CRS.UserManagement/`
- **Build:** Ant
- **Key Classes:** UserManager, AuthService, PasswordRecovery, ActivityLog
- **Features:** User authentication, activity logging, password recovery

### AcademicEngine (Core Academic Logic)
Foundation module for academic operations.
- **Location:** `AcademicEngine/`
- **Build:** Ant
- **Key Components:** 
  - Attempt tracking (CourseAttempt, AttemptStatus)
  - Course management (Course, CourseCatalog)
  - Enrollment management (Enrollment, EnrollmentManager)
  - Grade calculation (Grade, GradeCalculator)
  - Assignment submissions

### CourseProjectSystem
Project and assignment management system.
- **Location:** `CourseProjectSystem/`
- **Handles:** Project assignments, submissions, grading

## 👥 Role-Based Features

### Admin
- User account management
- View all users and their activity
- System administration
- Report generation

### Instructor
- Manage enrolled students
- Create and manage course recovery programs
- View student grades and attempts
- Generate academic reports

### Student
- View personal academic records
- Enroll in available courses
- View enrollment history
- Attempt course recovery programs
- Update password

## 🔐 Security Features

- **Role-Based Access Control** - Different permissions per role
- **Activity Logging** - All user actions tracked
- **Password Recovery** - Self-service password reset
- **Persistent Authentication** - Session management
- **File-Based Storage** - Encrypted data persistence

## 📖 Documentation

- [README_PROJECT_STRUCTURE.md](README_PROJECT_STRUCTURE.md) - Detailed module structure
- [CRS_Unified/README.md](CRS_Unified/README.md) - GUI application guide
- [CRS_Unified/ROLE_BASED_SYSTEM_GUIDE.md](CRS_Unified/ROLE_BASED_SYSTEM_GUIDE.md) - Role-based access guide
- [CRS_Unified/ACCOUNT_ACTIVATION_GUIDE.md](CRS_Unified/ACCOUNT_ACTIVATION_GUIDE.md) - Setting up test accounts
- [CRS_Unified/INTEGRATION_SUMMARY.md](CRS_Unified/INTEGRATION_SUMMARY.md) - Integration details
- [CRS_Unified/RETURN_TO_LOGIN_FEATURE.md](CRS_Unified/RETURN_TO_LOGIN_FEATURE.md) - Navigation features

## 🛠️ Development

### Project Stack
- **Language:** Java 8+
- **Build Tools:** Maven, Apache Ant
- **UI Framework:** Swing (for GUI)
- **Data Persistence:** File-based with serialization
- **IDE:** NetBeans, IntelliJ IDEA, Eclipse, VS Code

### Module Dependencies
```
CRS_Unified (Main)
├── CourseRecoverySystem
├── CRS.UserManagement
└── AcademicEngine
    └── CourseProjectSystem
```

### Adding New Features

1. **Academic Logic:** Add to `AcademicEngine` module
2. **Recovery Features:** Add to `CourseRecoverySystem`
3. **User Features:** Add to `CRS.UserManagement`
4. **GUI:** Add to `CRS_Unified/src/main/java/com/crs/ui/`

### Running Tests
```bash
cd CourseRecoverySystem
mvn test

cd ../CRS_Unified
mvn test
```

## 🐛 Troubleshooting

### Application Won't Start
- Ensure Java 8+ is installed: `java -version`
- Check Maven is installed: `mvn -version`
- Verify all dependencies downloaded: `mvn dependency:resolve`

### Build Fails
- Clean build artifacts: `mvn clean`
- Force dependency updates: `mvn dependency:resolve -U`
- Check pom.xml for missing dependencies

### Compilation Errors
- Ensure all modules are in the correct directory structure
- Verify Java version compatibility
- Check for missing import statements

## 📝 License

APU Object Oriented Development with Java - Assignment
YEAR 2 SEM 1

## 👨‍💻 Contributors

APU Computer Science Students - Course Recovery System Team

---

**Repository:** https://github.com/isaxxz-herta/Course-recovery-system

**Last Updated:** June 2026
