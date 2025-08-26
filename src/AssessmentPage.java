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
        centerWindow();
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
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header styling
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(52, 152, 219));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionLabel.setForeground(new Color(70, 130, 180));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        Dimension buttonSize = new Dimension(350, 50);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

        styleButton(knowConditionButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        styleButton(needAssessmentButton, buttonSize, buttonFont, new Color(52, 152, 219), Color.WHITE);
        styleButton(backButton, new Dimension(120, 40), new Font("Segoe UI", Font.BOLD, 14), 
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

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AssessmentPage();
        });
    }
}
