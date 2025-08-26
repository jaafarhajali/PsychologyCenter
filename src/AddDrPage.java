import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AddDrPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, fullNameLabel, emailLabel, phoneLabel, addressLabel, 
                   specialityLabel, feeLabel, passwordLabel, iconLabel;
    private JTextField fullNameField, emailField, phoneField, addressField, feeField;
    private JComboBox<String> specialityDropdown;
    private JPasswordField passwordField;
    private JPanel mainPanel, headerPanel, formPanel, buttonPanel;
    private JButton saveButton, backButton, logoutButton;
    private JScrollPane scrollPane;

    public AddDrPage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        centerWindow();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Add New Doctor - Psychology Center");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        iconLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel = new JLabel("Add New Doctor", SwingConstants.CENTER);

        // Form components
        fullNameLabel = new JLabel("Full Name:");
        emailLabel = new JLabel("Email Address:");
        phoneLabel = new JLabel("Phone Number:");
        addressLabel = new JLabel("Address:");
        specialityLabel = new JLabel("Speciality:");
        feeLabel = new JLabel("Consultation Fee ($):");
        passwordLabel = new JLabel("Password:");

        fullNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        feeField = new JTextField();
        passwordField = new JPasswordField();

        String[] specialities = {
            "Depression", "ADHD", "Anxiety", "Bipolar Disorder", 
            "OCD", "Schizophrenia", "Narcissistic Personality Disorder", "Normal"
        };
        specialityDropdown = new JComboBox<>(specialities);

        // Button components
        saveButton = new JButton("Save Doctor");
        backButton = new JButton("← Back");
        logoutButton = new JButton("Logout");

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
        headerPanel.add(iconLabel);
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
        addFormField(gbc, 1, emailLabel, emailField);
        addFormField(gbc, 2, phoneLabel, phoneField);
        addFormField(gbc, 3, addressLabel, addressField);
        addFormField(gbc, 4, specialityLabel, specialityDropdown);
        addFormField(gbc, 5, feeLabel, feeField);
        addFormField(gbc, 6, passwordLabel, passwordField);

        // Button panel setup
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(logoutButton);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(GridBagConstraints gbc, int row, JLabel label, JComponent field) {
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
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(46, 204, 113));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form styling
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(44, 62, 80);

        // Style all labels
        JLabel[] labels = {fullNameLabel, emailLabel, phoneLabel, addressLabel, 
                          specialityLabel, feeLabel, passwordLabel};
        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(labelColor);
        }

        // Style all text fields
        JTextField[] fields = {fullNameField, emailField, phoneField, addressField, feeField, passwordField};
        for (JTextField field : fields) {
            styleTextField(field, fieldFont);
        }

        // Dropdown styling
        specialityDropdown.setFont(fieldFont);
        specialityDropdown.setPreferredSize(new Dimension(400, 40));
        specialityDropdown.setBackground(Color.WHITE);
        specialityDropdown.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Button styling
        Dimension primaryButtonSize = new Dimension(150, 45);
        Dimension secondaryButtonSize = new Dimension(100, 40);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font smallButtonFont = new Font("Segoe UI", Font.BOLD, 12);

        styleButton(saveButton, primaryButtonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(backButton, secondaryButtonSize, smallButtonFont, new Color(149, 165, 166), Color.WHITE);
        styleButton(logoutButton, secondaryButtonSize, smallButtonFont, new Color(231, 76, 60), Color.WHITE);
    }

    private void styleTextField(JTextField field, Font font) {
        field.setFont(font);
        field.setPreferredSize(new Dimension(400, 40));
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
        getRootPane().setDefaultButton(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveDoctorToDatabase();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AdminDashboard();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(AddDrPage.this, 
                    "Are you sure you want to logout?", 
                    "Confirm Logout", 
                    JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    new WelcomePage();
                }
            }
        });
    }

    private void saveDoctorToDatabase() {
        try {
            conn = DBconnection.getConnection();
            String fullname = fullNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String speciality = (String) specialityDropdown.getSelectedItem();
            String fee = feeField.getText();
            String password = new String(passwordField.getPassword());
            
            // Validate input fields
            if(fullname.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || 
               address.trim().isEmpty() || fee.trim().isEmpty() || password.isEmpty()) {
                showErrorMessage("Please fill all fields!");
                return;
            }
            
            // Validate email format
            if(!email.contains("@") || !email.contains(".")) {
                showErrorMessage("Please enter a valid email address!");
                return;
            }
            
            // Validate password strength
            if(password.length() < 6) {
                showErrorMessage("Password must be at least 6 characters long!");
                return;
            }
            
            // Check if email already exists
            String checkEmailQuery = "SELECT email FROM doctor WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery);
            checkStmt.setString(1, email);
            ResultSet emailResult = checkStmt.executeQuery();
            
            if(emailResult.next()) {
                showErrorMessage("Email already exists! Please use a different email.");
                return;
            }
            
            // Hash the password
            String[] passwordData = PasswordUtils.hashPasswordWithNewSalt(password);
            String hashedPassword = passwordData[0];
            String salt = passwordData[1];
            
            // SQL query to insert new doctor with hashed password
            String query = "INSERT INTO doctor (full_name,email,phone,address,speciality,consultation_fees,password,password_salt) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fullname);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, speciality);
            pstmt.setString(6, fee);
            pstmt.setString(7, hashedPassword);
            pstmt.setString(8, salt);
            
            int result = pstmt.executeUpdate();
            if(result > 0) {
                showSuccessMessage("Doctor added successfully!");
                clearForm();
            } else {
                showErrorMessage("Failed to add doctor!");
            }
        } catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Error: " + ex.getMessage());
        }
    }

    private void clearForm() {
        fullNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        specialityDropdown.setSelectedIndex(0);
        feeField.setText("");
        passwordField.setText("");
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
            new AddDrPage();
        });
    }
}
