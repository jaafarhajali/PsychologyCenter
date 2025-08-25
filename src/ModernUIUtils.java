import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern UI Design System Utility Class
 * Provides consistent styling, colors, and components following Material Design principles
 */
public class ModernUIUtils {
    
    // Modern Color Palette
    public static final Color PRIMARY_BLUE = new Color(0, 102, 204);        // #0066CC
    public static final Color PRIMARY_BLUE_DARK = new Color(0, 82, 164);    // Darker shade
    public static final Color PRIMARY_BLUE_LIGHT = new Color(51, 139, 219); // Lighter shade
    
    public static final Color SECONDARY_GREEN = new Color(0, 184, 148);     // #00B894
    public static final Color ACCENT_TEAL = new Color(0, 206, 201);         // #00CEC9
    public static final Color NEUTRAL_DARK = new Color(45, 52, 54);         // #2D3436
    public static final Color NEUTRAL_MEDIUM = new Color(99, 110, 114);     // #636E72
    public static final Color NEUTRAL_LIGHT = new Color(178, 190, 195);     // #B2BEC3
    
    public static final Color BACKGROUND_PRIMARY = new Color(248, 249, 250); // #F8F9FA
    public static final Color BACKGROUND_SECONDARY = Color.WHITE;
    public static final Color BACKGROUND_CARD = Color.WHITE;
    
    public static final Color SUCCESS_GREEN = new Color(0, 184, 148);       // #00B894
    public static final Color WARNING_ORANGE = new Color(253, 203, 110);    // #FDCB6E
    public static final Color ERROR_RED = new Color(231, 76, 60);           // #E74C3C
    public static final Color INFO_BLUE = new Color(116, 185, 255);         // #74B9FF
    
    // Typography
    public static final Font FONT_HEADING_LARGE = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_HEADING_MEDIUM = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADING_SMALL = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY_LARGE = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font FONT_BODY_MEDIUM = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_CAPTION = new Font("Segoe UI", Font.PLAIN, 11);
    
    // Spacing System (8px base unit)
    public static final int SPACING_XS = 4;
    public static final int SPACING_SM = 8;
    public static final int SPACING_MD = 16;
    public static final int SPACING_LG = 24;
    public static final int SPACING_XL = 32;
    public static final int SPACING_XXL = 48;
    
    // Border Radius
    public static final int RADIUS_SM = 4;
    public static final int RADIUS_MD = 8;
    public static final int RADIUS_LG = 12;
    public static final int RADIUS_XL = 16;
    
