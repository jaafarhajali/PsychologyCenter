import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AssessmentPage extends JFrame {
    private JLabel titleLabel, questionLabel, iconLabel;
    private JPanel mainPanel, headerPanel, buttonPanel;
    private JButton knowConditionButton, needAssessmentButton, backButton;

    public AssessmentPage() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        UIStyleManager.applyWindowConstraints(this, new Dimension(650, 500));
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Assessment Choice - Psychology Center");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        iconLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel = new JLabel("Assessment Choice", SwingConstants.CENTER);
        questionLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Do you already know the condition (diagnosis)<br>" +
            "or would you like to answer a short assessment<br>" +
            "to help us suggest the right doctor for you?" +
            "</div></html>", SwingConstants.CENTER);

        // Button components
        knowConditionButton = new JButton("I already know the condition");
        needAssessmentButton = new JButton("I need an assessment");
        backButton = new JButton("← Back");

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
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        headerPanel.add(questionLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Button panel setup
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 15, 0);
        buttonPanel.add(knowConditionButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(needAssessmentButton, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 0, 0);
        buttonPanel.add(backButton, gbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);

        // Header styling
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UIStyleManager.styleLabel(titleLabel, UIStyleManager.Fonts.TITLE_LARGE, UIStyleManager.Colors.PRIMARY_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        UIStyleManager.styleLabel(questionLabel, UIStyleManager.Fonts.SUBTITLE, UIStyleManager.Colors.TEXT_SECONDARY);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        UIStyleManager.styleButton(knowConditionButton, UIStyleManager.Colors.PRIMARY_GREEN, Color.WHITE, new Dimension(350, 50));
        UIStyleManager.styleButton(needAssessmentButton, UIStyleManager.Colors.PRIMARY_BLUE, Color.WHITE, new Dimension(350, 50));
        UIStyleManager.styleButton(backButton, UIStyleManager.Colors.TEXT_LIGHT, Color.WHITE, new Dimension(120, 40));
    }

    private void addEventListeners() {
        knowConditionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Go to condition selection page
                setVisible(false);
                new ConditionSelectionPage().setVisible(true);
            }
        });

        needAssessmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Go to psychological assessment page
                setVisible(false);
                new PsychologicalAssessment().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Go back to welcome page
                setVisible(false);
                new WelcomePage();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AssessmentPage();
        });
    }
}
