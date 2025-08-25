import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class DoctorsListPage extends JFrame {
    Connection conn = null;
    JLabel label_title;
    JLabel label_info;
    JLabel[] doctor_labels;
    JButton[] appointment_buttons;
    JPanel panel;
    JButton back_but;
    String selectedCondition;
    String source;

    public DoctorsListPage(String condition, String source) {
        super("Doctors List Page");
        this.selectedCondition = condition;
        this.source = source;

        // Create labels
        label_title = new JLabel("Based on your selection/Assessment results");
        label_info = new JLabel("We suggest the following doctors:");

        // Create panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1)); // Dynamic rows

        // Add title labels
        panel.add(label_title);
        panel.add(label_info);

        // Load doctors from database
        loadDoctorsFromDB();

        // Create back button
        back_but = new JButton("Back");
        panel.add(back_but);

        // Add panel to frame
        add(panel, "Center");

        // Set frame properties
        setSize(600, 500);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Back button functionality
        back_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if ("assessment".equals(source)) {
                    new PsychologicalAssessment().setVisible(true);
                } else {
                    new ConditionSelectionPage().setVisible(true);
                }
            }
        });
    }

    // Method to load doctors from database
    private void loadDoctorsFromDB() {
        try {
            // Get connection to database
            conn = DBconnection.getConnection();

            // SQL query to find doctors whose specialization matches the condition
            String query = "select doctor_id, full_name, phone, speciality, address, consultation_fees from doctor where speciality LIKE '%" + selectedCondition + "%'";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Count doctors first to create arrays
            int doctorCount = 0;
            while (rs.next()) {
                doctorCount++;
            }

            if (doctorCount == 0) {
                JLabel no_doctors = new JLabel("No doctors found for " + selectedCondition);
                panel.add(no_doctors);
                return;
            }

            // Reset ResultSet
            rs = st.executeQuery(query);

            // Create arrays for labels and buttons
            doctor_labels = new JLabel[doctorCount * 5]; // 5 labels per doctor
            appointment_buttons = new JButton[doctorCount];

            int index = 0;
            int buttonIndex = 0;

            // Display each doctor
            while (rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                String name = rs.getString("full_name");
                String phone = rs.getString("phone");
                String speciality = rs.getString("speciality");
                String address = rs.getString("address");
                double fees = rs.getDouble("consultation_fees");

                // Create labels for doctor info
                doctor_labels[index] = new JLabel("Doctor: " + name);
                doctor_labels[index + 1] = new JLabel("Phone: " + phone);
                doctor_labels[index + 2] = new JLabel("Speciality: " + speciality);
                doctor_labels[index + 3] = new JLabel("Address: " + address);
                doctor_labels[index + 4] = new JLabel("Consultation Fees: $" + fees);

                // Add labels to panel
                panel.add(doctor_labels[index]);
                panel.add(doctor_labels[index + 1]);
                panel.add(doctor_labels[index + 2]);
                panel.add(doctor_labels[index + 3]);
                panel.add(doctor_labels[index + 4]);

                // Create appointment button for this doctor
                appointment_buttons[buttonIndex] = new JButton("Take Appointment");

                // Add action listener to appointment button
                final int docId = doctorId;
                final String doctorName = name;
                final double doctorFees = fees;
                appointment_buttons[buttonIndex].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Go to appointment page with doctor info and doctor id
                        setVisible(false);
                        new AppointmentPage(docId, doctorName, doctorFees, selectedCondition, source).setVisible(true);
                    }
                });

                panel.add(appointment_buttons[buttonIndex]);

                // Add separator
                panel.add(new JLabel("------------------------"));

                index += 5;
                buttonIndex++;
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            JLabel error_label = new JLabel("Error loading doctors: " + ex.getMessage());
            panel.add(error_label);
        }
    }

    public static void main(String[] args) {
        new DoctorsListPage("Depression", "assessment").setVisible(true);
    }
}
