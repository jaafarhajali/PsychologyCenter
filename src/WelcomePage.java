import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomePage extends JFrame {
    private JLabel welcomeLabel, subtitleLabel;
    private JButton patientButton, doctorButton, adminButton;
    private JPanel mainPanel, headerPanel, buttonPanel;

    public WelcomePage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        UIStyleManager.applyWindowConstraints(this, new Dimension(700, 500));
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
        mainPanel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);
        
        // Header styling
        UIStyleManager.styleLabel(welcomeLabel, UIStyleManager.Fonts.TITLE_LARGE, UIStyleManager.Colors.PRIMARY_GREEN);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        UIStyleManager.styleLabel(subtitleLabel, UIStyleManager.Fonts.SUBTITLE, UIStyleManager.Colors.PRIMARY_BLUE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling using UIStyleManager
        UIStyleManager.styleButton(patientButton, UIStyleManager.Colors.PRIMARY_BLUE, Color.WHITE, new Dimension(250, 50));
        UIStyleManager.styleButton(doctorButton, UIStyleManager.Colors.PRIMARY_GREEN, Color.WHITE, new Dimension(250, 50));
        UIStyleManager.styleButton(adminButton, UIStyleManager.Colors.PRIMARY_PURPLE, Color.WHITE, new Dimension(250, 50));
    }

    private void addEventListeners() {
        patientButton.addActionListener(e -> openPatientLogin());
        doctorButton.addActionListener(e -> openDoctorLogin());
        adminButton.addActionListener(e -> openAdminLogin());
    }

    private void openPatientLogin() {
        new LoginPage();
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
