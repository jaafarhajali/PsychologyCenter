import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        centerWindow();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("All Appointments - Psychology Center");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Header components
        iconLabel = new JLabel("📅", SwingConstants.CENTER);
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
        refreshButton = new JButton("🔄 Refresh");

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
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Table styling
        appointmentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        appointmentsTable.setRowHeight(25);
        appointmentsTable.setGridColor(new Color(230, 230, 230));
        appointmentsTable.setSelectionBackground(new Color(52, 152, 219, 100));
        appointmentsTable.setSelectionForeground(Color.BLACK);

        // Header styling
        JTableHeader header = appointmentsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        // Scroll pane styling
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Button styling
        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);

        styleButton(backButton, buttonSize, buttonFont, new Color(149, 165, 166), Color.WHITE);
        styleButton(refreshButton, buttonSize, buttonFont, new Color(52, 152, 219), Color.WHITE);
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

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShowAppointmentPage();
        });
    }
}