    /**
     * Create a modern gradient background panel
     */
    public static JPanel createGradientPanel(Color startColor, Color endColor) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    0, getHeight(), endColor
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }
    
    /**
     * Create a modern card panel with shadow effect
     */
    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(BACKGROUND_CARD);
        card.setBorder(createCardBorder());
        return card;
    }
    
    /**
     * Create a card border with shadow effect
     */
    public static Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            BorderFactory.createEmptyBorder(SPACING_LG, SPACING_LG, SPACING_LG, SPACING_LG)
        );
    }
    
    /**
     * Create a modern primary button
     */
    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, PRIMARY_BLUE, Color.WHITE, PRIMARY_BLUE_DARK);
    }
    
    /**
     * Create a modern secondary button
     */
    public static JButton createSecondaryButton(String text) {
        return createStyledButton(text, BACKGROUND_SECONDARY, PRIMARY_BLUE, NEUTRAL_LIGHT);
    }
    
    /**
     * Create a success button
     */
    public static JButton createSuccessButton(String text) {
        return createStyledButton(text, SUCCESS_GREEN, Color.WHITE, SUCCESS_GREEN.darker());
    }
    
    /**
     * Create a warning button
     */
    public static JButton createWarningButton(String text) {
        return createStyledButton(text, WARNING_ORANGE, NEUTRAL_DARK, WARNING_ORANGE.darker());
    }
    
    /**
     * Create a danger button
     */
    public static JButton createDangerButton(String text) {
        return createStyledButton(text, ERROR_RED, Color.WHITE, ERROR_RED.darker());
    }
    
    /**
     * Create a styled button with hover effects
     */
    public static JButton createStyledButton(String text, Color bgColor, Color textColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create rounded rectangle
                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), RADIUS_MD, RADIUS_MD);
                g2d.setColor(getBackground());
                g2d.fill(roundedRectangle);
                
                // Draw text
                g2d.setColor(getForeground());
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
        };
        
        button.setFont(FONT_BUTTON);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createEmptyBorder(SPACING_MD, SPACING_LG, SPACING_MD, SPACING_LG));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.repaint();
            }
        });
        
        return button;
    }
    
    /**
     * Create a modern text field with floating label effect
     */
    public static JTextField createModernTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(FONT_BODY_MEDIUM);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEUTRAL_LIGHT, 1),
            BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
        ));
        textField.setBackground(BACKGROUND_SECONDARY);
        
        // Add placeholder text
        textField.setText(placeholder);
        textField.setForeground(NEUTRAL_MEDIUM);
        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(NEUTRAL_DARK);
                }
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_BLUE, 2),
                    BorderFactory.createEmptyBorder(SPACING_MD-1, SPACING_MD-1, SPACING_MD-1, SPACING_MD-1)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(NEUTRAL_MEDIUM);
                }
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(NEUTRAL_LIGHT, 1),
                    BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
                ));
            }
        });
        
        return textField;
    }
    
    /**
     * Create a modern password field
     */
    public static JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(FONT_BODY_MEDIUM);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEUTRAL_LIGHT, 1),
            BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
        ));
        passwordField.setBackground(BACKGROUND_SECONDARY);
        
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_BLUE, 2),
                    BorderFactory.createEmptyBorder(SPACING_MD-1, SPACING_MD-1, SPACING_MD-1, SPACING_MD-1)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(NEUTRAL_LIGHT, 1),
                    BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
                ));
            }
        });
        
        return passwordField;
    }
    
    /**
     * Create a modern loading indicator
     */
    public static JPanel createLoadingIndicator(String message) {
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(BACKGROUND_CARD);
        centerPanel.setBorder(createCardBorder());
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        // Simple loading animation placeholder
        JLabel loadingIcon = new JLabel("⏳", SwingConstants.CENTER);
        loadingIcon.setFont(new Font("Arial", Font.PLAIN, 24));
        loadingIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel loadingText = new JLabel(message, SwingConstants.CENTER);
        loadingText.setFont(FONT_BODY_MEDIUM);
        loadingText.setForeground(NEUTRAL_DARK);
        loadingText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(loadingIcon);
        centerPanel.add(Box.createVerticalStrut(SPACING_MD));
        centerPanel.add(loadingText);
        centerPanel.add(Box.createVerticalGlue());
        
        loadingPanel.add(centerPanel, BorderLayout.CENTER);
        return loadingPanel;
    }
    
    /**
     * Create a section header label
     */
    public static JLabel createSectionHeader(String text) {
        JLabel header = new JLabel(text);
        header.setFont(FONT_HEADING_SMALL);
        header.setForeground(NEUTRAL_DARK);
        return header;
    }
    
    /**
     * Create a body text label
     */
    public static JLabel createBodyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_BODY_MEDIUM);
        label.setForeground(NEUTRAL_DARK);
        return label;
    }
    
    /**
     * Create a caption label
     */
    public static JLabel createCaptionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_CAPTION);
        label.setForeground(NEUTRAL_MEDIUM);
        return label;
    }
    
    /**
     * Create a progress bar with modern styling
     */
    public static JProgressBar createModernProgressBar() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBackground(NEUTRAL_LIGHT);
        progressBar.setForeground(PRIMARY_BLUE);
        progressBar.setBorder(BorderFactory.createEmptyBorder(SPACING_SM, 0, SPACING_SM, 0));
        return progressBar;
    }
    
    /**
     * Medical Icons (Unicode medical symbols)
     */
    public static final String ICON_MEDICAL_CROSS = "⚕";
    public static final String ICON_STETHOSCOPE = "🩺";
    public static final String ICON_HEART = "❤";
    public static final String ICON_CALENDAR = "📅";
    public static final String ICON_CLOCK = "🕐";
    public static final String ICON_USER = "👤";
    public static final String ICON_DOCTOR = "👨‍⚕️";
    public static final String ICON_NURSE = "👩‍⚕️";
    public static final String ICON_PILL = "💊";
    public static final String ICON_SYRINGE = "💉";
    public static final String ICON_THERMOMETER = "🌡";
    public static final String ICON_AMBULANCE = "🚑";
    public static final String ICON_HOSPITAL = "🏥";
    public static final String ICON_PHONE = "📞";
    public static final String ICON_EMAIL = "📧";
    public static final String ICON_LOCATION = "📍";
    public static final String ICON_SUCCESS = "✅";
    public static final String ICON_WARNING = "⚠";
    public static final String ICON_ERROR = "❌";
    public static final String ICON_INFO = "ℹ";
}
