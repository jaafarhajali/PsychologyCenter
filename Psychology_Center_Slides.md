# Psychology Center Management System
## Project Presentation Slides

---

## Slide 1: Title Slide

# Psychology Center Management System
## Complete Project Presentation

**A Java Swing Desktop Application**

*Technology Stack: Java + SQL Server + UML Design*

---

## Slide 2: Agenda

# 📑 Presentation Agenda

1. **Project Introduction**
2. **System Overview** 
3. **Key Features & Functionality**
4. **UML Diagrams Analysis**
5. **Database Design**
6. **Java Implementation**
7. **Recent Enhancements**
8. **System Demo**
9. **Conclusion**

---

## Slide 3: Project Introduction

# 🎯 What is the Psychology Center Management System?

A **desktop application** built with **Java Swing** that manages psychology center operations:

- ✅ **Patient Registration and Login**
- ✅ **Doctor Management** 
- ✅ **Appointment Booking System** with conflict prevention
- ✅ **Psychological Assessment** with condition evaluation
- ✅ **Admin Panel** for system management

---

## Slide 4: Project Structure

# 💻 Project Structure

## **17 Java Classes** implementing the complete system:

### Core Navigation:
- `WelcomePage.java` - Main entry point
- `LoginPage.java` - Patient login
- `RegistrationPage.java` - Patient registration

### Appointment System:
- `AppointmentPage.java` - Booking with conflict prevention
- `DoctorsListPage.java` - Doctor selection
- `ShowAppointmentPage.java` - Patient appointment history

### Assessment System:
- `AssessmentPage.java` - Psychological evaluation
- `PsychologicalAssessment.java` - Main assessment logic
- `ConditionSelectionPage.java` - Assessment conditions

---

## Slide 5: Technology Stack

