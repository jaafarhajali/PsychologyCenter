import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class AssessmentPage extends JFrame {
Connection conn=null;
JLabel label_title;
JLabel label_question;
JPanel panel;
JButton know_condition_but;
JButton need_assessment_but;
JButton back_but;

AssessmentPage(){
super("Assessment Choice Page");

// Create labels
label_title=new JLabel("Assessment Choice Page");
label_question=new JLabel("<html><center>Do you already know the condition (diagnosis)<br>or would you like to answer a short assessment<br>to help us suggest a doctor?</center></html>");

// Create buttons
know_condition_but=new JButton("I already know the condition");
need_assessment_but=new JButton("I need an assessment");
back_but=new JButton("Back");

// Create panel
panel=new JPanel();
panel.setLayout(new GridLayout(5,1));

// Add components to panel
panel.add(label_title);
panel.add(label_question);
panel.add(know_condition_but);
panel.add(need_assessment_but);
panel.add(back_but);

// Add panel to frame
add(panel,"Center");

// Set frame properties
setSize(500,300);
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Button for "I already know the condition"
know_condition_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     // Go to condition selection page
     setVisible(false);
     new ConditionSelectionPage().setVisible(true);
 }
});

// Button for "I need an assessment"
need_assessment_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     // Go to psychological assessment page
     setVisible(false);
     new PsychologicalAssessment().setVisible(true);
 }
});

// Back button functionality
back_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     // Go back to welcome page
     setVisible(false);
     new WelcomePage().setVisible(true);
 }
});
}

    public static void main(String[] args) {
        new AssessmentPage().setVisible(true);
    }
}
