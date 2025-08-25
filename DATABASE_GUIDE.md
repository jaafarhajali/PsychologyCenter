# Psychology Center Database Documentation

## Table-to-Form Mapping Guide

### 1. Login Page (LoginPage.java)
**Form Fields:**
- Email text field
- Password field
- Login button
- Register button

**Database Table Used:** `users`
**Fields Mapped:**
- Email → `users.email`
- Password → `users.password_hash` (should be hashed before storing)

**SQL Query for Login Validation:**
```sql
SELECT user_id, full_name, email FROM users 
WHERE email = ? AND password_hash = ? AND is_active = 1
```

### 2. Registration Page (RegistrationPage.java)
**Form Fields:**
- Full Name text field
- Age text field
- Email text field
- Phone Number text field
- Password field
- Confirm Password field
- Create Account button
- Back to Login button

**Database Table Used:** `users`
**Fields Mapped:**
- Full Name → `users.full_name`
- Age → `users.age`
- Email → `users.email`
- Phone Number → `users.phone_number`
- Password → `users.password_hash` (hash the password before storing)

**SQL Query for Registration:**
```sql
INSERT INTO users (full_name, age, email, phone_number, password_hash, created_at) 
VALUES (?, ?, ?, ?, ?, GETDATE())
```

### 3. Condition Selection Page (ConditionSelectionPage.java)
**Purpose:** Allow users to select psychological conditions/issues they want help with

**Database Tables Used:**
- `conditions` (to display available conditions)
- `user_conditions` (to store user's selected conditions)

**Recommended Form Elements:**
- Checkbox list or dropdown for conditions
- Severity level slider (1-10)
- Notes text area
- Next/Continue button

**SQL Queries:**
```sql
-- Get all available conditions
SELECT condition_id, condition_name, description, category 
FROM conditions WHERE is_active = 1 
ORDER BY category, condition_name

-- Save user's selected conditions
INSERT INTO user_conditions (user_id, condition_id, severity_level, notes) 
VALUES (?, ?, ?, ?)
```

### 4. Assessment Page (AssessmentPage.java)
**Purpose:** Additional assessment or appointment booking

**Database Tables Used:**
- `psychologists` (to show available therapists)
- `time_slots` (to show available time slots)
- `appointments` (to book the appointment)

**Recommended Form Elements:**
- Psychologist selection dropdown
- Date picker
- Time slot selection
- Additional notes text area
- Book Appointment button

## Data Validation Rules

### Registration Form Validation:
1. **Email:** Must be unique, valid email format
2. **Password:** Minimum 8 characters, include special characters
3. **Confirm Password:** Must match password
4. **Age:** Must be between 13-120
5. **Phone Number:** Valid phone format
6. **Full Name:** Minimum 2 characters, no numbers

### Login Form Validation:
1. **Email:** Valid email format, must exist in database
2. **Password:** Must match stored hash

## Security Recommendations

### Password Handling:
```java
// Use BCrypt or similar for password hashing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(plainPassword);
```

### SQL Injection Prevention:
Always use PreparedStatement instead of concatenating strings:

```java
// BAD (vulnerable to SQL injection)
String query = "SELECT * FROM users WHERE email='" + email + "'";

// GOOD (safe from SQL injection)
String query = "SELECT * FROM users WHERE email = ?";
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, email);
```

## Sample Java Methods for Database Operations

### Registration Method:
```java
public boolean registerUser(String fullName, int age, String email, 
                           String phoneNumber, String password) {
    String query = "INSERT INTO users (full_name, age, email, phone_number, password_hash) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, fullName);
        pstmt.setInt(2, age);
        pstmt.setString(3, email);
        pstmt.setString(4, phoneNumber);
        pstmt.setString(5, hashPassword(password)); // implement password hashing
        
        int result = pstmt.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
```

### Login Method:
```java
public boolean authenticateUser(String email, String password) {
    String query = "SELECT user_id, password_hash FROM users WHERE email = ? AND is_active = 1";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            String storedHash = rs.getString("password_hash");
            return verifyPassword(password, storedHash); // implement password verification
        }
        return false;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
```

## Next Steps for Implementation

1. **Run the SQL script** to create your database schema
2. **Update your LoginPage.java** to use prepared statements and proper password hashing
3. **Complete your RegistrationPage.java** with proper form validation
4. **Implement ConditionSelectionPage.java** for condition selection
5. **Add appointment booking functionality** in AssessmentPage.java

## Required Dependencies

Add these to your project's classpath:
- SQL Server JDBC Driver (mssql-jdbc)
- BCrypt for password hashing (spring-security-crypto or jbcrypt)
- Input validation library (Apache Commons Validator)
