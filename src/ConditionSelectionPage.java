import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ConditionSelectionPage extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, selectLabel, iconLabel, instructionLabel;
    private JComboBox<String> conditionDropdown;
    private JPanel mainPanel, headerPanel, formPanel, buttonPanel;
    private JButton findDoctorsButton, backButton;

    public ConditionSelectionPage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        centerWindow();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Condition Selection - Psychology Center");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        iconLabel = new JLabel("📋", SwingConstants.CENTER);
        titleLabel = new JLabel("Condition Selection", SwingConstants.CENTER);
        instructionLabel = new JLabel("Please select your condition from the list below:", SwingConstants.CENTER);

        // Form components
        selectLabel = new JLabel("Select your condition:");
        String[] conditions = {
            "Select a condition...",
            "Depression",
            "Anxiety", 
            "ADHD",
            "Bipolar Disorder",
            "OCD",
            "Schizophrenia",
            "Narcissistic Personality Disorder",
            "Normal"
        };
        conditionDropdown = new JComboBox<>(conditions);

        // Button components
        findDoctorsButton = new JButton("🔍 Find Doctors");
        backButton = new JButton("← Back");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        formPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(instructionLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Form panel setup
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 8, 0);
        formPanel.add(selectLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 30, 0);
        formPanel.add(conditionDropdown, gbc);

        // Button panel setup
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints btnGbc = new GridBagConstraints();

        btnGbc.gridx = 0; btnGbc.gridy = 0;
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.insets = new Insets(0, 0, 15, 0);
        buttonPanel.add(findDoctorsButton, btnGbc);

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
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(100, 100, 100));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form styling
        selectLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectLabel.setForeground(new Color(44, 62, 80));

        // Dropdown styling
        conditionDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        conditionDropdown.setPreferredSize(new Dimension(400, 40));
        conditionDropdown.setBackground(Color.WHITE);
        conditionDropdown.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Button styling
        Dimension buttonSize = new Dimension(300, 45);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        styleButton(findDoctorsButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(backButton, new Dimension(120, 40), new Font("Segoe UI", Font.BOLD, 12), 
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

    private void addEventListeners() {
        getRootPane().setDefaultButton(findDoctorsButton);

        findDoctorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performConditionSelection();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AssessmentPage();
            }
        });
    }

    private void performConditionSelection() {
        // Get selected condition
        String selectedCondition = (String) conditionDropdown.getSelectedItem();
        
        // Check if user selected a condition
        if(selectedCondition.equals("Select a condition...")) {
            showErrorMessage("Please select a condition!");
            return;
        }
        
        try {
            // Get connection to database
            conn = DBconnection.getConnection();
            
            // Update the latest registered patient's condition_name
            String getPatientIdQuery = "select patient_id from patient order by patient_id desc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getPatientIdQuery);
            int patientId = -1;
            if(rs.next()) {
                patientId = rs.getInt("patient_id");
            }
            if(patientId != -1) {
                String updateQuery = "update patient set condition_name='" + selectedCondition + "' where patient_id=" + patientId;
                int result = st.executeUpdate(updateQuery);
                if(result > 0) {
                    setVisible(false);
                    new DoctorsListPage(selectedCondition, "condition").setVisible(true);
                } else {
                    showErrorMessage("Failed to save condition!");
                }
            } else {
                showErrorMessage("No patient found to update condition!");
            }
        } catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Database error: " + ex.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Selection Error", JOptionPane.ERROR_MESSAGE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConditionSelectionPage();
        });
    }
}
