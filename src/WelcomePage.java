import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {
    private JLabel logoLabel, titleLabel, subtitleLabel;
    private JButton patientButton, doctorButton, adminButton;
    private JPanel headerPanel, mainPanel, buttonPanel, footerPanel;
    private JPanel loadingPanel;

    public WelcomePage() {
        setTitle("MindCare Psychology Center - Professional Mental Health Services");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(900, 600));
        
        initializeComponents();
        setupLayout();
        addEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        // Create gradient header panel
        headerPanel = ModernUIUtils.createGradientPanel(
            ModernUIUtils.PRIMARY_BLUE, 
            ModernUIUtils.PRIMARY_BLUE_DARK
        );
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(
            ModernUIUtils.SPACING_XL, ModernUIUtils.SPACING_XL, 
            ModernUIUtils.SPACING_XL, ModernUIUtils.SPACING_XL
        ));
        
        // Logo with medical icon
        logoLabel = new JLabel(ModernUIUtils.ICON_HOSPITAL, SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setPreferredSize(new Dimension(80, 80));
        
        // Title and subtitle panel with modern typography
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        titleLabel = new JLabel("<html><center>MindCare Psychology Center</center></html>");
        titleLabel.setFont(ModernUIUtils.FONT_HEADING_LARGE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        subtitleLabel = new JLabel("<html><center>Professional Mental Health Services & Consultation Platform<br>" +
                                  "Your Mental Wellness is Our Priority</center></html>");
        subtitleLabel.setFont(ModernUIUtils.FONT_BODY_LARGE);
        subtitleLabel.setForeground(new Color(220, 235, 255));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(Box.createVerticalStrut(ModernUIUtils.SPACING_MD), BorderLayout.SOUTH);
        
        JPanel subtitleContainer = new JPanel(new BorderLayout());
        subtitleContainer.setOpaque(false);
        subtitleContainer.add(subtitleLabel, BorderLayout.CENTER);
        
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(subtitleContainer, BorderLayout.SOUTH);
        
        // Main content panel with card-based layout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ModernUIUtils.BACKGROUND_PRIMARY);
        mainPanel.setBorder(new EmptyBorder(
            ModernUIUtils.SPACING_XXL, ModernUIUtils.SPACING_XXL, 
            ModernUIUtils.SPACING_XXL, ModernUIUtils.SPACING_XXL
        ));
        
        // Welcome card
        JPanel welcomeCard = ModernUIUtils.createCardPanel();
        welcomeCard.setLayout(new BorderLayout());
        
        JLabel welcomeMsg = new JLabel("<html><center><h2 style='color: #2D3436; margin: 0;'>" +
                                      "Welcome to Our Digital Health Platform</h2>" +
                                      "<p style='color: #636E72; margin-top: 16px; font-size: 16px;'>" +
                                      "Please select your role to access the appropriate portal:</p></center></html>");
        welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeCard.add(welcomeMsg, BorderLayout.CENTER);
        
        // Enhanced button panel with cards
        buttonPanel = new JPanel(new GridLayout(1, 3, ModernUIUtils.SPACING_LG, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(
            ModernUIUtils.SPACING_XL, 0, ModernUIUtils.SPACING_XL, 0
        ));
        
        // Create enhanced portal buttons
        patientButton = createEnhancedPortalButton(
            ModernUIUtils.ICON_USER + " PATIENT PORTAL", 
            "<html><center>Book Appointments<br>View Medical Records<br>Take Assessments</center></html>",
            ModernUIUtils.SUCCESS_GREEN
        );
        
        doctorButton = createEnhancedPortalButton(
            ModernUIUtils.ICON_DOCTOR + " DOCTOR PORTAL", 
            "<html><center>Manage Appointments<br>Patient Consultations<br>Medical Records</center></html>",
            ModernUIUtils.PRIMARY_BLUE
        );
        
        adminButton = createEnhancedPortalButton(
            ModernUIUtils.ICON_MEDICAL_CROSS + " ADMIN PORTAL", 
            "<html><center>System Management<br>Doctor Registration<br>Appointment Overview</center></html>",
            ModernUIUtils.ACCENT_TEAL
        );
        
        buttonPanel.add(patientButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(adminButton);
        
        // Info panel with modern styling
        JPanel infoPanel = ModernUIUtils.createCardPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            infoPanel.getBorder(),
            new EmptyBorder(ModernUIUtils.SPACING_MD, 0, 0, 0)
        ));
        
        JLabel infoLabel = new JLabel("<html><center>" +
                                    "<p style='color: #2D3436; font-size: 16px; font-weight: bold;'>" +
                                    "New Patient? Create an account to:</p>" +
                                    "<div style='color: #636E72; font-size: 14px; margin-top: 12px;'>" +
                                    "📅 Book appointments with specialists<br>" +
                                    "📋 Take psychological assessments<br>" +
                                    "📊 Track your mental health progress<br>" +
                                    "💬 Secure communication with doctors" +
                                    "</div></center></html>");
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        mainPanel.add(welcomeCard, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        // Enhanced footer with modern styling
        footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(ModernUIUtils.NEUTRAL_DARK);
        footerPanel.setBorder(new EmptyBorder(
            ModernUIUtils.SPACING_LG, ModernUIUtils.SPACING_XL, 
            ModernUIUtils.SPACING_LG, ModernUIUtils.SPACING_XL
        ));
        
        JLabel footerText = new JLabel("<html><center>" +
                                      "<span style='color: #B2BEC3;'>" +
                                      ModernUIUtils.ICON_PHONE + " Emergency: 911 | " +
                                      ModernUIUtils.ICON_EMAIL + " info@mindcarecenter.com | " +
                                      "🌐 www.mindcarecenter.com</span><br>" +
                                      "<span style='color: #636E72; font-size: 11px;'>" +
                                      "© 2025 MindCare Psychology Center. Licensed Mental Health Facility" +
                                      "</span></center></html>");
        footerText.setHorizontalAlignment(SwingConstants.CENTER);
        
        footerPanel.add(footerText, BorderLayout.CENTER);
    }
    
    private JButton createEnhancedPortalButton(String title, String description, Color backgroundColor) {
        JPanel buttonCard = ModernUIUtils.createCardPanel();
        buttonCard.setLayout(new BorderLayout());
        buttonCard.setPreferredSize(new Dimension(280, 160));
        buttonCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create button content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(
            ModernUIUtils.SPACING_LG, ModernUIUtils.SPACING_MD, 
            ModernUIUtils.SPACING_LG, ModernUIUtils.SPACING_MD
        ));
        
        // Title label
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(ModernUIUtils.FONT_HEADING_SMALL);
        titleLabel.setForeground(backgroundColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Description label
        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(ModernUIUtils.FONT_BODY_SMALL);
        descLabel.setForeground(ModernUIUtils.NEUTRAL_MEDIUM);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(ModernUIUtils.SPACING_MD));
        contentPanel.add(descLabel);
        
        buttonCard.add(contentPanel, BorderLayout.CENTER);
        
        // Create invisible button for click handling
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effects to the card
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonCard.setBackground(backgroundColor.brighter());
                titleLabel.setForeground(backgroundColor.darker());
                buttonCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(backgroundColor, 2),
                    new EmptyBorder(
                        ModernUIUtils.SPACING_LG-2, ModernUIUtils.SPACING_LG-2, 
                        ModernUIUtils.SPACING_LG-2, ModernUIUtils.SPACING_LG-2
                    )
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                buttonCard.setBackground(ModernUIUtils.BACKGROUND_CARD);
                titleLabel.setForeground(backgroundColor);
                buttonCard.setBorder(ModernUIUtils.createCardBorder());
            }
        });
        
        // Overlay the button on the card
        buttonCard.setLayout(new OverlayLayout(buttonCard));
        buttonCard.add(button);
        buttonCard.add(contentPanel);
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void addEventListeners() {
        patientButton.addActionListener(e -> openPatientLogin());
        doctorButton.addActionListener(e -> openDoctorLogin());
        adminButton.addActionListener(e -> openAdminLogin());
    }

    private void showLoading(String message) {
        if (loadingPanel != null) {
            remove(loadingPanel);
        }
        loadingPanel = ModernUIUtils.createLoadingIndicator(message);
        setGlassPane(loadingPanel);
        loadingPanel.setVisible(true);
    }
    
    private void hideLoading() {
        if (loadingPanel != null) {
            loadingPanel.setVisible(false);
        }
    }

    private void openPatientLogin() {
        showLoading("Opening Patient Portal...");
        
        // Simulate brief loading (in real app, this would be navigation preparation)
        Timer timer = new Timer(800, e -> {
            hideLoading();
            new LoginPage().setVisible(true);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void openDoctorLogin() {
        showLoading("Opening Doctor Portal...");
        
        Timer timer = new Timer(800, e -> {
            hideLoading();
            new LoginDrPage().setVisible(true);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void openAdminLogin() {
        showLoading("Opening Admin Portal...");
        
        Timer timer = new Timer(800, e -> {
            hideLoading();
            new LoginAdminPage().setVisible(true);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }
}
