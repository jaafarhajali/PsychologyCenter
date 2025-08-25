import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class AddDrPage extends JFrame {
Connection conn=null;
JLabel label_title;
JLabel label_fullname;
JLabel label_email;
JLabel label_phone;
JLabel label_address;
JLabel label_speciality;
JLabel label_fee;
JLabel label_password;
JTextField text_fullname;
JTextField text_email;
JTextField text_phone;
JTextField text_address;
JComboBox<String> speciality_dropdown;
JTextField text_fee;
JPasswordField pass;
JPanel panel;
JButton save_but;
JButton back_but;
JButton logout_but;

AddDrPage(){
super("Add Doctor Page");
label_title=new JLabel("Please fill in the details below to add a new doctor to the system");
label_fullname=new JLabel("Full Name");
label_email=new JLabel("Email");
label_phone=new JLabel("Phone Number");
label_address=new JLabel("Address");
label_speciality=new JLabel("Speciality");
label_fee=new JLabel("Consultation Fee");
label_password=new JLabel("Password");
text_fullname=new JTextField();
text_email=new JTextField();
text_phone=new JTextField();
text_address=new JTextField();
String[] specialities = {"Depression", "ADHD", "Anxiety", "Bipolar Disorder", "OCD", "Schizophrenia", "Narcissistic Personality Disorder", "Normal"};
speciality_dropdown=new JComboBox<>(specialities);
text_fee=new JTextField();
pass=new JPasswordField();
save_but=new JButton("Save Doctor");
back_but=new JButton("Back");
logout_but=new JButton("Logout");

panel=new JPanel();
panel.setLayout(new GridLayout(11,2));
panel.add(label_title);
panel.add(new JLabel("")); // Empty space
panel.add(label_fullname);
panel.add(text_fullname);
panel.add(label_email);
panel.add(text_email);
panel.add(label_phone);
panel.add(text_phone);
panel.add(label_address);
panel.add(text_address);
panel.add(label_speciality);
panel.add(speciality_dropdown);
panel.add(label_fee);
panel.add(text_fee);
panel.add(label_password);
panel.add(pass);
panel.add(save_but);
panel.add(back_but);
panel.add(logout_but);
panel.add(new JLabel(""));

add(panel,"Center");
setSize(500,500);
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

save_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){ 
   try{
   conn=DBconnection.getConnection();
   String fullname=text_fullname.getText();
   String email=text_email.getText();
   String phone=text_phone.getText();
   String address=text_address.getText();
   String speciality=(String)speciality_dropdown.getSelectedItem();
   String fee=text_fee.getText();
   String password=new String(pass.getPassword());
   
   // Validate input fields
   if(fullname.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || 
      address.trim().isEmpty() || fee.trim().isEmpty() || password.isEmpty()){
       JOptionPane.showMessageDialog(AddDrPage.this,"Please fill all fields!");
       return;
   }
   
   // Validate email format
   if(!email.contains("@") || !email.contains(".")){
       JOptionPane.showMessageDialog(AddDrPage.this,"Please enter a valid email address!");
       return;
   }
   
   // Validate password strength
   if(password.length() < 6){
       JOptionPane.showMessageDialog(AddDrPage.this,"Password must be at least 6 characters long!");
       return;
   }
   
   // Check if email already exists
   String checkEmailQuery = "SELECT email FROM doctor WHERE email = ?";
   PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery);
   checkStmt.setString(1, email);
   ResultSet emailResult = checkStmt.executeQuery();
   
   if(emailResult.next()){
       JOptionPane.showMessageDialog(AddDrPage.this,"Email already exists! Please use a different email.");
       return;
   }
   
   // Hash the password
   String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
   String hashedPassword = passwordData[0];
   String salt = passwordData[1];
   
   // SQL query to insert new doctor with hashed password
   String query = "INSERT INTO doctor (full_name,email,phone,address,speciality,consultation_fees,password,password_salt) VALUES (?,?,?,?,?,?,?,?)";
   PreparedStatement pstmt = conn.prepareStatement(query);
   pstmt.setString(1, fullname);
   pstmt.setString(2, email);
   pstmt.setString(3, phone);
   pstmt.setString(4, address);
   pstmt.setString(5, speciality);
   pstmt.setString(6, fee);
   pstmt.setString(7, hashedPassword);
   pstmt.setString(8, salt);
   
   int result = pstmt.executeUpdate();
   if(result>0){
       JOptionPane.showMessageDialog(AddDrPage.this,"Doctor added successfully!");
       // Clear form
       text_fullname.setText("");
       text_email.setText("");
       text_phone.setText("");
       text_address.setText("");
       speciality_dropdown.setSelectedIndex(0);
       text_fee.setText("");
       pass.setText("");
   }
   else{
       JOptionPane.showMessageDialog(AddDrPage.this,"Failed to add doctor!");
   }
   }
   catch(Exception ex){
       System.out.println("Error: "+ex.getMessage());
       JOptionPane.showMessageDialog(AddDrPage.this,"Error: "+ex.getMessage());
   }
 }
});

back_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     dispose();
     new AdminSelectionPageImproved().setVisible(true);
 }
});

logout_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     dispose();
     new WelcomePage().setVisible(true);
 }
});
}
    public static void main(String[] args) {
        new AddDrPage().setVisible(true);
    }
}
