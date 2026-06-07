# Course Recovery System (CRS)

A comprehensive Java-based system for managing course recovery, academic enrollment, and user management.

## Project Structure

### Projects Overview

- **AcademicEngine/** - Core academic engine handling attempt tracking, course management, and enrollment
  - `attempt/` - Attempt management components
  - `course/` - Course-related classes
  - `enrollment/` - Enrollment processing
  
- **CourseProjectSystem/** - Project and course management system
  - Handles course project assignments and submissions
  
- **CourseRecoverySystem/** - Maven-based recovery system (Primary)
  - `model/` - Data models
  - `recovery/` - Recovery logic
  - `storage/` - Data persistence
  - `ui/` - User interface components
  
- **CRS_Unified/** - Unified GUI application (Main Application)
  - Integrated GUI for all CRS functionality
  - Role-based system (Admin, Instructor, Student)
  - User management and authentication
  
- **CRS.UserManagement/** - User management module
  - User authentication
  - Activity logging
  - Password recovery
  - Role-based access control

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven (for Maven projects)
- Apache Ant (for Ant projects)

### Building

#### Maven Projects
```bash
cd CourseRecoverySystem
mvn clean package

cd ../CRS_Unified
mvn clean package
```

#### Ant Projects
```bash
cd AcademicEngine
ant clean build

cd ../CRS.UserManagement
ant clean build
```

### Running

#### CRS_Unified (Recommended - Full Application)
```bash
cd CRS_Unified
java -jar target/CRS_Unified-1.0-SNAPSHOT.jar
# Or use: ./run.bat (Windows)
```

#### Console Mode
```bash
cd CRS_Unified
./console_mode.bat
```

## Features

- **User Management**
  - Role-based authentication (Admin, Instructor, Student)
  - Password recovery
  - Activity logging

- **Academic Management**
  - Course management
  - Enrollment tracking
  - Attempt management
  - Course recovery

- **GUI Interface**
  - User-friendly desktop application
  - Integrated role-based interface
  - Real-time updates

## Account Activation

See [ACCOUNT_ACTIVATION_GUIDE.md](CRS_Unified/ACCOUNT_ACTIVATION_GUIDE.md) for setting up test accounts.

## Documentation

- [Role-Based System Guide](CRS_Unified/ROLE_BASED_SYSTEM_GUIDE.md)
- [Integration Summary](CRS_Unified/INTEGRATION_SUMMARY.md)
- [Return to Login Feature](CRS_Unified/RETURN_TO_LOGIN_FEATURE.md)
- [Direct GUI Start](CRS_Unified/DIRECT_GUI_START.md)

## Development

Each module is independently buildable but works together in the unified system.

### Module Dependencies
- CRS_Unified (main app) depends on CRS.UserManagement, CourseRecoverySystem
- CourseRecoverySystem uses models from AcademicEngine
- CRS.UserManagement is standalone for auth services

## License

APU Object Oriented Development with Java - Assignment

## Authors

YEAR 2 SEM 1 - Object Oriented Development with Java Team
