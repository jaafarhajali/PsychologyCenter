import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class WelcomePage extends JFrame {
    private JLabel welcomeLabel, subtitleLabel, mottoLabel, logoTextLabel;
    private JButton patientButton, doctorButton, adminButton;
    private JPanel mainPanel, headerPanel, buttonPanel, footerPanel;
    private Timer fadeInTimer;

    public WelcomePage() {
        // Set full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // Keep window decorations for better UX
        
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        setupAnimations();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Psychology Center - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header components with enhanced text
        logoTextLabel = new JLabel("PSYCHOLOGY CENTER", SwingConstants.CENTER);
        welcomeLabel = new JLabel("Professional Mental Health Services", SwingConstants.CENTER);
        subtitleLabel = new JLabel("Comprehensive Care for Your Mental Wellness", SwingConstants.CENTER);
        mottoLabel = new JLabel("\"Empowering minds, transforming lives, one step at a time\"", SwingConstants.CENTER);

        // Button components with descriptive text
        patientButton = new JButton("<html><center><b>PATIENT PORTAL</b><br><span style='font-size:11px'>Book Appointments • Take Assessments • View Records</span></center></html>");
        doctorButton = new JButton("<html><center><b>DOCTOR PORTAL</b><br><span style='font-size:11px'>Manage Patients • View Schedules • Clinical Tools</span></center></html>");
        adminButton = new JButton("<html><center><b>ADMIN PORTAL</b><br><span style='font-size:11px'>System Management • User Control • Reports</span></center></html>");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        buttonPanel = new JPanel();
        footerPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup with enhanced gradient background
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Beautiful multi-stop gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 248, 255),           // Alice Blue
                    0, getHeight(), new Color(176, 196, 222)   // Light Steel Blue
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle decorative elements
                g2d.setColor(new Color(255, 255, 255, 30));
                for (int i = 0; i < 5; i++) {
                    int size = 100 + i * 50;
                    g2d.fillOval(getWidth() - size/2, i * 150, size, size);
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
        getContentPane().add(mainPanel);

        // Header panel setup with optimized spacing
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(Box.createVerticalStrut(20)); // Reduced from 30
        headerPanel.add(logoTextLabel);
        headerPanel.add(Box.createVerticalStrut(15)); // Reduced from 20
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createVerticalStrut(8));  // Reduced from 10
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(10)); // Reduced from 15
        headerPanel.add(mottoLabel);
        headerPanel.add(Box.createVerticalStrut(30)); // Reduced from 50

        // Button panel setup with optimized layout
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 15, 0); // Reduced from 20 to 15
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttonPanel.add(patientButton, gbc);
        
        gbc.gridy = 1;
        buttonPanel.add(doctorButton, gbc);
        
        gbc.gridy = 2;
        buttonPanel.add(adminButton, gbc);

        // Footer panel setup
        footerPanel.setLayout(new FlowLayout());
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("© 2025 Psychology Center - Confidential & Professional Services");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(footerLabel);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Logo text styling - large and impressive
        logoTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        logoTextLabel.setForeground(new Color(25, 25, 112)); // Midnight Blue
        logoTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Header styling with beautiful typography
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        welcomeLabel.setForeground(new Color(70, 130, 180)); // Steel Blue
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(100, 149, 237)); // Cornflower Blue
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mottoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        mottoLabel.setForeground(new Color(128, 128, 128)); // Gray
        mottoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Enhanced button styling with premium look
        styleEnhancedButton(patientButton, new Color(52, 152, 219), Color.WHITE); // Modern Blue
        styleEnhancedButton(doctorButton, new Color(46, 204, 113), Color.WHITE);  // Modern Green
        styleEnhancedButton(adminButton, new Color(155, 89, 182), Color.WHITE);   // Modern Purple
    }

    private void styleEnhancedButton(JButton button, Color bgColor, Color textColor) {
        button.setPreferredSize(new Dimension(400, 70)); // Reduced height from 80 to 70
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Rounded corners effect with shadow
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 3),
            BorderFactory.createEmptyBorder(18, 30, 18, 30) // Reduced padding
        ));
        
        // Enhanced hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColor.brighter().brighter(), 4),
                    BorderFactory.createEmptyBorder(17, 29, 17, 29) // Adjusted for reduced height
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(bgColor.darker(), 3),
                    BorderFactory.createEmptyBorder(18, 30, 18, 30) // Adjusted for reduced height
                ));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
        });
    }

    private void setupAnimations() {
        // Create smooth fade-in animation
        final Component[] components = {logoTextLabel, welcomeLabel, subtitleLabel, mottoLabel, 
                                       patientButton, doctorButton, adminButton};
        
        // Initially hide all components
        for (Component comp : components) {
            comp.setVisible(false);
        }
        
        fadeInTimer = new Timer(200, new ActionListener() {
            private int currentComponent = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentComponent < components.length) {
                    components[currentComponent].setVisible(true);
                    currentComponent++;
                } else {
                    fadeInTimer.stop();
                }
            }
        });
        fadeInTimer.start();
    }

    private void addEventListeners() {
        patientButton.addActionListener(e -> openPatientLogin());
        doctorButton.addActionListener(e -> openDoctorLogin());
        adminButton.addActionListener(e -> openAdminLogin());
    }

    private void openPatientLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        dispose();
    }

    private void openDoctorLogin() {
        LoginDrPage doctorPage = new LoginDrPage();
        doctorPage.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        doctorPage.setVisible(true);
        dispose();
    }

    private void openAdminLogin() {
        LoginAdminPage adminPage = new LoginAdminPage();
        adminPage.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        adminPage.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }
}
