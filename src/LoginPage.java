import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, subtitleLabel, emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPanel headerPanel, mainPanel, formPanel, buttonPanel;
    private JButton loginButton, registerButton, backButton;
    private JCheckBox rememberMeCheckBox;
    private JLabel passwordStrengthLabel;
    private JPanel loadingPanel;

    public LoginPage() {
        setTitle("Patient Portal - MindCare Psychology Center");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        
        initializeComponents();
        setupLayout();
        addEventListeners();
        setVisible(true);
    }
    
    private void initializeComponents() {
        // Header Panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_BLUE);
        headerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        JLabel logoLabel = new JLabel("👤", SwingConstants.LEFT);
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        logoLabel.setForeground(WHITE);
        
        titleLabel = new JLabel("Patient Portal Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(WHITE);
        
        subtitleLabel = new JLabel("Access your medical records and book appointments");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 235, 255));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY_BLUE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_GRAY);
        mainPanel.setBorder(new EmptyBorder(60, 80, 60, 80));
        
        // Form Panel
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(40, 40, 40, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("<html><h3>Welcome Back!</h3>" +
                                       "<p>Please enter your credentials to access your account</p></html>");
        welcomeLabel.setForeground(DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(welcomeLabel, gbc);
        
        // Email field
        emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setForeground(DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(emailLabel, gbc);
        
        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 12, 10, 12)
        ));
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(emailField, gbc);
        
        // Password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 12, 10, 12)
        ));
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);
        
        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(WHITE);
        
        loginButton = createStyledButton("LOGIN", ACCENT_GREEN, WHITE);
        loginButton.setPreferredSize(new Dimension(120, 45));
        
        registerButton = createStyledButton("REGISTER", SECONDARY_BLUE, WHITE);
        registerButton.setPreferredSize(new Dimension(120, 45));
        
        backButton = createStyledButton("← BACK", DARK_GRAY, WHITE);
        backButton.setPreferredSize(new Dimension(100, 45));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);
        
        // Info panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(LIGHT_GRAY);
        infoPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JLabel infoLabel = new JLabel("<html><center>" +
                                    "<p><strong>New Patient?</strong> Create an account to:</p>" +
                                    "<ul style='text-align: left;'>" +
                                    "<li>📅 Book appointments with specialists</li>" +
                                    "<li>📋 Take psychological assessments</li>" +
                                    "<li>📊 Track your mental health progress</li>" +
                                    "<li>💬 Secure communication with doctors</li>" +
                                    "</ul></center></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoLabel.setForeground(DARK_GRAY);
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        // Set login button as default
        getRootPane().setDefaultButton(loginButton);
        
        // Login button action
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ 
                try{
                    // Get connection to database
                    conn = DBconnection.getConnection();
                    
                    // Get email and password from text fields
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    
                    // Validate input
                    if(email.trim().isEmpty() || password.isEmpty()){
                        showErrorMessage("Please enter both email and password!");
                        return;
                    }
                    
                    // SQL query to get patient's hashed password and salt
                    String query = "SELECT email, password, password_salt FROM patient WHERE email = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, email);
                    ResultSet rs = pstmt.executeQuery();
                    
                    // If patient found, verify password
                    if(rs.next()){
                        String storedPassword = rs.getString("password");
                        String storedSalt = rs.getString("password_salt");
                        
                        // Handle legacy passwords (if salt is null, it's an old plain text password)
                        boolean loginSuccessful = false;
                        if(storedSalt == null || storedSalt.isEmpty()){
                            // Legacy plain text password comparison
                            loginSuccessful = password.equals(storedPassword);
                            
                            // If login successful with plain text, update to hashed password
                            if(loginSuccessful){
                                String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
                                String newHashedPassword = passwordData[0];
                                String newSalt = passwordData[1];
                                
                                String updateQuery = "UPDATE patient SET password = ?, password_salt = ? WHERE email = ?";
                                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                                updateStmt.setString(1, newHashedPassword);
                                updateStmt.setString(2, newSalt);
                                updateStmt.setString(3, email);
                                updateStmt.executeUpdate();
                                
                                System.out.println("Password upgraded to hashed version for user: " + email);
                            }
                        } else {
                            // Use proper password verification for hashed passwords
                            loginSuccessful = PasswordUtils.verifyPassword(password, storedPassword, storedSalt);
                        }
                        
                        if(loginSuccessful){
                            // Hide login page and go to assessment page
                            showSuccessMessage("Login successful! Welcome back.");
                            dispose();
                            new AssessmentPage().setVisible(true);
                        } else {
                            showErrorMessage("Invalid email or password. Please try again.");
                        }
                    }
                    else{
                        // Show error message if login failed
                        showErrorMessage("Invalid email or password. Please try again.");
                    }
                }
                catch(Exception ex){
                    System.out.println("Error: " + ex.getMessage());
                    showErrorMessage("Login failed: " + ex.getMessage());
                }
            }
        });

        // Register button action
        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Go to Registration Page
                dispose();
                new RegistrationPage().setVisible(true);
            }
        });
        
        // Back button action
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                new WelcomePage();
            }
        });
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}

