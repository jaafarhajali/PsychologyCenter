import javax.swing.*;

/**
 * Demo class to showcase the improved GUI designs for Psychology Center
 * This class provides a menu to view different improved pages
 */
public class ImprovedGUIDemo extends JFrame {
    private JButton welcomePageBtn, loginPageBtn, registrationPageBtn, adminPageBtn, appointmentPageBtn, doctorsListBtn;
    private JPanel mainPanel;

    public ImprovedGUIDemo() {
        setTitle("Psychology Center - Improved GUI Demo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setupLayout();
        addEventListeners();
    }

    private void initializeComponents() {
        welcomePageBtn = new JButton("🏠 Welcome Page");
        loginPageBtn = new JButton("🔐 Patient Login Page");
        registrationPageBtn = new JButton("📝 Registration Page");
        adminPageBtn = new JButton("⚙️ Admin Dashboard");
        appointmentPageBtn = new JButton("📅 Appointment Booking");
        doctorsListBtn = new JButton("👨‍⚕️ Doctors List");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
    }

    private void setupLayout() {
        JLabel titleLabel = new JLabel("Psychology Center - Improved GUI Showcase");
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Click any button to view the improved design:");
        instructionLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        instructionLabel.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        mainPanel.add(instructionLabel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 30)));

        JButton[] buttons = {welcomePageBtn, loginPageBtn, registrationPageBtn, adminPageBtn, appointmentPageBtn, doctorsListBtn};
        for (JButton button : buttons) {
            button.setAlignmentX(CENTER_ALIGNMENT);
            button.setMaximumSize(new java.awt.Dimension(300, 40));
            mainPanel.add(button);
            mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        }

        add(mainPanel);
    }

    private void addEventListeners() {
        welcomePageBtn.addActionListener(e -> new WelcomePage());
        
        loginPageBtn.addActionListener(e -> new LoginPageImproved().setVisible(true));
        
        registrationPageBtn.addActionListener(e -> new RegistrationPageImproved().setVisible(true));
        
        adminPageBtn.addActionListener(e -> new AdminSelectionPageImproved().setVisible(true));
        
        appointmentPageBtn.addActionListener(e -> {
            // Demo with sample data
            new AppointmentPageImproved(1, "Dr. Sarah Johnson", 150.0).setVisible(true);
        });
        
        doctorsListBtn.addActionListener(e -> {
            // Demo with sample data
            new DoctorsListPageImproved("Depression", "condition").setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImprovedGUIDemo().setVisible(true);
        });
    }
}
