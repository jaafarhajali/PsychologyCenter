import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ConditionSelectionPage extends JFrame {
Connection conn=null;
JLabel label_title;
JLabel label_select;
JComboBox<String> condition_dropdown;
JPanel panel;
JButton find_doctors_but;

ConditionSelectionPage(){
super("Condition Selection Page");

// Create labels
label_title=new JLabel("Condition Selection");
label_select=new JLabel("Select your condition:");

// Create dropdown with conditions
String[] conditions = {
    "Select a condition...",
    "Depression",
    "Anxiety", 
    "ADHD",
    "Bipolar Disorder",
    "OCD",
    "Schizophrenia",
    "Narcissistic Personality Disorder",
    "Normal"
};
condition_dropdown=new JComboBox<>(conditions);

// Create buttons
find_doctors_but=new JButton("Find Doctors");
JButton back_but = new JButton("Back");
back_but.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
        setVisible(false);
        new AssessmentPage().setVisible(true);
    }
});

// Create panel
panel=new JPanel();
panel.setLayout(new GridLayout(5,1));

// Add components to panel
panel.add(label_title);
panel.add(label_select);
panel.add(condition_dropdown);
JPanel buttonPanel = new JPanel(new GridLayout(1,2,10,10));
buttonPanel.add(find_doctors_but);
buttonPanel.add(back_but);
panel.add(buttonPanel);

// Add panel to frame
add(panel,"Center");

// Set frame properties
setSize(400,300);
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Find Doctors button functionality
find_doctors_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
   // Get selected condition
   String selectedCondition = (String) condition_dropdown.getSelectedItem();
   
   // Check if user selected a condition
   if(selectedCondition.equals("Select a condition...")){
       JOptionPane.showMessageDialog(ConditionSelectionPage.this,"Please select a condition!");
       return;
   }
   
   try{
   // Get connection to database
   conn=DBconnection.getConnection();
   
   // Update the latest registered patient's condition_name
   String getPatientIdQuery = "select patient_id from patient order by patient_id desc";
   Statement st=conn.createStatement();
   ResultSet rs = st.executeQuery(getPatientIdQuery);
   int patientId = -1;
   if(rs.next()){
       patientId = rs.getInt("patient_id");
   }
   if(patientId != -1){
       String updateQuery = "update patient set condition_name='"+selectedCondition+"' where patient_id="+patientId;
       int result = st.executeUpdate(updateQuery);
       if(result>0){
           setVisible(false);
           new DoctorsListPageImproved(selectedCondition, "condition").setVisible(true);
       }else{
           JOptionPane.showMessageDialog(ConditionSelectionPage.this,"Failed to save condition!");
       }
   }else{
       JOptionPane.showMessageDialog(ConditionSelectionPage.this,"No patient found to update condition!");
   }
   }
   catch(Exception ex){
       System.out.println("Error: "+ex.getMessage());
       JOptionPane.showMessageDialog(ConditionSelectionPage.this,"Database error: "+ex.getMessage());
   }
 }
});
}

    public static void main(String[] args) {
        new ConditionSelectionPage().setVisible(true);
    }
}
