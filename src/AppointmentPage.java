import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.Timer;

public class AppointmentPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, patientLabel, doctorLabel, feesLabel, dateLabel, timeLabel;
    private JTextField patientField, doctorField, feesField;
    private JComboBox<String> dateDropdown, timeDropdown;
    private JButton bookButton, backButton;
    private JPanel mainPanel, headerPanel, formPanel, buttonPanel, doctorInfoPanel;
    private int doctorId, patientId;
    private String selectedDoctor;
    private double sessionFees;
    private String previousCondition, previousSource;

    public AppointmentPage(int doctorId, String doctorName, double fees) {
        this.doctorId = doctorId;
        this.selectedDoctor = doctorName;
        this.sessionFees = fees;
        this.previousCondition = "General";
        this.previousSource = "condition";
        
        // Set full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        initializeComponents();
        setupLayout();
        styleComponents();
        loadData();
        addEventListeners();
        setVisible(true);
    }

    public AppointmentPage(int doctorId, String doctorName, double fees, String previousCondition, String previousSource) {
        this(doctorId, doctorName, fees);
        this.previousCondition = previousCondition;
        this.previousSource = previousSource;
    }

    private void initializeComponents() {
        setTitle("Book Appointment - Psychology Center");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        titleLabel = new JLabel("Book Your Appointment", SwingConstants.CENTER);

        // Form components
        patientLabel = new JLabel("Patient Name:");
        doctorLabel = new JLabel("Doctor:");
        feesLabel = new JLabel("Session Fee:");
        dateLabel = new JLabel("Appointment Date:");
        timeLabel = new JLabel("Appointment Time:");

        patientField = new JTextField();
        doctorField = new JTextField();
        feesField = new JTextField();

        dateDropdown = new JComboBox<>();
        timeDropdown = new JComboBox<>();

        // Button components
        bookButton = new JButton("Book Appointment");
        backButton = new JButton("← Back");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        formPanel = new JPanel();
        buttonPanel = new JPanel();
        doctorInfoPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Doctor info panel setup
        doctorInfoPanel.setLayout(new BorderLayout());
        doctorInfoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 1),
            "Doctor Information",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(52, 152, 219)
        ));
        doctorInfoPanel.setBackground(new Color(247, 250, 252));

        JPanel doctorDetailsPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        doctorDetailsPanel.setOpaque(false);
        doctorDetailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        doctorDetailsPanel.add(doctorLabel);
        doctorDetailsPanel.add(doctorField);
        doctorDetailsPanel.add(feesLabel);
        doctorDetailsPanel.add(feesField);

        doctorInfoPanel.add(doctorDetailsPanel, BorderLayout.CENTER);

        // Form panel setup
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(46, 204, 113), 1),
            "Appointment Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(46, 204, 113)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 5, 15);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(patientLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 15, 15, 15);
        formPanel.add(patientField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 15, 5, 15);
        formPanel.add(dateLabel, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 15, 15, 15);
        formPanel.add(dateDropdown, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 15, 5, 15);
        formPanel.add(timeLabel, gbc);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 15, 20, 15);
        formPanel.add(timeDropdown, gbc);

        // Button panel setup
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(bookButton);

        // Add panels to main panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);
        contentPanel.add(doctorInfoPanel, BorderLayout.NORTH);
        contentPanel.add(formPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);

        // Header styling
        UIStyleManager.styleLabel(titleLabel, UIStyleManager.Fonts.TITLE_MEDIUM, UIStyleManager.Colors.PRIMARY_BLUE);

        // Form styling
        JLabel[] labels = {patientLabel, doctorLabel, feesLabel, dateLabel, timeLabel};
        for (JLabel label : labels) {
            UIStyleManager.styleLabel(label, UIStyleManager.Fonts.LABEL_FONT, UIStyleManager.Colors.TEXT_PRIMARY);
        }

        // Text fields styling
        UIStyleManager.styleTextField(patientField, new Dimension(300, 35));
        UIStyleManager.styleTextField(doctorField, new Dimension(300, 35));
        UIStyleManager.styleTextField(feesField, new Dimension(300, 35));

        // Make doctor and fees fields read-only
        doctorField.setEditable(false);
        feesField.setEditable(false);
        doctorField.setBackground(UIStyleManager.Colors.BACKGROUND_CARD);
        feesField.setBackground(UIStyleManager.Colors.BACKGROUND_CARD);

        // Dropdown styling
        dateDropdown.setFont(UIStyleManager.Fonts.BODY_LARGE);
        dateDropdown.setPreferredSize(new Dimension(300, 35));
        dateDropdown.setBackground(Color.WHITE);
        dateDropdown.setBorder(BorderFactory.createLineBorder(UIStyleManager.Colors.BORDER_MEDIUM, 1));
        
        timeDropdown.setFont(UIStyleManager.Fonts.BODY_LARGE);
        timeDropdown.setPreferredSize(new Dimension(300, 35));
        timeDropdown.setBackground(Color.WHITE);
        timeDropdown.setBorder(BorderFactory.createLineBorder(UIStyleManager.Colors.BORDER_MEDIUM, 1));

        // Button styling
        UIStyleManager.styleButton(bookButton, UIStyleManager.Colors.PRIMARY_GREEN, Color.WHITE, UIStyleManager.Dimensions.BUTTON_MEDIUM);
        UIStyleManager.styleButton(backButton, UIStyleManager.Colors.TEXT_LIGHT, Color.WHITE, UIStyleManager.Dimensions.BUTTON_MEDIUM);
    }

    private void loadData() {
        // Fill doctor information
        doctorField.setText(selectedDoctor);
        feesField.setText("$" + sessionFees);

        // Load current patient
        loadCurrentPatient();

        // Load available dates and times
        loadWeekdays();
        loadTimeSlots();
    }

    private void loadCurrentPatient() {
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT patient_id, full_name FROM patient ORDER BY patient_id DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                patientId = rs.getInt("patient_id");
                String patientName = rs.getString("full_name");
                patientField.setText(patientName);
            }
        } catch (Exception ex) {
            System.out.println("Error loading patient: " + ex.getMessage());
        }
    }

    private void loadWeekdays() {
        dateDropdown.removeAllItems();
        dateDropdown.addItem("Select date...");
        
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (int i = 1; i <= 30; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            
            // Only add weekdays (Monday to Friday)
            if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY) {
                dateDropdown.addItem(dateFormat.format(calendar.getTime()));
            }
        }
    }

    private void loadTimeSlots() {
        timeDropdown.removeAllItems();
        timeDropdown.addItem("Select time...");
        
        String[] allTimes = {
            "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM"
        };
        
        // Get selected date to filter available times
        String selectedDate = (String) dateDropdown.getSelectedItem();
        int availableSlots = 0;
        
        for (String time : allTimes) {
            // Only add time slot if it's available for this doctor on the selected date
            if (selectedDate != null && !selectedDate.equals("Select date...")) {
                if (isTimeSlotAvailable(selectedDate, time)) {
                    timeDropdown.addItem(time);
                    availableSlots++;
                }
            } else {
                // If no date selected, show all times
                timeDropdown.addItem(time);
                availableSlots++;
            }
        }
        
        // If no slots available for selected date, show message
        if (selectedDate != null && !selectedDate.equals("Select date...") && availableSlots == 0) {
            timeDropdown.addItem("No available slots for this date");
            timeDropdown.setEnabled(false);
        } else {
            timeDropdown.setEnabled(true);
        }
    }

    private boolean isTimeSlotAvailable(String date, String time) {
        try {
            conn = DBconnection.getConnection();
            String dbTime = convertTimeForDB(time);
            
            // Check if this time slot is already taken by this doctor
            String query = "SELECT COUNT(*) as count FROM appointment WHERE doctor_id = ? AND appointment_date = ? AND appointment_time = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, doctorId);
            pstmt.setString(2, date);
            pstmt.setString(3, dbTime);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") == 0; // Available if count is 0
            }
            return true; // Default to available if query fails
            
        } catch (Exception ex) {
            System.out.println("Error checking time slot availability: " + ex.getMessage());
            return true; // Default to available on error
        }
    }

    private void addEventListeners() {
        getRootPane().setDefaultButton(bookButton);

        // Add listener to date dropdown to refresh time slots when date changes
        dateDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Refresh time slots when date changes
                loadTimeSlots();
            }
        });

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookAppointment();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DoctorsListPage(previousCondition, previousSource).setVisible(true);
            }
        });
    }

    private void bookAppointment() {
        try {
            String patientName = patientField.getText();
            String selectedDate = (String) dateDropdown.getSelectedItem();
            String selectedTime = (String) timeDropdown.getSelectedItem();
            
            if (patientName.isEmpty() || "Select date...".equals(selectedDate) || "Select time...".equals(selectedTime)) {
                showErrorMessage("Please fill all fields!");
                return;
            }
            
            // Check if no slots are available message is selected
            if ("No available slots for this date".equals(selectedTime)) {
                showErrorMessage("No available time slots for the selected date. Please choose a different date.");
                return;
            }
            
            conn = DBconnection.getConnection();
            String dbTime = convertTimeForDB(selectedTime);
            
            // Check for conflicts and duplications before booking
            if (checkAppointmentConflicts(selectedDate, dbTime)) {
                return; // Error message already shown in the check method
            }
            
            String query = "INSERT INTO appointment (appointment_date, appointment_time, doctor_id, patient_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, selectedDate);
            pstmt.setString(2, dbTime);
            pstmt.setInt(3, doctorId);
            pstmt.setInt(4, patientId);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                showSuccessMessage("Appointment booked successfully!\nYou will now be taken to the assessment page to help us better understand your needs.");
                clearForm();
                // Redirect to assessment page after successful booking
                redirectToAssessment();
            } else {
                showErrorMessage("Failed to book appointment!");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Booking failed: " + ex.getMessage());
        }
    }

    private boolean checkAppointmentConflicts(String selectedDate, String selectedTime) {
        try {
            // Check 1: Doctor time conflict - Same doctor, same date and time
            if (checkDoctorTimeConflict(selectedDate, selectedTime)) {
                showErrorMessage("Time conflict! This doctor already has an appointment at " + 
                               convertTimeForDisplay(selectedTime) + " on " + selectedDate + 
                               ".\nPlease select a different time slot.");
                return true;
            }
            
            // Check 2: Patient duplicate appointment - Same patient, same date (regardless of time)
            if (checkPatientDuplicateAppointment(selectedDate)) {
                showErrorMessage("Duplicate appointment! You already have an appointment scheduled on " + selectedDate + 
                               ".\nPlease select a different date or cancel your existing appointment first.");
                return true;
            }
            
            // Check 3: Patient time conflict - Same patient, same date and time with different doctor
            if (checkPatientTimeConflict(selectedDate, selectedTime)) {
                showErrorMessage("Time conflict! You already have an appointment at " + 
                               convertTimeForDisplay(selectedTime) + " on " + selectedDate + 
                               " with another doctor.\nPlease select a different time slot.");
                return true;
            }
            
            return false; // No conflicts found
            
        } catch (Exception ex) {
            System.out.println("Error checking conflicts: " + ex.getMessage());
            showErrorMessage("Error checking appointment conflicts. Please try again.");
            return true; // Return true to prevent booking on error
        }
    }

    private boolean checkDoctorTimeConflict(String date, String time) throws Exception {
        String query = "SELECT COUNT(*) as conflict_count FROM appointment WHERE doctor_id = ? AND appointment_date = ? AND appointment_time = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, doctorId);
        pstmt.setString(2, date);
        pstmt.setString(3, time);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("conflict_count") > 0;
        }
        return false;
    }

    private boolean checkPatientDuplicateAppointment(String date) throws Exception {
        String query = "SELECT COUNT(*) as duplicate_count FROM appointment WHERE patient_id = ? AND appointment_date = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, patientId);
        pstmt.setString(2, date);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("duplicate_count") > 0;
        }
        return false;
    }

    private boolean checkPatientTimeConflict(String date, String time) throws Exception {
        String query = "SELECT COUNT(*) as conflict_count FROM appointment WHERE patient_id = ? AND appointment_date = ? AND appointment_time = ? AND doctor_id != ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, patientId);
        pstmt.setString(2, date);
        pstmt.setString(3, time);
        pstmt.setInt(4, doctorId);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("conflict_count") > 0;
        }
        return false;
    }

    private String convertTimeForDisplay(String dbTime) {
        // Convert database time format back to display format
        if (dbTime.contains(":")) {
            String[] parts = dbTime.split(":");
            int hour = Integer.parseInt(parts[0]);
            if (hour == 0) {
                return "12:00 AM";
            } else if (hour < 12) {
                return hour + ":00 AM";
            } else if (hour == 12) {
                return "12:00 PM";
            } else {
                return (hour - 12) + ":00 PM";
            }
        }
        return dbTime;
    }

    private String convertTimeForDB(String time) {
        if (time.contains("AM") || time.contains("PM")) {
            return time.replace(" AM", ":00").replace(" PM", ":00")
                      .replace("12:", "00:").replace("01:", "13:")
                      .replace("02:", "14:").replace("03:", "15:")
                      .replace("04:", "16:");
        }
        return time;
    }

    private void clearForm() {
        dateDropdown.setSelectedIndex(0);
        timeDropdown.setSelectedIndex(0);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Booking Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void redirectToAssessment() {
        // Add a small delay to let user read the success message
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close current appointment page and open assessment page
                setVisible(false);
                new AssessmentPage("appointment");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppointmentPage(1, "Dr. Sample Doctor", 150.0).setVisible(true);
        });
    }
}
