import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, iconLabel;
    private JPanel mainPanel, headerPanel, contentPanel, buttonPanel;
    private JTabbedPane tabbedPane;
    private JTable doctorsTable, patientsTable, appointmentsTable;
    private JScrollPane doctorsScrollPane, patientsScrollPane, appointmentsScrollPane;
    private JButton addDoctorButton, removeDoctorButton, refreshButton, logoutButton;
    private DefaultTableModel doctorsModel, patientsModel, appointmentsModel;

    public AdminDashboard() {
        initializeComponents();
        setupLayout();
        styleComponents();
        loadData();
        addEventListeners();
        centerWindow();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Admin Dashboard - Psychology Center");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Header components
        iconLabel = new JLabel("🏥", SwingConstants.CENTER);
        titleLabel = new JLabel("Psychology Center - Admin Dashboard", SwingConstants.CENTER);

        // Initialize table models
        String[] doctorColumns = {"ID", "Full Name", "Email", "Phone", "Speciality", "Consultation Fee", "Address"};
        String[] patientColumns = {"ID", "Full Name", "Email", "Phone", "Age", "Condition"};
        String[] appointmentColumns = {"ID", "Patient Name", "Doctor Name", "Date", "Time", "Status"};

        doctorsModel = new DefaultTableModel(doctorColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        patientsModel = new DefaultTableModel(patientColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentsModel = new DefaultTableModel(appointmentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create tables
        doctorsTable = new JTable(doctorsModel);
        patientsTable = new JTable(patientsModel);
        appointmentsTable = new JTable(appointmentsModel);

        // Create scroll panes
        doctorsScrollPane = new JScrollPane(doctorsTable);
        patientsScrollPane = new JScrollPane(patientsTable);
        appointmentsScrollPane = new JScrollPane(appointmentsTable);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Button components
        addDoctorButton = new JButton("👨‍⚕️ Add New Doctor");
        removeDoctorButton = new JButton("🗑️ Remove Doctor");
        refreshButton = new JButton("🔄 Refresh Data");
        logoutButton = new JButton("🚪 Logout");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        contentPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Setup tabbed pane
        tabbedPane.addTab("👨‍⚕️ Doctors", createDoctorsPanel());
        tabbedPane.addTab("👤 Patients", createPatientsPanel());
        tabbedPane.addTab("📅 Appointments", createAppointmentsPanel());

        // Content panel setup
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Button panel setup
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addDoctorButton);
        buttonPanel.add(removeDoctorButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createDoctorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel infoLabel = new JLabel("Manage doctors in the system");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(100, 100, 100));
        infoLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        panel.add(infoLabel, BorderLayout.NORTH);
        panel.add(doctorsScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel infoLabel = new JLabel("View all registered patients");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(100, 100, 100));
        infoLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        panel.add(infoLabel, BorderLayout.NORTH);
        panel.add(patientsScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel infoLabel = new JLabel("View all scheduled appointments");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(100, 100, 100));
        infoLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        panel.add(infoLabel, BorderLayout.NORTH);
        panel.add(appointmentsScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(155, 89, 182));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tabbed pane styling
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        // Table styling
        styleTable(doctorsTable);
        styleTable(patientsTable);
        styleTable(appointmentsTable);

        // Scroll pane styling
        styleScrollPane(doctorsScrollPane);
        styleScrollPane(patientsScrollPane);
        styleScrollPane(appointmentsScrollPane);

        // Button styling
        Dimension buttonSize = new Dimension(160, 40);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);

        styleButton(addDoctorButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(removeDoctorButton, buttonSize, buttonFont, new Color(231, 76, 60), Color.WHITE);
        styleButton(refreshButton, buttonSize, buttonFont, new Color(52, 152, 219), Color.WHITE);
        styleButton(logoutButton, buttonSize, buttonFont, new Color(149, 165, 166), Color.WHITE);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(52, 152, 219, 100));
        table.setSelectionForeground(Color.BLACK);
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
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
        loadDoctors();
        loadPatients();
        loadAppointments();
    }

    private void loadDoctors() {
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT doctor_id, full_name, email, phone, speciality, consultation_fees, address FROM doctor ORDER BY doctor_id";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            // Clear existing data
            doctorsModel.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("doctor_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("speciality"),
                    "$" + rs.getDouble("consultation_fees"),
                    rs.getString("address")
                };
                doctorsModel.addRow(row);
            }
        } catch (Exception ex) {
            showErrorMessage("Error loading doctors: " + ex.getMessage());
        }
    }

    private void loadPatients() {
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT patient_id, full_name, email, phone, age, condition_name FROM patient ORDER BY patient_id";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            // Clear existing data
            patientsModel.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("patient_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("age"),
                    rs.getString("condition_name") != null ? rs.getString("condition_name") : "Not specified"
                };
                patientsModel.addRow(row);
            }
        } catch (Exception ex) {
            showErrorMessage("Error loading patients: " + ex.getMessage());
        }
    }

    private void loadAppointments() {
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT a.appointment_id, p.full_name AS patient_name, d.full_name AS doctor_name, " +
                          "a.appointment_date, a.appointment_time " +
                          "FROM appointment a " +
                          "JOIN patient p ON a.patient_id = p.patient_id " +
                          "JOIN doctor d ON a.doctor_id = d.doctor_id " +
                          "ORDER BY a.appointment_date DESC, a.appointment_time";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            // Clear existing data
            appointmentsModel.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("appointment_id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getString("appointment_date"),
                    rs.getString("appointment_time"),
                    "Scheduled"
                };
                appointmentsModel.addRow(row);
            }
        } catch (Exception ex) {
            showErrorMessage("Error loading appointments: " + ex.getMessage());
        }
    }

    private void addEventListeners() {
        addDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddDrPage();
                dispose();
            }
        });

        removeDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSelectedDoctor();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadData();
                showSuccessMessage("Data refreshed successfully!");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    AdminDashboard.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    new WelcomePage();
                }
            }
        });
    }

    private void removeSelectedDoctor() {
        int selectedRow = doctorsTable.getSelectedRow();
        if (selectedRow == -1) {
            showErrorMessage("Please select a doctor to remove!");
            return;
        }

        int doctorId = (Integer) doctorsModel.getValueAt(selectedRow, 0);
        String doctorName = (String) doctorsModel.getValueAt(selectedRow, 1);

        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to remove Dr. " + doctorName + "?\nThis action cannot be undone.",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                conn = DBconnection.getConnection();
                
                // First check if doctor has appointments
                String checkQuery = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, doctorId);
                ResultSet rs = checkStmt.executeQuery();
                
                if (rs.next() && rs.getInt(1) > 0) {
                    showErrorMessage("Cannot remove doctor with existing appointments!");
                    return;
                }
                
                // Remove doctor
                String deleteQuery = "DELETE FROM doctor WHERE doctor_id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, doctorId);
                int deleteResult = deleteStmt.executeUpdate();
                
                if (deleteResult > 0) {
                    showSuccessMessage("Doctor removed successfully!");
                    loadDoctors(); // Refresh the table
                } else {
                    showErrorMessage("Failed to remove doctor!");
                }
            } catch (Exception ex) {
                showErrorMessage("Error removing doctor: " + ex.getMessage());
            }
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard();
        });
    }
}
