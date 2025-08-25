import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class PsychologicalAssessment extends JFrame {
    private Connection conn = null;
    private JLabel titleLabel, instructionLabel, iconLabel, progressLabel;
    private JPanel mainPanel, headerPanel, questionsPanel, buttonPanel;
    private JScrollPane scrollPane;
    private JButton submitButton, backButton;
    private JProgressBar progressBar;

    // Arrays to store question labels and radio button groups
    private JLabel[] questionLabels;
    private ButtonGroup[] buttonGroups;
    private JRadioButton[][] radioButtons;

    // Questions and options
    private String[] questions = {
        "1. How do you usually spend your free time?",
        "2. How is your sleep usually?",
        "3. How do you react when facing a problem?",
        "4. How would you describe your energy level during the day?",
        "5. How do you react to criticism?",
        "6. Do you find it difficult to sit still for long periods?",
        "7. How would you describe your relationships with others?",
        "8. How do you deal with painful or traumatic memories?",
        "9. Do you hear voices or see things others don't?",
        "10. How do you organize your time and daily tasks?",
        "11. How would you describe your feelings most of the time?",
        "12. Do you feel a strong need to perform rituals (like washing hands, checking doors)?",
        "13. How do you feel in social events or large gatherings?",
        "14. Do you make impulsive or sudden decisions (like buying expensive things, traveling without planning)?",
        "15. How do you behave when you feel sad?",
        "16. How would you describe your ability to concentrate?",
        "17. Do you sometimes feel your thoughts are disorganized or scattered?",
        "18. Do you believe you are more special or better than others?",
        "19. How would you describe your appetite?",
        "20. Do you sometimes feel your life lacks purpose or meaning?"
    };

    private String[][] options = {
        {"I often feel tired and want to sleep or rest", "I feel restless and want to move or do many things", "I worry a lot even when relaxing", "My mood swings between very high energy and low mood", "I repeat certain actions or rituals frequently", "I sometimes see or hear things others don't", "I like being the center of attention and expect praise", "I enjoy my free time normally without any problems"},
        {"I sleep a lot but still feel tired", "I have trouble sitting still and often feel energetic", "I have difficulty sleeping because of worries", "I have periods of very little sleep with high energy", "I feel compelled to check things before sleeping", "I sometimes hear voices at night", "I think I deserve special treatment even in my sleep", "My sleep is normal and restful"},
        {"I feel hopeless and avoid thinking about it", "I act impulsively without planning", "I feel anxious and overthink the problem", "I switch between feeling very confident and very low", "I perform rituals to feel in control", "I believe others are controlling or watching me", "I blame others and refuse to accept fault", "I stay calm and look for reasonable solutions"},
        {"Very low and I feel tired most of the time", "Very high without any clear reason", "Fluctuates between high and low", "Normal and stable", "I feel restless and have repetitive urges", "Sometimes I feel disconnected from reality", "I feel superior to others often", "My energy level is normal and steady"},
        {"I feel very sad or discouraged", "I get distracted easily and lose focus", "I feel nervous and think about it a lot", "I either get very angry or very sad quickly", "I repeat checking or correcting things after criticism", "I think others are talking about me behind my back", "I get angry and defend myself strongly", "I accept criticism and try to improve"},
        {"I feel very tired and want to rest", "Yes, I move or talk a lot without control", "I feel anxious and restless sometimes", "My activity level changes a lot during the day", "I feel compelled to do rituals repeatedly", "I sometimes feel confused or disconnected", "I get impatient and want to be noticed", "I can sit still and relax easily"},
        {"I avoid socializing and prefer to be alone", "I have trouble focusing in conversations", "I worry about what others think of me", "My mood affects how I interact with people", "I feel a need to repeat social behaviors to feel safe", "I feel suspicious that people are against me", "I like to be admired and praised", "I maintain healthy and balanced relationships"},
        {"I feel sad and avoid thinking about them", "I get restless and distracted", "These memories cause me anxiety and fear", "My emotions around these memories vary greatly", "I try to control my thoughts by rituals", "I sometimes experience hallucinations related to memories", "I focus on myself and expect others to support me", "I accept and cope with these memories normally"},
        {"No, I do not experience this", "Sometimes when I'm very stressed", "Rarely or never", "Frequently and clearly", "Only in dreams or imagination", "When feeling anxious or fearful", "I am aware but do not let it affect me", "Not at all"},
        {"I often feel too tired to do much", "I find it hard to focus and stay organized", "I get worried about finishing on time", "My productivity varies a lot", "I have rituals to organize my tasks", "I sometimes lose track of time or reality", "I expect others to help me or notice me", "I manage my time and tasks well"},
        {"Sad or empty", "Tense and anxious", "Fluctuating between sadness and high energy", "Stable and normal", "Repetitive thoughts that bother me", "Detached from reality at times", "Confident and expect admiration", "Balanced and calm"},
        {"Yes, often and feel pressured if I don't do them", "Sometimes, only in certain situations", "No, I don't feel this need", "I feel restless if I can't do them", "I repeat actions to feel safe", "I worry excessively about contamination", "I ignore such urges", "I don't have these urges at all"},
        {"Very anxious and avoid attending", "Restless and easily distracted in groups", "Worried about being judged", "My mood affects how I socialize", "I repeat social behaviors to feel secure", "I feel suspicious about others' intentions", "I enjoy being the center of attention", "I participate comfortably and enjoy myself"},
        {"I avoid decisions and feel helpless", "I make impulsive choices often", "I worry a lot before deciding", "My decisions vary with mood", "I feel compelled to repeat checking before deciding", "I am suspicious of others' advice", "I expect special treatment in decisions", "I make thoughtful and planned decisions"},
        {"I withdraw and isolate myself", "I become restless or irritable", "I feel anxious and fearful", "My emotions shift rapidly", "I perform rituals to calm myself", "I have strange or disturbing thoughts", "I expect others to comfort me", "I seek support and try to solve problems"},
        {"Very weak and easily distracted", "Often restless and unfocused", "Anxious and worried thoughts interfere", "Concentration varies with mood", "I focus excessively on details", "I have difficulty organizing thoughts", "I concentrate well when interested", "I focus easily and maintain attention"},
        {"Often and it bothers me", "Sometimes, especially when restless", "Frequently anxious thoughts", "Mood affects clarity of thoughts", "Repetitive or intrusive thoughts occur", "I experience hallucinations or delusions", "I feel my thoughts are clear and confident", "My thoughts are generally clear and organized"},
        {"No, I often feel inadequate", "Sometimes I feel restless about this", "I worry about being judged", "My mood influences self-esteem", "I repeat behaviors to feel worthy", "I distrust others' opinions", "Yes, often expect special treatment", "No, I feel equal to others"},
        {"Very low and little interest in food", "High and often impulsive eating", "Anxious about eating habits", "Appetite varies with mood", "I eat in rituals or patterns", "I sometimes forget to eat", "I expect others to cater to my preferences", "My appetite is normal and stable"},
        {"Yes, frequently", "Sometimes, and I feel restless", "I worry about my future", "My feelings about this change often", "I perform rituals to feel control", "I feel disconnected from reality", "I feel unique and expect admiration", "No, I have a clear sense of purpose"}
    };

    private String[] conditionNames = {
        "Depression", "ADHD", "Anxiety", "Bipolar Disorder", "OCD", "Schizophrenia", "Narcissistic Personality Disorder", "Normal"
    };

    public PsychologicalAssessment() {
        initializeComponents();
        setupLayout();
        styleComponents();
        createQuestions();
        addEventListeners();
        centerWindow();
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Psychological Assessment - Psychology Center");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        // Header components
        iconLabel = new JLabel("📝", SwingConstants.CENTER);
        titleLabel = new JLabel("Psychological Assessment", SwingConstants.CENTER);
        instructionLabel = new JLabel("Please answer the following questions honestly. Your responses will help us recommend the right specialist for you.", SwingConstants.CENTER);
        progressLabel = new JLabel("Progress: 0/20 questions answered", SwingConstants.CENTER);

        // Progress bar
        progressBar = new JProgressBar(0, 20);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("0/20 completed");

        // Button components
        submitButton = new JButton("📊 Submit Assessment");
        backButton = new JButton("← Back");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        questionsPanel = new JPanel();
        buttonPanel = new JPanel();

        // Initialize arrays
        questionLabels = new JLabel[20];
        buttonGroups = new ButtonGroup[20];
        radioButtons = new JRadioButton[20][8];
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(instructionLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(progressLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(progressBar);

        // Questions panel setup
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setOpaque(false);
        questionsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Scroll pane for questions
        scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);

        // Button panel setup
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
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

        progressLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        progressLabel.setForeground(new Color(70, 130, 180));
        progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Progress bar styling
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        progressBar.setForeground(new Color(46, 204, 113));
        progressBar.setBackground(new Color(220, 220, 220));
        progressBar.setMaximumSize(new Dimension(400, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        styleButton(submitButton, new Dimension(200, 45), new Font("Segoe UI", Font.BOLD, 14), 
                   new Color(46, 204, 113), Color.WHITE);
        styleButton(backButton, new Dimension(100, 40), new Font("Segoe UI", Font.BOLD, 12), 
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

    private void createQuestions() {
        for (int i = 0; i < 20; i++) {
            // Create question panel for better organization
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setOpaque(false);
            questionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));
            questionPanel.setBackground(Color.WHITE);
            questionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, questionPanel.getPreferredSize().height));

            questionLabels[i] = new JLabel("<html><div style='margin-bottom: 10px;'><b>" + questions[i] + "</b></div></html>");
            questionLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
            questionLabels[i].setForeground(new Color(44, 62, 80));
            questionLabels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            
            questionPanel.add(questionLabels[i]);
            questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            buttonGroups[i] = new ButtonGroup();
            
            for (int j = 0; j < 8; j++) {
                radioButtons[i][j] = new JRadioButton("<html><div style='width: 600px;'>" + options[i][j] + "</div></html>");
                radioButtons[i][j].setActionCommand(String.valueOf(j));
                radioButtons[i][j].setFont(new Font("Segoe UI", Font.PLAIN, 12));
                radioButtons[i][j].setForeground(new Color(70, 70, 70));
                radioButtons[i][j].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
                radioButtons[i][j].setOpaque(false);
                radioButtons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                buttonGroups[i].add(radioButtons[i][j]);
                questionPanel.add(radioButtons[i][j]);
                
                // Add progress tracking
                radioButtons[i][j].addActionListener(e -> updateProgress());
            }

            questionsPanel.add(questionPanel);
            questionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
    }

    private void updateProgress() {
        int answeredQuestions = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 8; j++) {
                if (radioButtons[i][j].isSelected()) {
                    answeredQuestions++;
                    break;
                }
            }
        }
        
        progressBar.setValue(answeredQuestions);
        progressBar.setString(answeredQuestions + "/20 completed");
        progressLabel.setText("Progress: " + answeredQuestions + "/20 questions answered");
        
        // Enable submit button only when at least 10 questions are answered
        submitButton.setEnabled(answeredQuestions >= 10);
        if (answeredQuestions >= 10) {
            submitButton.setBackground(new Color(46, 204, 113));
        } else {
            submitButton.setBackground(new Color(189, 195, 199));
        }
    }

    private void addEventListeners() {
        getRootPane().setDefaultButton(submitButton);
        
        // Initially disable submit button
        submitButton.setEnabled(false);
        submitButton.setBackground(new Color(189, 195, 199));

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performAssessment();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AssessmentPage();
            }
        });
    }

    private void performAssessment() {
        try {
            int[] conditionCounts = new int[8];
            int answeredQuestions = 0;
            
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 8; j++) {
                    if (radioButtons[i][j].isSelected()) {
                        conditionCounts[j]++;
                        answeredQuestions++;
                        break;
                    }
                }
            }
            
            if (answeredQuestions < 10) {
                showErrorMessage("Please answer at least 10 questions to get a proper assessment!");
                return;
            }
            
            int maxCount = 0;
            int maxIndex = 7; // Default to "Normal"
            for (int i = 0; i < 8; i++) {
                if (conditionCounts[i] > maxCount) {
                    maxCount = conditionCounts[i];
                    maxIndex = i;
                }
            }
            
            String suggestedCondition = conditionNames[maxIndex];
            
            // Save to database
            conn = DBconnection.getConnection();
            String getPatientIdQuery = "select patient_id from patient order by patient_id desc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getPatientIdQuery);
            int patientId = -1;
            if (rs.next()) {
                patientId = rs.getInt("patient_id");
            }
            
            if (patientId != -1) {
                String updateQuery = "update patient set condition_name='" + suggestedCondition + "' where patient_id=" + patientId;
                int result = st.executeUpdate(updateQuery);
                if (result > 0) {
                    showSuccessMessage("Assessment completed successfully!\n\nBased on your answers, we suggest consulting with doctors specializing in:\n" + 
                                     suggestedCondition + "\n\nYou will now be redirected to view available specialists.");
                    setVisible(false);
                    new DoctorsListPage(suggestedCondition, "assessment").setVisible(true);
                } else {
                    showErrorMessage("Failed to save assessment results!");
                }
            } else {
                showErrorMessage("No patient found to update condition!");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            showErrorMessage("Assessment failed: " + ex.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Assessment Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Assessment Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PsychologicalAssessment();
        });
    }
}
