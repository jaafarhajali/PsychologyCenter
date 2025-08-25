import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LoginPage extends JFrame {
Connection conn=null;
JLabel label_login;
JLabel label_email;
JLabel label_pass;
JPasswordField pass;
JTextField text;
JPanel panel;
JButton log_but;
JButton reg_but;
LoginPage(){
super("login page");
label_login=new JLabel("Login");
label_email=new JLabel("Email");
label_pass=new JLabel("Password");
pass=new JPasswordField();
text=new JTextField();
panel=new JPanel();
log_but=new JButton("Login");
JButton back_but = new JButton("Back");
back_but.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
        setVisible(false);
        new WelcomePage();
    }
});
reg_but=new JButton("Don't have an account?Register now");

panel.setLayout(new GridLayout(4,2,10,10));
panel.add(label_email);
panel.add(text);
panel.add(label_pass);
panel.add(pass);
panel.add(log_but);
panel.add(back_but);
panel.add(reg_but);
panel.add(new JLabel(""));
add(panel,"Center");
getRootPane().setDefaultButton(log_but);
setSize(400,200);
setResizable(true);

log_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){ 
   try{
   // Get connection to database
   conn=DBconnection.getConnection();
   
   // Get email and password from text fields
   String email=text.getText();
   String password=new String(pass.getPassword());
   
   // Validate input
   if(email.trim().isEmpty() || password.isEmpty()){
       JOptionPane.showMessageDialog(LoginPage.this,"Please enter both email and password!");
       return;
   }
   
   // SQL query to get patient's hashed password and salt
   String query = "SELECT email, password, password_salt FROM patient WHERE email = ?";
   PreparedStatement pstmt = conn.prepareStatement(query);
   pstmt.setString(1, email);
   ResultSet rs = pstmt.executeQuery();
   
   // If patient found, verify password
   if(rs.next()){
       String storedPassword = rs.getString("password");
       String storedSalt = rs.getString("password_salt");
       
       // Handle legacy passwords (if salt is null, it's an old plain text password)
       boolean loginSuccessful = false;
       if(storedSalt == null || storedSalt.isEmpty()){
           // Legacy plain text password comparison
           loginSuccessful = password.equals(storedPassword);
           
           // If login successful with plain text, update to hashed password
           if(loginSuccessful){
               String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
               String newHashedPassword = passwordData[0];
               String newSalt = passwordData[1];
               
               String updateQuery = "UPDATE patient SET password = ?, password_salt = ? WHERE email = ?";
               PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
               updateStmt.setString(1, newHashedPassword);
               updateStmt.setString(2, newSalt);
               updateStmt.setString(3, email);
               updateStmt.executeUpdate();
               
               System.out.println("Password upgraded to hashed version for user: " + email);
           }
       } else {
           // Use proper password verification for hashed passwords
           loginSuccessful = PasswordUtils.verifyPassword(password, storedPassword, storedSalt);
       }
       
       if(loginSuccessful){
           // Hide login page and go to assessment page
           setVisible(false);
           new AssessmentPage().setVisible(true);
       } else {
           JOptionPane.showMessageDialog(LoginPage.this,"Wrong email or password!");
       }
   }
   else{
       // Show error message if login failed
       JOptionPane.showMessageDialog(LoginPage.this,"Wrong email or password!");
   }
   }
   catch(Exception ex){
       System.out.println("Error: "+ex.getMessage());
       JOptionPane.showMessageDialog(LoginPage.this,"Login failed: " + ex.getMessage());
   }
 }
});

// Add register button functionality
reg_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     // Go to Registration Page
     setVisible(false);
     new RegistrationPage().setVisible(true);
 }
});
}
    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}

