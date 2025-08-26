import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

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
        
        initializeComponents();
        setupLayout();
        styleComponents();
        loadData();
        addEventListeners();
        centerWindow();
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
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 152, 219));

        // Form styling
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(44, 62, 80);

        JLabel[] labels = {patientLabel, doctorLabel, feesLabel, dateLabel, timeLabel};
        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(labelColor);
        }

        // Text fields styling
        styleTextField(patientField, fieldFont);
        styleTextField(doctorField, fieldFont);
        styleTextField(feesField, fieldFont);

        // Make doctor and fees fields read-only
        doctorField.setEditable(false);
        feesField.setEditable(false);
        doctorField.setBackground(new Color(240, 240, 240));
        feesField.setBackground(new Color(240, 240, 240));

        // Dropdown styling
        styleDropdown(dateDropdown, fieldFont);
        styleDropdown(timeDropdown, fieldFont);

        // Button styling
        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        styleButton(bookButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(backButton, buttonSize, buttonFont, new Color(149, 165, 166), Color.WHITE);
    }

    private void styleTextField(JTextField field, Font font) {
        field.setFont(font);
        field.setPreferredSize(new Dimension(300, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    private void styleDropdown(JComboBox<String> dropdown, Font font) {
        dropdown.setFont(font);
        dropdown.setPreferredSize(new Dimension(300, 35));
        dropdown.setBackground(Color.WHITE);
        dropdown.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
    }

    private void styleButton(JButton button, Dimension size, Font font, Color bgColor, Color textColor) {
        button.setPreferredSize(size);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
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
        String[] times = {
            "Select time...",
            "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM"
        };
        
        for (String time : times) {
            timeDropdown.addItem(time);
        }
    }

    private void addEventListeners() {
        getRootPane().setDefaultButton(bookButton);

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
            
            conn = DBconnection.getConnection();
            String dbTime = convertTimeForDB(selectedTime);
            
            String query = "INSERT INTO appointment (appointment_date, appointment_time, doctor_id, patient_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, selectedDate);
            pstmt.setString(2, dbTime);
            pstmt.setInt(3, doctorId);
            pstmt.setInt(4, patientId);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                showSuccessMessage("Appointment booked successfully!");
                clearForm();
            } else {
                showErrorMessage("Failed to book appointment!");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Booking failed: " + ex.getMessage());
        }
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

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppointmentPage(1, "Dr. Sample Doctor", 150.0).setVisible(true);
        });
    }
}