# 🏗️ Technology Stack

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│         Java Swing Frontend            │
├─────────────────────────────────────────┤
│           Business Logic Layer          │
│      17 Java Classes + UIStyleManager  │
├─────────────────────────────────────────┤
│            Data Access Layer           │
│         JDBC + DBconnection            │
├─────────────────────────────────────────┤
│            Database Layer               │
│       Microsoft SQL Server             │
└─────────────────────────────────────────┘
```

**Database**: Microsoft SQL Server with JDBC driver (`mssql-jdbc-12.10.0.jre8.jar`)

---

## Slide 6: Database Schema

# 🗄️ Database Design - 4 Tables

## **Database Schema:**

1. **patient** table
   - Primary key: patient_id
   - Fields: full_name, age, email, phone, password, condition_name

2. **doctor** table  
   - Primary key: doctor_id
   - Fields: full_name, email, phone, address, speciality, consultation_fees, password

3. **admin** table
   - Primary key: admin_id
   - Fields: email, password

4. **appointment** table
   - Primary key: appointment_id
   - Foreign keys: patient_id, doctor_id
   - Fields: appointment_date, appointment_time, status

---

## Slide 7: Key Features

# ✨ Key Features & Functionality

## 🔐 **User Authentication System**
- **Patient Portal**: Registration, login, appointment booking
- **Doctor Portal**: View appointments and patient information
- **Admin Portal**: System management and doctor addition

## 📅 **Appointment System**
- Doctor selection from available list
- Date and time selection
- **Conflict Prevention System** with multiple validation layers

## 🧠 **Psychological Assessment**
- Assessment questions and evaluation
- Automatic redirection after appointment booking
- Condition category selection

---

## Slide 8: Conflict Prevention System

# 🛡️ Enhanced Appointment System

## **Conflict Prevention (`AppointmentPage.java`):**

### **New Methods Added:**
- `checkDoctorTimeConflict()` - Prevents doctor double-booking
- `checkPatientDuplicateAppointment()` - Stops patient duplicates  
- `isTimeSlotAvailable()` - Validates time availability
- `checkAppointmentConflicts()` - Main validation orchestrator

### **Benefits:**
✅ Real-time conflict checking during booking  
✅ Clear error messages when conflicts detected  
✅ Automatic prevention of problematic appointments  

---

## Slide 9: UML Diagrams Overview

# 📊 UML Diagrams Analysis

## **Diagram Purpose:**
- **Use Case Diagram**: Shows what users can do in the system
- **Class Diagram**: Shows the system's data structure and relationships

## **Use Case Diagram - 3 Actors:**
- 👤 **Patient** - Register, Login, Book appointments, Take assessments
- 👩‍⚕️ **Doctor** - Login, View appointments
- 🔧 **Admin** - Login, Manage doctors, View all appointments

## **Key Relationship:**
**Book Appointment <<include>> Psychological Assessment**
- Automatic assessment after booking

---

## Slide 10: Class Diagram Structure

# 🏛️ Class Diagram - 4 Core Classes

## **Patient Class**
- Attributes: patient_id, full_name, age, email, phone, password, condition_name
- Methods: register(), login(), bookAppointment(), viewAppointments()

## **Doctor Class**  
- Attributes: doctor_id, full_name, email, speciality, consultation_fees, password
- Methods: login(), viewAppointments(), updateProfile()

## **Admin Class**
- Attributes: admin_id, email, password
- Methods: login(), addDoctor(), viewAllAppointments(), manageDoctors()

## **Appointment Class**
- Attributes: appointment_id, patient_id, doctor_id, date, time, status
- Methods: createAppointment(), checkConflicts(), validateTimeSlot()

---

## Slide 11: System Flow - Patient Journey

# 🎬 Patient Journey Demo

## **Complete User Workflow:**

### 1. **System Entry**
```
WelcomePage.java → Patient Portal → LoginPage.java or RegistrationPage.java
```

### 2. **Account Management**
```
New Users: RegistrationPage.java → Account Creation → LoginPage.java
Existing Users: LoginPage.java → Authentication → Patient Dashboard
```

### 3. **Appointment Booking**
```
AppointmentPage.java → Doctor Selection → Date/Time → Conflict Checking → Confirmation
```

### 4. **Assessment Integration**
```
Automatic Redirect → AssessmentPage.java → Questions → Evaluation
```

---

## Slide 12: System Flow - Admin & Doctor

# 🎬 Admin & Doctor Workflows

## **Doctor Portal:**
```
WelcomePage.java → Doctor Portal → LoginDrPage.java
Doctor Dashboard → ShowDrAppointmentPage.java → View Appointments
```

## **Administrative Functions:**
```
WelcomePage.java → Admin Portal → LoginAdminPage.java
AdminDashboard.java → AddDrPage.java → Add New Doctor
AdminDashboard.java → View All Appointments → System Monitoring
```

### **Admin Capabilities:**
- Add and manage doctors
- View all system appointments
- System oversight and control

---

## Slide 13: Recent Enhancements

# 🆕 Recent Enhancements

## **Appointment Conflict Prevention System**

### **Problem Solved:**
- ❌ Doctors could be double-booked at the same time
- ❌ Patients could book multiple appointments on the same day
- ❌ No validation for time slot availability

### **Solution Implemented:**
- ✅ Real-time conflict checking during booking
- ✅ Multi-layer validation system
- ✅ Enhanced user feedback and messaging
- ✅ Automatic assessment redirection after booking

### **Technical Implementation:**
Enhanced `AppointmentPage.java` with comprehensive validation methods

---

## Slide 14: Java Implementation

# 💻 Java Implementation Highlights

## **User Interface Styling:**
- **UIStyleManager.java**: Centralized styling system
  - Color schemes and themes
  - Font management
  - Component styling consistency

## **Core Navigation Flow:**
1. `WelcomePage.java` - System entry point
2. Role selection leads to specific login pages
3. Authenticated users access role-specific dashboards
4. Features accessible through navigation menus

## **Key Implementation Features:**
- **Database Integration**: All classes connect via `DBconnection.java`
- **Event-Driven UI**: Swing action listeners and event handling
- **Modular Design**: Separate classes for distinct functionalities

---

## Slide 15: Project Achievements

# 🏆 Project Achievements

## **Technical Implementation:**
✅ **17 Java Classes**: Complete desktop application system  
✅ **4 Database Tables**: Normalized relational design  
✅ **3 User Roles**: Patient, Doctor, Admin portals  
✅ **Conflict Prevention**: Enhanced appointment validation system  
✅ **Assessment Integration**: Seamless booking-to-evaluation workflow  

## **System Features Delivered:**
✅ **User Authentication**: Secure login for all role types  
✅ **Appointment Management**: Booking with conflict prevention  
✅ **Doctor Management**: Admin can add and manage healthcare providers  
✅ **Assessment System**: Psychological evaluation after booking  
✅ **Professional UI**: Consistent styling with UIStyleManager  

---

## Slide 16: Learning Outcomes

# 📚 Skills Demonstrated

## **Technical Skills:**
- **Java Swing Development**: Professional desktop application creation
- **Database Integration**: SQL Server connectivity with JDBC
- **Object-Oriented Design**: Multiple interacting classes and relationships
- **User Interface Design**: Consistent styling and user experience
- **System Enhancement**: Adding conflict prevention and workflow improvements

## **Software Engineering:**
- **Modular Architecture**: 17 separate, focused classes
- **Database Design**: Normalized 4-table schema
- **UML Modeling**: Complete system documentation
- **Error Handling**: Comprehensive validation systems

---

## Slide 17: Conclusion

# 🎯 Project Summary

## **Psychology Center Management System** successfully implements:

- **Desktop Application**: Java Swing with professional UI
- **Database Backend**: Microsoft SQL Server with 4-table schema
- **Multi-Role Access**: Patient, Doctor, and Admin portals
- **Enhanced Workflow**: Appointment booking with assessment integration
- **Conflict Prevention**: Multi-layer validation for appointment scheduling

## **Real Implementation:**
This presentation reflects the **actual implementation** found in the project files without speculation or unfound features.

---

## Slide 18: Thank You

# 🤝 Thank You

## **Project Showcase Summary**

**Psychology Center Management System** represents a comprehensive solution addressing real-world healthcare challenges through:

- **Technical Excellence**: Modern Java architecture
- **User-Centered Design**: Multi-role interface system
- **Operational Efficiency**: Automated administrative processes
- **Enhanced Patient Care**: Systematic assessment integration
- **Scalable Foundation**: Ready for future enhancements

### **Questions & Discussion**

*Ready to discuss technical implementation, UML diagrams, database design, and system architecture*

---

**End of Presentation**

*Psychology Center Management System - Java Swing + SQL Server + UML Design*
