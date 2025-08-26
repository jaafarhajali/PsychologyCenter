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
        // Set full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Condition Selection - Psychology Center");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        iconLabel = new JLabel("", SwingConstants.CENTER);
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
        findDoctorsButton = new JButton("Find Doctors");
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
        mainPanel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);

        // Header styling
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UIStyleManager.styleLabel(titleLabel, UIStyleManager.Fonts.TITLE_MEDIUM, UIStyleManager.Colors.PRIMARY_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UIStyleManager.styleLabel(instructionLabel, UIStyleManager.Fonts.BODY_LARGE, UIStyleManager.Colors.TEXT_SECONDARY);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form styling
        UIStyleManager.styleLabel(selectLabel, UIStyleManager.Fonts.LABEL_FONT, UIStyleManager.Colors.TEXT_PRIMARY);

        // Dropdown styling
        conditionDropdown.setFont(UIStyleManager.Fonts.BODY_LARGE);
        conditionDropdown.setPreferredSize(new Dimension(400, 40));
        conditionDropdown.setBackground(Color.WHITE);
        conditionDropdown.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyleManager.Colors.BORDER_MEDIUM, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Button styling
        UIStyleManager.styleButton(findDoctorsButton, UIStyleManager.Colors.PRIMARY_GREEN, Color.WHITE, new Dimension(300, 45));
        UIStyleManager.styleButton(backButton, UIStyleManager.Colors.TEXT_LIGHT, Color.WHITE, new Dimension(120, 40));
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
        UIStyleManager.showErrorMessage(this, message, "Selection Error");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConditionSelectionPage();
        });
    }
}
