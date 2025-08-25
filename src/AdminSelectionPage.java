import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminSelectionPage extends JFrame {
    JLabel instructionLabel;
    JButton showAppointmentsButton;
    JButton addDoctorButton;
    JButton logoutButton;
    JPanel panel;

    public AdminSelectionPage() {
        super("Admin Selection Page");
        instructionLabel = new JLabel("Please select an action to continue:");
        showAppointmentsButton = new JButton("See All Doctor's Appointments");
        addDoctorButton = new JButton("Add a New Doctor");
        logoutButton = new JButton("Logout");

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 20));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        showAppointmentsButton.setBackground(new Color(135, 206, 250));
        addDoctorButton.setBackground(new Color(135, 206, 250));
        logoutButton.setBackground(new Color(255, 182, 193)); // Light pink for logout

        panel.add(instructionLabel);
        panel.add(showAppointmentsButton);
        panel.add(addDoctorButton);
        panel.add(logoutButton);

        add(panel, "Center");
        setSize(400, 300);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showAppointmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ShowAppointmentPage().setVisible(true);
                setVisible(false);
            }
        });
        addDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddDrPage().setVisible(true);
                setVisible(false);
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WelcomePage().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new AdminSelectionPage().setVisible(true);
    }
}
