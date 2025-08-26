import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ShowAppointmentPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, iconLabel;
    private JPanel mainPanel, headerPanel, buttonPanel;
    private JTable appointmentsTable;
    private JScrollPane scrollPane;
    private JButton backButton, refreshButton;
    private DefaultTableModel tableModel;

    public ShowAppointmentPage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        loadAppointments();
        addEventListeners();
        UIStyleManager.applyWindowConstraints(this, new Dimension(800, 600));
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("All Appointments - Psychology Center");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Header components
        iconLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel = new JLabel("All Scheduled Appointments", SwingConstants.CENTER);

        // Table setup
        String[] columns = {"Appointment ID", "Patient Name", "Doctor Name", "Date", "Time", "Patient Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        appointmentsTable = new JTable(tableModel);
        scrollPane = new JScrollPane(appointmentsTable);

        // Button components
        backButton = new JButton("← Back to Dashboard");
        refreshButton = new JButton("Refresh");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
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
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button panel setup
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(refreshButton);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);

        // Header styling
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UIStyleManager.styleLabel(titleLabel, UIStyleManager.Fonts.TITLE_MEDIUM, UIStyleManager.Colors.PRIMARY_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Table styling
        UIStyleManager.styleTable(appointmentsTable);

        // Scroll pane styling
        scrollPane.setBorder(BorderFactory.createLineBorder(UIStyleManager.Colors.BORDER_LIGHT, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Button styling
        UIStyleManager.styleButton(backButton, UIStyleManager.Colors.TEXT_LIGHT, Color.WHITE, UIStyleManager.Dimensions.BUTTON_MEDIUM);
        UIStyleManager.styleButton(refreshButton, UIStyleManager.Colors.PRIMARY_BLUE, Color.WHITE, UIStyleManager.Dimensions.BUTTON_MEDIUM);
    }

    private void loadAppointments() {
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT a.appointment_id, p.full_name AS patient_name, " +
                          "d.full_name AS doctor_name, a.appointment_date, a.appointment_time, p.phone " +
                          "FROM appointment a " +
                          "JOIN patient p ON a.patient_id = p.patient_id " +
                          "JOIN doctor d ON a.doctor_id = d.doctor_id " +
                          "ORDER BY a.appointment_date DESC, a.appointment_time";
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            // Clear existing data
            tableModel.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("appointment_id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getString("appointment_date"),
                    rs.getString("appointment_time"),
                    rs.getString("phone")
                };
                tableModel.addRow(row);
            }
            
            if (tableModel.getRowCount() == 0) {
                // Add a message row if no appointments found
                Object[] noDataRow = {"No appointments found", "", "", "", "", ""};
                tableModel.addRow(noDataRow);
            }
            
        } catch (Exception ex) {
            showErrorMessage("Error loading appointments: " + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void addEventListeners() {
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AdminDashboard();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAppointments();
                showSuccessMessage("Appointments refreshed successfully!");
            }
        });
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShowAppointmentPage();
        });
    }
}
