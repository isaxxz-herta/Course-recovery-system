# CRS Role-Based System Guide

## 🎯 System Overview

The Course Recovery System (CRS) is now a complete role-based application where users authenticate once and are directed to their appropriate interface based on their role. Each role has specific capabilities and access levels.

## 🔐 Authentication Flow

```
1. User starts application
2. Chooses GUI or Console mode
3. Enters username and password
4. System authenticates and identifies role
5. User is directed to role-specific interface
6. User performs role-appropriate tasks
7. User logs out when finished
```

## 👤 Role Capabilities Matrix

| Feature | Administrator | Academic Officer | Student |
|---------|:-------------:|:----------------:|:-------:|
| **User Management** |
| Create users | ✅ | ❌ | ❌ |
| Edit users | ✅ | ❌ | ❌ |
| Activate/Deactivate users | ✅ | ❌ | ❌ |
| Reset passwords | ✅ | ❌ | ❌ |
| **Academic Management** |
| View all students | ✅ | ✅ | ❌ |
| View student details | ✅ | ✅ | Own only |
| Check eligibility | ✅ | ✅ | Own only |
| Generate reports | ✅ | ✅ | Own only |
| Export reports | ✅ | ✅ | Own only |
| **System Management** |
| Save system data | ✅ | ✅ | ❌ |
| View system logs | ✅ | ❌ | ❌ |
| **Personal Management** |
| Change own password | ✅ | ✅ | ✅ |
| View own profile | ✅ | ✅ | ✅ |

## 🖥️ Administrator Interface

### GUI Dashboard Tabs:
1. **User Management** - Complete user lifecycle management
2. **Student Reports** - Academic performance overview
3. **System Logs** - Activity monitoring and troubleshooting

### Console Menu Options:
1. View All Users
2. Create New User
3. Edit User
4. Deactivate User
5. Activate User
6. Reset User Password
7. View Student Reports
8. Check Student Eligibility
9. Change My Password
10. Save Data
11. Logout

### Key Administrator Tasks:
- **User Creation**: Add new students, officers, or administrators
- **Account Management**: Activate/deactivate accounts, reset passwords
- **System Oversight**: Monitor all system activities and data
- **Report Generation**: Create comprehensive academic reports
- **Data Management**: Backup and restore system data

## 👨‍🏫 Academic Officer Interface

### GUI Dashboard Tabs:
1. **Student Overview** - All students with key metrics
2. **Eligibility Management** - Progression eligibility tracking
3. **Academic Reports** - Detailed academic performance reports
4. **Recovery Plans** - At-risk student identification and planning

### Console Menu Options:
1. View All Students
2. View Student Details
3. Check Student Eligibility
4. Check All Students Eligibility
5. Generate Student Report
6. Generate All Student Reports
7. Identify At-Risk Students
8. Change My Password
9. Save Data
10. Logout

### Key Officer Tasks:
- **Student Monitoring**: Track academic performance across all students
- **Eligibility Assessment**: Determine progression eligibility
- **Risk Identification**: Identify students needing intervention
- **Report Generation**: Create detailed academic reports
- **Recovery Planning**: Develop plans for struggling students

## 🎓 Student Interface

### GUI Dashboard Tabs:
1. **Academic Overview** - Personal academic summary
2. **Course Grades** - Detailed grade breakdown
3. **Failed Courses** - Courses requiring attention
4. **Academic Report** - Personal performance report

### Console Menu Options:
1. View Profile
2. Academic Overview
3. View Course Grades
4. View Failed Courses
5. Check Eligibility
6. Generate Academic Report
7. Export Academic Report
8. Change Password
9. Logout

### Key Student Tasks:
- **Academic Monitoring**: Track personal academic progress
- **Grade Review**: View detailed course performance
- **Eligibility Check**: Understand progression status
- **Report Access**: Generate and export personal reports
- **Profile Management**: Update personal information

## 🔄 Workflow Examples

### Administrator Workflow: Creating a New Student
1. Login as Administrator
2. Navigate to User Management tab (GUI) or select option 2 (Console)
3. Click "Create User" or enter user details
4. Fill in student information:
   - User ID: S003
   - Username: student3
   - Password: student123
   - Email: student3@crs.edu
   - Full Name: Mike Johnson
   - Role: STUDENT
   - Student ID: TP003
   - Program: Information Technology
5. Save user
6. User is immediately available for login

### Academic Officer Workflow: Checking Student Eligibility
1. Login as Academic Officer
2. Navigate to Student Overview tab (GUI) or select option 1 (Console)
3. Review student list with CGPA and eligibility status
4. Select "Check All Students Eligibility" for comprehensive review
5. Review detailed eligibility report
6. Identify students needing intervention
7. Export eligibility report for records

### Student Workflow: Viewing Academic Progress
1. Login as Student
2. Navigate to Academic Overview tab (GUI) or select option 2 (Console)
3. Review current CGPA and credit hours
4. Check eligibility status
5. Navigate to Course Grades tab to see detailed performance
6. Generate academic report for personal records
7. Export report if needed

## 🛡️ Security Features

### Authentication Security:
- **Password Protection**: All accounts require password authentication
- **Role-Based Access**: Users can only access features appropriate to their role
- **Session Management**: Proper login/logout tracking
- **Account Status**: Ability to activate/deactivate accounts

### Data Security:
- **Access Control**: Students can only view their own data
- **Administrative Oversight**: Administrators have full system access
- **Audit Trail**: System logs track user activities
- **Data Persistence**: Secure data storage and backup

## 🔧 System Administration

### Default Account Setup:
The system automatically creates default accounts on first run:
- Administrator account for system management
- Academic Officer account for student oversight
- Sample student accounts with realistic data

### Data Management:
- **Automatic Save**: System saves data after major operations
- **Manual Save**: Users can manually save data
- **Data Loading**: System loads existing data on startup
- **Export Functions**: Generate reports and export data

### Maintenance Tasks:
- **User Account Management**: Regular review of active accounts
- **Data Backup**: Regular system data backups
- **Performance Monitoring**: Track system usage and performance
- **Security Updates**: Regular password and access reviews

## 📊 Reporting Capabilities

### Administrator Reports:
- Complete user listing with status
- System-wide academic performance
- User activity logs
- Comprehensive eligibility reports

### Academic Officer Reports:
- Individual student academic reports
- Bulk student performance reports
- Eligibility status reports
- At-risk student identification reports

### Student Reports:
- Personal academic transcript
- Eligibility status report
- Course performance breakdown
- Failed course summary

## 🚀 Getting Started Checklist

### For Administrators:
- [ ] Login with admin credentials
- [ ] Review existing user accounts
- [ ] Create additional user accounts as needed
- [ ] Verify system data integrity
- [ ] Set up regular backup procedures

### For Academic Officers:
- [ ] Login with officer credentials
- [ ] Review student overview
- [ ] Perform eligibility checks
- [ ] Identify at-risk students
- [ ] Generate necessary reports

### For Students:
- [ ] Login with student credentials
- [ ] Review academic overview
- [ ] Check current eligibility status
- [ ] Generate personal academic report
- [ ] Update password if needed

## 📞 Support and Troubleshooting

### Common Issues:
1. **Login Problems**: Verify username/password, check account status
2. **Data Not Loading**: Ensure proper file permissions, check data file integrity
3. **Report Generation Errors**: Verify student data completeness
4. **GUI Not Starting**: Check Java version and display settings

### Getting Help:
- Check console output for error messages
- Verify Java installation and version
- Ensure proper file permissions
- Review system logs for detailed error information

This role-based system provides a comprehensive, secure, and user-friendly environment for managing course recovery and academic progression tracking.