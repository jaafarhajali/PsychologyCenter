import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginAdminPageImproved extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, emailLabel, passwordLabel, adminIconLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;
    private JPanel mainPanel, formPanel, buttonPanel, headerPanel;

    public LoginAdminPageImproved() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        centerWindow();
    }

    private void initializeComponents() {
        setTitle("Admin Login - Psychology Center");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        adminIconLabel = new JLabel("⚙️", SwingConstants.CENTER);
        titleLabel = new JLabel("Admin Portal", SwingConstants.CENTER);

        // Form components
        emailLabel = new JLabel("Email Address:");
        passwordLabel = new JLabel("Password:");
        emailField = new JTextField();
        passwordField = new JPasswordField();

        // Button components
        loginButton = new JButton("Sign In");
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
        headerPanel.add(adminIconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Form panel setup
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 5, 0);
        formPanel.add(emailLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(emailField, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 5, 0);
        formPanel.add(passwordLabel, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 30, 0);
        formPanel.add(passwordField, gbc);

        // Button panel setup
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints btnGbc = new GridBagConstraints();

        btnGbc.gridx = 0; btnGbc.gridy = 0;
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.insets = new Insets(0, 0, 15, 0);
        buttonPanel.add(loginButton, btnGbc);

        btnGbc.gridy = 1;
        btnGbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(backButton, btnGbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        adminIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        adminIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(155, 89, 182));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form styling
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(new Color(44, 62, 80));
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(new Color(44, 62, 80));

        styleTextField(emailField, fieldFont);
        styleTextField(passwordField, fieldFont);

        // Button styling
        Dimension buttonSize = new Dimension(300, 45);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        styleButton(loginButton, buttonSize, buttonFont, new Color(155, 89, 182), Color.WHITE);
        styleButton(backButton, buttonSize, buttonFont, new Color(149, 165, 166), Color.WHITE);
    }

    private void styleTextField(JTextField field, Font font) {
        field.setFont(font);
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
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

    private void addEventListeners() {
        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
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
        try {
            // Get connection to database
            conn = DBconnection.getConnection();
            
            // Get email and password from text fields
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            
            // Validate input
            if(email.trim().isEmpty() || password.isEmpty()) {
                showErrorMessage("Please enter both email and password!");
                return;
            }
            
            // SQL query to get admin's hashed password and salt
            String query = "SELECT email, password, password_salt FROM admin WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            // If admin found, verify password
            if(rs.next()) {
                String storedPassword = rs.getString("password");
                String storedSalt = rs.getString("password_salt");
                
                // Handle legacy passwords (if salt is null, it's an old plain text password)
                boolean loginSuccessful = false;
                if(storedSalt == null || storedSalt.isEmpty()) {
                    // Legacy plain text password comparison
                    loginSuccessful = password.equals(storedPassword);
                    
                    // If login successful with plain text, update to hashed password
                    if(loginSuccessful) {
                        String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
                        String newHashedPassword = passwordData[0];
                        String newSalt = passwordData[1];
                        
                        String updateQuery = "UPDATE admin SET password = ?, password_salt = ? WHERE email = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                        updateStmt.setString(1, newHashedPassword);
                        updateStmt.setString(2, newSalt);
                        updateStmt.setString(3, email);
                        updateStmt.executeUpdate();
                        
                        System.out.println("Password upgraded to hashed version for admin: " + email);
                    }
                } else {
                    // Use proper password verification for hashed passwords
                    loginSuccessful = PasswordUtils.verifyPassword(password, storedPassword, storedSalt);
                }
                
                if(loginSuccessful) {
                    // Hide login page and go to admin selection page
                    setVisible(false);
                    new AdminSelectionPageImproved().setVisible(true);
                } else {
                    showErrorMessage("Invalid email or password!");
                }
            } else {
                // Show error message if login failed
                showErrorMessage("Invalid email or password!");
            }
        } catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Login failed: " + ex.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginAdminPageImproved().setVisible(true);
        });
    }
}
