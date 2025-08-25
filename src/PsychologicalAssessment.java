import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PsychologicalAssessment extends JFrame {
    Connection conn = null;
    JLabel label_title;
    JLabel label_instruction;
    JPanel mainPanel;
    JPanel questionsPanel;
    JScrollPane scrollPane;
    JButton submit_but;
    JButton back_but;

    // Arrays to store question labels and radio button groups
    JLabel[] questionLabels;
    ButtonGroup[] buttonGroups;
    JRadioButton[][] radioButtons;

    // Questions and options
    String[] questions = {
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

    String[][] options = {
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

    String[] conditionNames = {
        "Depression", "ADHD", "Anxiety", "Bipolar Disorder", "OCD", "Schizophrenia", "Narcissistic Personality Disorder", "Normal"
    };

    public PsychologicalAssessment() {
        super("Psychological Assessment");
        conn = null;
        label_title = new JLabel("Psychological Assessment");
        label_instruction = new JLabel("Please answer the following questions:");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(questionsPanel);
        submit_but = new JButton("Submit");
        back_but = new JButton("Back");

        questionLabels = new JLabel[20];
        buttonGroups = new ButtonGroup[20];
        radioButtons = new JRadioButton[20][8];

        createQuestions();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(submit_but);
        buttonPanel.add(back_but);

        mainPanel.add(label_title, BorderLayout.NORTH);
        mainPanel.add(label_instruction, BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButtonListeners();
    }

    private void createQuestions() {
        for (int i = 0; i < 20; i++) {
            questionLabels[i] = new JLabel("<html><b>" + questions[i] + "</b></html>");
            questionsPanel.add(questionLabels[i]);
            questionsPanel.add(Box.createVerticalStrut(10));
            buttonGroups[i] = new ButtonGroup();
            for (int j = 0; j < 8; j++) {
                radioButtons[i][j] = new JRadioButton("<html>" + options[i][j] + "</html>");
                radioButtons[i][j].setActionCommand(String.valueOf(j));
                buttonGroups[i].add(radioButtons[i][j]);
                radioButtons[i][j].setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
                questionsPanel.add(radioButtons[i][j]);
            }
            questionsPanel.add(Box.createVerticalStrut(15));
            questionsPanel.add(new JSeparator());
            questionsPanel.add(Box.createVerticalStrut(15));
        }
    }

    private void addButtonListeners() {
        submit_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                        JOptionPane.showMessageDialog(PsychologicalAssessment.this,
                            "Please answer at least 10 questions to get a proper assessment!");
                        return;
                    }
                    int maxCount = 0;
                    int maxIndex = 7;
                    for (int i = 0; i < 8; i++) {
                        if (conditionCounts[i] > maxCount) {
                            maxCount = conditionCounts[i];
                            maxIndex = i;
                        }
                    }
                    String suggestedCondition = conditionNames[maxIndex];
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
                            JOptionPane.showMessageDialog(PsychologicalAssessment.this,
                                "Assessment completed!\nBased on your answers, we suggest consulting with doctors specializing in: " + suggestedCondition);
                            setVisible(false);
                            new DoctorsListPage(suggestedCondition, "assessment").setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(PsychologicalAssessment.this, "Failed to save assessment results!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(PsychologicalAssessment.this, "No patient found to update condition!");
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(PsychologicalAssessment.this, "Assessment failed: " + ex.getMessage());
                }
            }
        });
        back_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AssessmentPage().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new PsychologicalAssessment().setVisible(true);
    }
}
