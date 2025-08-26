import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, emailLabel, passwordLabel, logoLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, backButton;
    private JPanel mainPanel, formPanel, buttonPanel, headerPanel;

    public LoginPage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        UIStyleManager.applyWindowConstraints(this, new Dimension(450, 600));
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Patient Login - Psychology Center");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        logoLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel = new JLabel("Patient Login", SwingConstants.CENTER);

        // Form components
        emailLabel = new JLabel("Email Address:");
        passwordLabel = new JLabel("Password:");
        emailField = new JTextField();
        passwordField = new JPasswordField();

        // Button components
        loginButton = new JButton("Sign In");
        registerButton = new JButton("Register New Account");
        backButton = new JButton("← Back to Home");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        formPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Form panel setup
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(emailField, gbc);
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);

        // Button panel setup
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(backButton);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);

        // Header styling
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UIStyleManager.styleLabel(titleLabel, UIStyleManager.Fonts.TITLE_MEDIUM, UIStyleManager.Colors.PRIMARY_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form styling
        UIStyleManager.styleLabel(emailLabel, UIStyleManager.Fonts.LABEL_FONT, UIStyleManager.Colors.TEXT_PRIMARY);
        UIStyleManager.styleLabel(passwordLabel, UIStyleManager.Fonts.LABEL_FONT, UIStyleManager.Colors.TEXT_PRIMARY);

        UIStyleManager.styleTextField(emailField, UIStyleManager.Dimensions.FIELD_STANDARD);
        UIStyleManager.styleTextField(passwordField, UIStyleManager.Dimensions.FIELD_STANDARD);

        // Button styling
        UIStyleManager.styleButton(loginButton, UIStyleManager.Colors.PRIMARY_BLUE, Color.WHITE, UIStyleManager.Dimensions.BUTTON_LARGE);
        UIStyleManager.styleButton(registerButton, UIStyleManager.Colors.PRIMARY_GREEN, Color.WHITE, UIStyleManager.Dimensions.BUTTON_LARGE);
        UIStyleManager.styleButton(backButton, UIStyleManager.Colors.TEXT_LIGHT, Color.WHITE, UIStyleManager.Dimensions.BUTTON_LARGE);
        
        // Align buttons
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void addEventListeners() {
        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new RegistrationPageImproved();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new WelcomePage();
            }
        });
    }

    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Basic validation
        if (email.isEmpty()) {
            UIStyleManager.showErrorMessage(this, "Please enter your email address.", "Input Required");
            return;
        }
        
        if (password.isEmpty()) {
            UIStyleManager.showErrorMessage(this, "Please enter your password.", "Input Required");
            return;
        }
        
        try {
            conn = DBconnection.getConnection();
            String query = "SELECT email, password, password_salt FROM patient WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String storedSalt = rs.getString("password_salt");
                
                // Handle legacy passwords
                boolean loginSuccessful = false;
                if (storedSalt == null || storedSalt.isEmpty()) {
                    loginSuccessful = password.equals(storedPassword);
                    
                    if (loginSuccessful) {
                        // Upgrade to hashed password
                        String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
                        String newHashedPassword = passwordData[0];
                        String newSalt = passwordData[1];
                        
                        String updateQuery = "UPDATE patient SET password = ?, password_salt = ? WHERE email = ?";
                        PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
                        updatePstmt.setString(1, newHashedPassword);
                        updatePstmt.setString(2, newSalt);
                        updatePstmt.setString(3, email);
                        updatePstmt.executeUpdate();
                        updatePstmt.close();
                    }
                } else {
                    // Verify hashed password
                    loginSuccessful = PasswordUtils.verifyPassword(password, storedPassword, storedSalt);
                }
                
                if (loginSuccessful) {
                    UIStyleManager.showSuccessMessage(this, "Login successful! Welcome back.", "Success");
                    setVisible(false);
                    new AssessmentPage().setVisible(true);
                } else {
                    UIStyleManager.showErrorMessage(this, "Invalid password. Please try again.", "Authentication Failed");
                }
            } else {
                UIStyleManager.showErrorMessage(this, "No account found with this email address.", "Account Not Found");
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            UIStyleManager.showErrorMessage(this, "Login failed: " + ex.getMessage(), "Error");
        }
    }
}
