import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class RegistrationPage extends JFrame {
Connection conn=null;
JLabel label_registration;
JLabel label_fullname;
JLabel label_age;
JLabel label_email;
JLabel label_phone;
JLabel label_password;
JLabel label_confirmpass;
JTextField text_fullname;
JTextField text_age;
JTextField text_email;
JTextField text_phone;
JPasswordField pass;
JPasswordField confirmpass;
JPanel panel;
JButton create_but;
JButton back_but;

RegistrationPage(){
super("Registration Form");

// Create labels
label_registration=new JLabel("Registration Form");
label_fullname=new JLabel("Full Name");
label_age=new JLabel("Age");
label_email=new JLabel("Email");
label_phone=new JLabel("Phone Number");
label_password=new JLabel("Password");
label_confirmpass=new JLabel("Confirm Password");

// Create text fields
text_fullname=new JTextField();
text_age=new JTextField();
text_email=new JTextField();
text_phone=new JTextField();
pass=new JPasswordField();
confirmpass=new JPasswordField();

// Create buttons
create_but=new JButton("Create Account");
back_but=new JButton("Back to Login");

// Create panel
panel=new JPanel();
panel.setLayout(new GridLayout(8,2));

// Add components to panel
panel.add(label_registration);
panel.add(new JLabel("")); // Empty space
panel.add(label_fullname);
panel.add(text_fullname);
panel.add(label_age);
panel.add(text_age);
panel.add(label_email);
panel.add(text_email);
panel.add(label_phone);
panel.add(text_phone);
panel.add(label_password);
panel.add(pass);
panel.add(label_confirmpass);
panel.add(confirmpass);
panel.add(create_but);
panel.add(back_but);

// Add panel to frame
add(panel,"Center");

// Set frame properties
setSize(400,500);
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Create Account button functionality
create_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
   try{
   // Get connection to database
   conn=DBconnection.getConnection();
   
   // Get data from text fields
   String fullname=text_fullname.getText();
   String age=text_age.getText();
   String email=text_email.getText();
   String phone=text_phone.getText();
   String password=new String(pass.getPassword());
   String confirmpassword=new String(confirmpass.getPassword());
   
   // Validate input fields
   if(fullname.trim().isEmpty() || age.trim().isEmpty() || email.trim().isEmpty() || 
      phone.trim().isEmpty() || password.isEmpty()){
       JOptionPane.showMessageDialog(RegistrationPage.this,"Please fill all fields!");
       return;
   }
   
   // Validate email format
   if(!email.contains("@") || !email.contains(".")){
       JOptionPane.showMessageDialog(RegistrationPage.this,"Please enter a valid email address!");
       return;
   }
   
   // Check if passwords match
   if(!password.equals(confirmpassword)){
       JOptionPane.showMessageDialog(RegistrationPage.this,"Passwords do not match!");
       return;
   }
   
   // Validate password strength
   if(password.length() < 6){
       JOptionPane.showMessageDialog(RegistrationPage.this,"Password must be at least 6 characters long!");
       return;
   }
   
   // Check if email already exists
   String checkEmailQuery = "SELECT email FROM patient WHERE email = ?";
   PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery);
   checkStmt.setString(1, email);
   ResultSet emailResult = checkStmt.executeQuery();
   
   if(emailResult.next()){
       JOptionPane.showMessageDialog(RegistrationPage.this,"Email already exists! Please use a different email.");
       return;
   }
   
   // Generate salt and hash password
   String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
   String hashedPassword = passwordData[0];
   String salt = passwordData[1];
   
   // SQL query to insert new patient with hashed password and salt
   String query = "INSERT INTO patient (full_name, age, email, phone, password, password_salt) VALUES (?, ?, ?, ?, ?, ?)";
   PreparedStatement pstmt = conn.prepareStatement(query);
   pstmt.setString(1, fullname);
   pstmt.setString(2, age);
   pstmt.setString(3, email);
   pstmt.setString(4, phone);
   pstmt.setString(5, hashedPassword);
   pstmt.setString(6, salt);
   
   int result = pstmt.executeUpdate();
   
   // If registration successful
   if(result>0){
       JOptionPane.showMessageDialog(RegistrationPage.this,"Account created successfully!");
       // Clear form
       text_fullname.setText("");
       text_age.setText("");
       text_email.setText("");
       text_phone.setText("");
       pass.setText("");
       confirmpass.setText("");
       // Go back to login page
       setVisible(false);
       new LoginPage().setVisible(true);
   }
   else{
       JOptionPane.showMessageDialog(RegistrationPage.this,"Registration failed!");
   }
   }
   catch(Exception ex){
       System.out.println("Error: "+ex.getMessage());
       JOptionPane.showMessageDialog(RegistrationPage.this,"Registration failed: "+ex.getMessage());
   }
 }
});

// Back to Login button functionality
back_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     // Go back to Login Page
     setVisible(false);
     new LoginPage().setVisible(true);
 }
});
}

    public static void main(String[] args) {
        new RegistrationPage().setVisible(true);
    }
}
