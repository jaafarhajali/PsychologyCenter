import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class DoctorsListPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, conditionLabel, instructionLabel;
    private JPanel mainPanel, headerPanel, doctorsPanel, buttonPanel;
    private JScrollPane scrollPane;
    private JButton backButton;
    private String selectedCondition, previousSource;

    public DoctorsListPage(String condition, String source) {
        this.selectedCondition = condition;
        this.previousSource = source;
        
        initializeComponents();
        setupLayout();
        styleComponents();
        loadDoctorsFromDB();
        addEventListeners();
        centerWindow();
    }

    private void initializeComponents() {
        setTitle("Available Doctors - Psychology Center");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Header components
        titleLabel = new JLabel("Available Doctors", SwingConstants.CENTER);
        conditionLabel = new JLabel("Specialists for: " + selectedCondition, SwingConstants.CENTER);
        instructionLabel = new JLabel("Select a doctor to book an appointment", SwingConstants.CENTER);

        // Button components
        backButton = new JButton("← Back");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        doctorsPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(conditionLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(instructionLabel);

        // Doctors panel setup (will be populated dynamically)
        doctorsPanel.setLayout(new BoxLayout(doctorsPanel, BoxLayout.Y_AXIS));
        doctorsPanel.setOpaque(false);

        // Scroll pane for doctors
        scrollPane = new JScrollPane(doctorsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);

        // Button panel setup
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        conditionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        conditionLabel.setForeground(new Color(46, 204, 113));
        conditionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(100, 100, 100));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Back button styling
        styleButton(backButton, new Dimension(100, 35), new Font("Segoe UI", Font.BOLD, 12), 
                   new Color(149, 165, 166), Color.WHITE);
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

    private void loadDoctorsFromDB() {
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT doctor_id, full_name, speciality, consultation_fees, phone, address FROM doctor WHERE speciality = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, selectedCondition);
            ResultSet rs = pstmt.executeQuery();

            boolean foundDoctors = false;
            while (rs.next()) {
                foundDoctors = true;
                int doctorId = rs.getInt("doctor_id");
                String name = rs.getString("full_name");
                String speciality = rs.getString("speciality");
                double fees = rs.getDouble("consultation_fees");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                // Create doctor card
                JPanel doctorCard = createDoctorCard(doctorId, name, speciality, fees, phone, address);
                doctorsPanel.add(doctorCard);
                doctorsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }

            if (!foundDoctors) {
                JLabel noDoctorsLabel = new JLabel("No doctors found for this condition.", SwingConstants.CENTER);
                noDoctorsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                noDoctorsLabel.setForeground(new Color(150, 150, 150));
                doctorsPanel.add(noDoctorsLabel);
            }

            // Refresh the panel
            doctorsPanel.revalidate();
            doctorsPanel.repaint();

        } catch (Exception ex) {
            System.out.println("Error loading doctors: " + ex.getMessage());
            JLabel errorLabel = new JLabel("Error loading doctors. Please try again.", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            errorLabel.setForeground(Color.RED);
            doctorsPanel.add(errorLabel);
        }
    }

    private JPanel createDoctorCard(int doctorId, String name, String speciality, double fees, String phone, String address) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Doctor info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        // Doctor name
        JLabel nameLabel = new JLabel("Dr. " + name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(new Color(52, 152, 219));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Speciality
        JLabel specialityLabel = new JLabel("Speciality: " + speciality);
        specialityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        specialityLabel.setForeground(new Color(100, 100, 100));
        specialityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Phone
        JLabel phoneLabel = new JLabel("Phone: " + (phone != null ? phone : "Not available"));
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneLabel.setForeground(new Color(100, 100, 100));
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Address
        JLabel addressLabel = new JLabel("Address: " + (address != null ? address : "Not available"));
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressLabel.setForeground(new Color(100, 100, 100));
        addressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fees
        JLabel feesLabel = new JLabel("Consultation Fee: $" + fees);
        feesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        feesLabel.setForeground(new Color(46, 204, 113));
        feesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(specialityLabel);
        infoPanel.add(phoneLabel);
        infoPanel.add(addressLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(feesLabel);

        // Appointment button
        JButton appointmentButton = new JButton("Book Appointment");
        styleButton(appointmentButton, new Dimension(150, 35), new Font("Segoe UI", Font.BOLD, 12), 
                   new Color(52, 152, 219), Color.WHITE);

        appointmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AppointmentPage(doctorId, name, fees, selectedCondition, previousSource).setVisible(true);
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(appointmentButton);

        // Add components to card
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.EAST);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(247, 250, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        return card;
    }

    private void addEventListeners() {
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if ("assessment".equals(previousSource)) {
                    new AssessmentPage().setVisible(true);
                } else {
                    new ConditionSelectionPage().setVisible(true);
                }
            }
        });
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DoctorsListPage("Depression", "condition").setVisible(true);
        });
    }
}
