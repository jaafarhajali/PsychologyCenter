import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {
    private JLabel welcomeLabel, subtitleLabel;
    private JButton patientButton, doctorButton, adminButton;
    private JPanel mainPanel, headerPanel, buttonPanel;

    public WelcomePage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        centerWindow();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Psychology Center - Welcome");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        welcomeLabel = new JLabel("Psychology Center", SwingConstants.CENTER);
        subtitleLabel = new JLabel("Appointment Booking System", SwingConstants.CENTER);

        // Button components
        patientButton = new JButton("Patient Portal");
        doctorButton = new JButton("Doctor Portal");
        adminButton = new JButton("Admin Portal");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(Box.createVerticalGlue());
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalGlue());

        // Button panel setup
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(patientButton, gbc);
        
        gbc.gridy = 1;
        buttonPanel.add(doctorButton, gbc);
        
        gbc.gridy = 2;
        buttonPanel.add(adminButton, gbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private void styleComponents() {
        // Background gradient
        mainPanel.setBackground(new Color(245, 250, 255));
        
        // Header styling
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(34, 139, 34));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(70, 130, 180));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        
        styleButton(patientButton, buttonSize, buttonFont, new Color(52, 152, 219), Color.WHITE);
        styleButton(doctorButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(adminButton, buttonSize, buttonFont, new Color(155, 89, 182), Color.WHITE);
    }

    private void styleButton(JButton button, Dimension size, Font font, Color bgColor, Color textColor) {
        button.setPreferredSize(size);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
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

    private void addEventListeners() {
        patientButton.addActionListener(e -> openPatientLogin());
        doctorButton.addActionListener(e -> openDoctorLogin());
        adminButton.addActionListener(e -> openAdminLogin());
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    private void openPatientLogin() {
        new LoginPageImproved().setVisible(true);
        dispose();
    }

    private void openDoctorLogin() {
        new LoginDrPageImproved().setVisible(true);
        dispose();
    }

    private void openAdminLogin() {
        new LoginAdminPageImproved().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }
}
