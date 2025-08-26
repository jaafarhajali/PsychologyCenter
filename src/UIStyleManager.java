import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Centralized UI Style Manager for consistent theming across the Psychology Center application
 * This class provides standardized colors, fonts, dimensions, and styling methods
 */
public class UIStyleManager {
    
    // Color Palette
    public static class Colors {
        // Primary Colors
        public static final Color PRIMARY_BLUE = new Color(52, 152, 219);
        public static final Color PRIMARY_GREEN = new Color(46, 204, 113);
        public static final Color PRIMARY_PURPLE = new Color(155, 89, 182);
        public static final Color PRIMARY_RED = new Color(231, 76, 60);
        public static final Color PRIMARY_ORANGE = new Color(230, 126, 34);
        
        // Neutral Colors
        public static final Color BACKGROUND_LIGHT = new Color(248, 250, 252);
        public static final Color BACKGROUND_WHITE = Color.WHITE;
        public static final Color BACKGROUND_CARD = new Color(247, 250, 252);
        
        // Text Colors
        public static final Color TEXT_PRIMARY = new Color(44, 62, 80);
        public static final Color TEXT_SECONDARY = new Color(100, 100, 100);
        public static final Color TEXT_LIGHT = new Color(149, 165, 166);
        public static final Color TEXT_SUCCESS = new Color(39, 174, 96);
        public static final Color TEXT_ERROR = new Color(192, 57, 43);
        
        // Border Colors
        public static final Color BORDER_LIGHT = new Color(220, 220, 220);
        public static final Color BORDER_MEDIUM = new Color(189, 195, 199);
        public static final Color BORDER_FOCUS = new Color(52, 152, 219);
        
        // Selection Colors
        public static final Color SELECTION_BACKGROUND = new Color(52, 152, 219, 100);
        public static final Color HOVER_BACKGROUND = new Color(240, 248, 255);
    }
    
    // Typography
    public static class Fonts {
        public static final String PRIMARY_FONT = "Segoe UI";
        public static final Font TITLE_LARGE = new Font(PRIMARY_FONT, Font.BOLD, 32);
        public static final Font TITLE_MEDIUM = new Font(PRIMARY_FONT, Font.BOLD, 24);
        public static final Font TITLE_SMALL = new Font(PRIMARY_FONT, Font.BOLD, 18);
        public static final Font SUBTITLE = new Font(PRIMARY_FONT, Font.PLAIN, 16);
        public static final Font BODY_LARGE = new Font(PRIMARY_FONT, Font.PLAIN, 14);
        public static final Font BODY_MEDIUM = new Font(PRIMARY_FONT, Font.PLAIN, 12);
        public static final Font BODY_SMALL = new Font(PRIMARY_FONT, Font.PLAIN, 11);
        public static final Font BUTTON_FONT = new Font(PRIMARY_FONT, Font.BOLD, 14);
        public static final Font LABEL_FONT = new Font(PRIMARY_FONT, Font.BOLD, 14);
    }
    
    // Dimensions
    public static class Dimensions {
        // Button Sizes
        public static final Dimension BUTTON_LARGE = new Dimension(200, 45);
        public static final Dimension BUTTON_MEDIUM = new Dimension(150, 40);
        public static final Dimension BUTTON_SMALL = new Dimension(100, 35);
        
        // Field Sizes
        public static final Dimension FIELD_STANDARD = new Dimension(300, 40);
        public static final Dimension FIELD_LARGE = new Dimension(400, 40);
        public static final Dimension FIELD_SMALL = new Dimension(200, 35);
        
        // Window Constraints
        public static final Dimension MIN_WINDOW_SIZE = new Dimension(400, 300);
        public static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
    }
    
    // Spacing
    public static class Spacing {
        public static final int SMALL = 5;
        public static final int MEDIUM = 10;
        public static final int LARGE = 20;
        public static final int XLARGE = 30;
    }
    
    /**
     * Apply standard styling to a JButton
     */
    public static void styleButton(JButton button, Color backgroundColor, Color textColor) {
        styleButton(button, backgroundColor, textColor, Dimensions.BUTTON_MEDIUM);
    }
    
    /**
     * Apply standard styling to a JButton with custom size
     */
    public static void styleButton(JButton button, Color backgroundColor, Color textColor, Dimension size) {
        button.setPreferredSize(size);
        button.setFont(Fonts.BUTTON_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(backgroundColor.brighter());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(backgroundColor);
                }
            }
        });
    }
    
    /**
     * Apply standard styling to a JTextField
     */
    public static void styleTextField(JTextField field) {
        styleTextField(field, Dimensions.FIELD_STANDARD);
    }
    
    /**
     * Apply standard styling to a JTextField with custom size
     */
    public static void styleTextField(JTextField field, Dimension size) {
        field.setFont(Fonts.BODY_LARGE);
        field.setPreferredSize(size);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER_MEDIUM, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Add focus effects
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colors.BORDER_FOCUS, 2),
                    BorderFactory.createEmptyBorder(7, 11, 7, 11)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colors.BORDER_MEDIUM, 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }
    
    /**
     * Apply standard styling to a JLabel
     */
    public static void styleLabel(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
    }
    
    /**
     * Create a standard panel with background color
     */
    public static JPanel createStandardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Colors.BACKGROUND_LIGHT);
        return panel;
    }
    
    /**
     * Create a card-style panel
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Colors.BACKGROUND_WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(Spacing.LARGE, Spacing.LARGE, Spacing.LARGE, Spacing.LARGE)
        ));
        return panel;
    }
    
    /**
     * Apply window constraints (resizable with min/max limits)
     */
    public static void applyWindowConstraints(JFrame frame, Dimension preferredSize) {
        frame.setSize(preferredSize);
        frame.setMinimumSize(Dimensions.MIN_WINDOW_SIZE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
    }
    
    /**
     * Style a JTable with standard appearance
     */
    public static void styleTable(JTable table) {
        table.setFont(Fonts.BODY_MEDIUM);
        table.setRowHeight(30);
        table.setGridColor(Colors.BORDER_LIGHT);
        table.setSelectionBackground(Colors.SELECTION_BACKGROUND);
        table.setSelectionForeground(Color.BLACK);
        
        // Style table header
        table.getTableHeader().setFont(Fonts.LABEL_FONT);
        table.getTableHeader().setBackground(Colors.PRIMARY_BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 35));
    }
    
    /**
     * Create a styled scroll pane
     */
    public static JScrollPane createStyledScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createLineBorder(Colors.BORDER_LIGHT, 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    
    /**
     * Show a styled success message
     */
    public static void showSuccessMessage(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, 
            "<html><center><b style='color: " + toHex(Colors.TEXT_SUCCESS) + ";'>" + title + "</b><br><br>" + 
            message + "</center></html>", 
            title, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show a styled error message
     */
    public static void showErrorMessage(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, 
            "<html><center><b style='color: " + toHex(Colors.TEXT_ERROR) + ";'>" + title + "</b><br><br>" + 
            message + "</center></html>", 
            title, 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Convert Color to hex string for HTML
     */
    private static String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
    
    /**
     * Create a loading dialog
     */
    public static JDialog createLoadingDialog(JFrame parent, String message) {
        JDialog loadingDialog = new JDialog(parent, "Please Wait", true);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loadingDialog.setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(Colors.BACKGROUND_WHITE);
        
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(Fonts.BODY_LARGE);
        messageLabel.setForeground(Colors.TEXT_PRIMARY);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Processing...");
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);
        
        loadingDialog.add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(parent);
        
        return loadingDialog;
    }
}
