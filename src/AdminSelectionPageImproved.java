import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AdminSelectionPageImproved extends JFrame {
    private JLabel titleLabel, instructionLabel, adminIconLabel;
    private JButton showAppointmentsButton, addDoctorButton, logoutButton;
    private JPanel mainPanel, headerPanel, buttonPanel;

    public AdminSelectionPageImproved() {
        initializeComponents();
        setupLayout();
        styleComponents();
        addEventListeners();
        centerWindow();
    }

    private void initializeComponents() {
        setTitle("Admin Dashboard - Psychology Center");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Header components
        adminIconLabel = new JLabel("⚙️", SwingConstants.CENTER);
        titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        instructionLabel = new JLabel("Select an action to continue:", SwingConstants.CENTER);

        // Button components with icons
        showAppointmentsButton = new JButton("📅 View All Appointments");
        addDoctorButton = new JButton("👨‍⚕️ Add New Doctor");
        logoutButton = new JButton("🚪 Logout");

        // Panel components
        mainPanel = new JPanel(new BorderLayout());
        headerPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    private void setupLayout() {
        // Main panel setup
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        getContentPane().add(mainPanel);

        // Header panel setup
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.add(adminIconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(instructionLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Button panel setup with card layout effect
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 15, 0);
        buttonPanel.add(createActionCard(showAppointmentsButton, "Manage and view all scheduled appointments"), gbc);

        gbc.gridy = 1;
        buttonPanel.add(createActionCard(addDoctorButton, "Add new doctors to the system"), gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 0, 0);
        buttonPanel.add(logoutButton, gbc);

        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private JPanel createActionCard(JButton button, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(120, 120, 120));

        card.add(button, BorderLayout.CENTER);
        card.add(descLabel, BorderLayout.SOUTH);

        // Add hover effect to card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 250, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                button.doClick();
            }
        });

        return card;
    }

    private void styleComponents() {
        // Background
        mainPanel.setBackground(new Color(245, 250, 255));

        // Header styling
        adminIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        adminIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(155, 89, 182));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        instructionLabel.setForeground(new Color(100, 100, 100));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button styling
        Dimension buttonSize = new Dimension(400, 50);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

        styleButton(showAppointmentsButton, buttonSize, buttonFont, new Color(52, 152, 219), Color.WHITE);
        styleButton(addDoctorButton, buttonSize, buttonFont, new Color(46, 204, 113), Color.WHITE);
        
        // Logout button with different styling
        styleButton(logoutButton, new Dimension(200, 40), new Font("Segoe UI", Font.BOLD, 14), 
                   new Color(231, 76, 60), Color.WHITE);
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
        showAppointmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ShowAppointmentPage().setVisible(true);
            }
        });

        addDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddDrPage().setVisible(true);
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    AdminSelectionPageImproved.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    new WelcomePage().setVisible(true);
                }
            }
        });
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminSelectionPageImproved().setVisible(true);
        });
    }
}
