# CRS - Course Recovery System
## Role-Based Access Control System

This is a comprehensive Course Recovery System with role-based authentication that provides different interfaces and capabilities for each user type.

## System Overview

The CRS is a unified application that combines all individual project modules into a single, role-based system where users login and are directed to their appropriate interface based on their role.

## User Roles & Capabilities

### 🔐 **Administrator**
- **Full system access and user management**
- Create, edit, activate/deactivate users
- Reset user passwords
- View all student reports and eligibility status
- System data management and backup
- Access to system logs and analytics

### 👨‍🏫 **Academic Officer**
- **Student academic oversight and reporting**
- View all student academic records
- Generate individual and bulk student reports
- Perform eligibility checks for progression
- Identify at-risk students
- Create recovery plan recommendations
- Export academic reports and eligibility data

### 🎓 **Student**
- **Personal academic management**
- View personal academic profile and grades
- Check own eligibility status
- Generate and export personal academic reports
- Email reports to themselves
- Change personal password
- View failed courses and recommendations

## Project Structure

```
CRS_Unified/
├── src/main/java/com/crs/
│   ├── main/           # Main application entry point
│   ├── model/          # Core data models (User, Student, etc.)
│   ├── service/        # Business logic services
│   ├── persistence/    # Data persistence layer
│   ├── ui/             # User interface components
│   ├── auth/           # Authentication and authorization
│   ├── academic/       # Academic engine components
│   ├── output/         # Report generation and output
│   └── recovery/       # Recovery system components
├── pom.xml            # Maven configuration
├── run.bat            # Windows batch file to run the application
└── README.md          # This file
```

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher installed
- Windows/Mac/Linux operating system

### Running the Application

#### Option 1: GUI Mode (Default - Recommended)
```bash
# Fresh start with clean data
fresh_start.bat

# Or quick run if already compiled
compile_and_run.bat

# Or manual compilation
javac -cp "src/main/java" -d "target/classes" src/main/java/com/crs/main/CRSApplication.java
java -cp "target/classes" com.crs.main.CRSApplication
```

#### Option 2: Console Mode (Text-based)
```bash
# Use console mode batch file
console_mode.bat

# Or manual with console argument
java -cp "target/classes" com.crs.main.CRSApplication console
```

## 🔑 Default Login Accounts

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| **Administrator** | `admin` | `admin123` | Full system access |
| **Academic Officer** | `officer` | `officer123` | Student oversight |
| **Student** | `student1` | `student123` | Sample student account |
| **Student** | `student2` | `student123` | Sample student account |

## 🖥️ Interface Options

The application now starts directly in GUI mode for the best user experience:

1. **GUI Mode** (Default) - Full graphical interface with role-based dashboards
2. **Console Mode** - Available via `console_mode.bat` or command line argument

### GUI Mode Features
- **Login Screen** - Secure authentication with role detection
- **Role-Based Dashboards** - Customized interface for each user type
- **Tabbed Navigation** - Organized features in easy-to-use tabs
- **Data Tables** - Interactive tables for viewing and managing data
- **Export Functions** - Generate and save reports
- **Real-time Updates** - Live data refresh and status updates

### Console Mode Features
- **Menu-Driven Interface** - Simple numbered menu options
- **Role-Specific Menus** - Different options based on user role
- **Interactive Commands** - Step-by-step guided operations
- **Immediate Feedback** - Real-time status and confirmation messages

## Features

### Academic Engine
- Course creation and management
- Student enrollment tracking
- Assessment submissions
- Grade calculations
- Academic progress monitoring

### User Management
- User authentication and authorization
- Role-based access control (Student, Administrator, Academic Officer)
- Password management
- User account lifecycle management

### Output Module
- Academic report generation
- Email notifications
- File export capabilities
- Performance recommendations

### Recovery System
- Eligibility checking
- Recovery plan creation
- Progress tracking
- Academic intervention alerts

## Sample Data

The application includes sample data for testing:
- Sample students with grades and failed courses
- Default administrator account
- Sample courses and assessments
- Generated academic reports

## File Outputs

The application generates several output files:
- `crs_data.bin` - Serialized user data
- `student_report.txt` - Generated academic reports
- Console logs for all operations

## Troubleshooting

### Common Issues

1. **Compilation Errors**
   - Ensure Java 11+ is installed
   - Check Maven is properly configured
   - Verify all dependencies are available

2. **Runtime Errors**
   - Check file permissions for data files
   - Ensure proper classpath configuration
   - Verify all required directories exist

3. **GUI Issues**
   - Ensure display is available for Swing GUI
   - Try console mode if GUI fails
   - Check Java AWT/Swing support

### Getting Help

If you encounter issues:
1. Check the console output for error messages
2. Verify Java and Maven versions
3. Try running individual demo modes
4. Check file permissions and disk space

## Development Notes

This unified system maintains compatibility with the original individual modules while providing a cohesive user experience. The modular architecture allows for easy maintenance and future enhancements.

### Key Design Decisions

- **Package Structure**: Organized by functionality rather than original project boundaries
- **Data Models**: Unified model classes shared across all modules
- **Interface Compatibility**: Maintained original interfaces where possible
- **Error Handling**: Consistent error handling across all modules
- **Configuration**: Single Maven configuration for the entire system

## Future Enhancements

Potential areas for improvement:
- Database integration for persistent storage
- Web-based interface
- Advanced reporting features
- Integration with external systems
- Enhanced security features
- Mobile application support