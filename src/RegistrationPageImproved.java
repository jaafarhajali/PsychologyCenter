import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegistrationPageImproved extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, logoLabel;
    private JLabel fullNameLabel, ageLabel, emailLabel, phoneLabel, passwordLabel, confirmPasswordLabel;
    private JTextField fullNameField, ageField, emailField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton createAccountButton, backToLoginButton;
    private JPanel mainPanel, formPanel, buttonPanel, headerPanel;
    private JScrollPane scrollPane;

    public RegistrationPageImproved() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        centerWindow();
    }

    private void initializeComponents() {
        setTitle("Create Account - Psychology Center");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        logoLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel = new JLabel("Create New Account", SwingConstants.CENTER);

        // Form components
        fullNameLabel = new JLabel("Full Name:");
        ageLabel = new JLabel("Age:");
        emailLabel = new JLabel("Email Address:");
        phoneLabel = new JLabel("Phone Number:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");

        fullNameField = new JTextField();
        ageField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Button components
        createAccountButton = new JButton("Create Account");
        backToLoginButton = new JButton("← Back to Login");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        formPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // Create scroll pane for main panel
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        getContentPane().add(scrollPane);

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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        addFormField(gbc, 0, fullNameLabel, fullNameField);
        addFormField(gbc, 1, ageLabel, ageField);
        addFormField(gbc, 2, emailLabel, emailField);
        addFormField(gbc, 3, phoneLabel, phoneField);
        addFormField(gbc, 4, passwordLabel, passwordField);
        addFormField(gbc, 5, confirmPasswordLabel, confirmPasswordField);

        // Button panel setup
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints btnGbc = new GridBagConstraints();

        btnGbc.gridx = 0; btnGbc.gridy = 0;
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.insets = new Insets(20, 0, 15, 0);
        buttonPanel.add(createAccountButton, btnGbc);

        btnGbc.gridy = 1;
        btnGbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(backToLoginButton, btnGbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(GridBagConstraints gbc, int row, JLabel label, JTextField field) {
        gbc.gridx = 0; gbc.gridy = row * 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        formPanel.add(label, gbc);

        gbc.gridy = row * 2 + 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        formPanel.add(field, gbc);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(46, 204, 113));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form styling
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(44, 62, 80);

        // Style all labels
        JLabel[] labels = {fullNameLabel, ageLabel, emailLabel, phoneLabel, passwordLabel, confirmPasswordLabel};
        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(labelColor);
        }

        // Style all text fields
        JTextField[] fields = {fullNameField, ageField, emailField, phoneField, passwordField, confirmPasswordField};
        for (JTextField field : fields) {
            styleTextField(field, fieldFont);
        }

        // Button styling
        Dimension buttonSize = new Dimension(350, 45);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        styleButton(createAccountButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(backToLoginButton, buttonSize, buttonFont, new Color(149, 165, 166), Color.WHITE);
    }

    private void styleTextField(JTextField field, Font font) {
        field.setFont(font);
        field.setPreferredSize(new Dimension(350, 40));
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
        getRootPane().setDefaultButton(createAccountButton);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new LoginPage().setVisible(true);
            }
        });
    }

    private void createAccount() {
        try {
            // Get connection to database
            conn = DBconnection.getConnection();
            
            // Get data from text fields
            String fullname = fullNameField.getText();
            String age = ageField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            String confirmpassword = new String(confirmPasswordField.getPassword());
            
            // Validate input fields
            if(fullname.trim().isEmpty() || age.trim().isEmpty() || email.trim().isEmpty() || 
               phone.trim().isEmpty() || password.isEmpty()) {
                showErrorMessage("Please fill all fields!");
                return;
            }
            
            // Validate email format
            if(!email.contains("@") || !email.contains(".")) {
                showErrorMessage("Please enter a valid email address!");
                return;
            }
            
            // Validate age
            try {
                int ageValue = Integer.parseInt(age);
                if (ageValue < 1 || ageValue > 120) {
                    showErrorMessage("Please enter a valid age (1-120)!");
                    return;
                }
            } catch (NumberFormatException e) {
                showErrorMessage("Please enter a valid age number!");
                return;
            }
            
            // Check if passwords match
            if(!password.equals(confirmpassword)) {
                showErrorMessage("Passwords do not match!");
                return;
            }
            
            // Validate password strength
            if(password.length() < 6) {
                showErrorMessage("Password must be at least 6 characters long!");
                return;
            }
            
            // Check if email already exists
            String checkEmailQuery = "SELECT email FROM patient WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery);
            checkStmt.setString(1, email);
            ResultSet emailResult = checkStmt.executeQuery();
            
            if(emailResult.next()) {
                showErrorMessage("Email already exists! Please use a different email.");
                return;
            }
            
            // Generate salt and hash password
            String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
            String hashedPassword = passwordData[0];
            String salt = passwordData[1];
            
            // SQL query to insert new patient with hashed password and salt
            String query = "INSERT INTO patient (full_name, age, email, phone, password, password_salt) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fullname);
            pstmt.setString(2, age);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, hashedPassword);
            pstmt.setString(6, salt);
            
            int result = pstmt.executeUpdate();
            
            // If registration successful
            if(result > 0) {
                showSuccessMessage("Account created successfully!");
                // Clear form
                clearForm();
                // Go back to login page
                setVisible(false);
                new LoginPage().setVisible(true);
            } else {
                showErrorMessage("Registration failed!");
            }
        } catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Registration failed: " + ex.getMessage());
        }
    }

    private void clearForm() {
        fullNameField.setText("");
        ageField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistrationPageImproved().setVisible(true);
        });
    }
}
