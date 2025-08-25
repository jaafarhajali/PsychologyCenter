import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {
    JLabel welcomeLabel;
    JButton patientButton, doctorButton, adminButton;

    public WelcomePage() {
        setTitle("Welcome Page");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255));

        // Use HTML for multi-line and word wrap
        welcomeLabel = new JLabel("<html><center>Welcome to Psychology Center<br>Appointment Booking System</center></html>", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setBounds(50, 30, 500, 80);

        patientButton = new JButton("Patient");
        doctorButton = new JButton("Doctor");
        adminButton = new JButton("Admin");

        patientButton.setBounds(225, 130, 150, 40);
        doctorButton.setBounds(225, 190, 150, 40);
        adminButton.setBounds(225, 250, 150, 40);

        patientButton.setBackground(new Color(135, 206, 250));
        doctorButton.setBackground(new Color(135, 206, 250));
        adminButton.setBackground(new Color(135, 206, 250));

        add(welcomeLabel);
        add(patientButton);
        add(doctorButton);
        add(adminButton);

        patientButton.addActionListener(e -> openPatientLogin());
        doctorButton.addActionListener(e -> openDoctorLogin());
        adminButton.addActionListener(e -> openAdminLogin());

        setVisible(true);
    }

    private void openPatientLogin() {
    new LoginPage().setVisible(true);
    dispose();
    }

    private void openDoctorLogin() {
    new LoginDrPage().setVisible(true);
    dispose();
    }

    private void openAdminLogin() {
    new LoginAdminPage().setVisible(true);
    dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }
}
