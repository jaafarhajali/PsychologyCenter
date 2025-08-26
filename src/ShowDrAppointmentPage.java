import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ShowDrAppointmentPage extends JFrame {
    Connection conn = null;
    JLabel label_title;
    JPanel panel;
    JTable table;
    JScrollPane scrollPane;
    JButton logoutButton;
    String doctorEmail;
    



    public ShowDrAppointmentPage(String doctorEmail) {
        super("Doctor Appointments Page");
        this.doctorEmail = doctorEmail;
        
        System.out.println("Debug: Creating ShowDrAppointmentPage with email: " + doctorEmail);
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(UIStyleManager.Colors.BACKGROUND_LIGHT);
        
        label_title = new JLabel("Below is the complete list of appointments scheduled for you:");
        UIStyleManager.styleLabel(label_title, UIStyleManager.Fonts.SUBTITLE, UIStyleManager.Colors.TEXT_PRIMARY);
        panel.add(label_title, BorderLayout.NORTH);

        String[] columnNames = {"Patient Name", "Date", "Time"};
        Object[][] data = getAppointmentsData();
        
        System.out.println("Debug: Data array length: " + data.length);
        
        table = new JTable(data, columnNames);
        table.setEnabled(false);
        UIStyleManager.styleTable(table);
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIStyleManager.Colors.BORDER_LIGHT, 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add logout button
        logoutButton = new JButton("Logout");
        UIStyleManager.styleButton(logoutButton, UIStyleManager.Colors.TEXT_LIGHT, Color.WHITE, UIStyleManager.Dimensions.BUTTON_MEDIUM);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WelcomePage().setVisible(true);
            }
        });
        panel.add(logoutButton, BorderLayout.SOUTH);

        add(panel, "Center");
        
        // Set full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



private Object[][] getAppointmentsData() {
    try {
        conn = DBconnection.getConnection();
        
        // Step 1: Check if doctor exists with this email
        System.out.println("Debug: Looking for doctor with email: '" + doctorEmail + "'");
        String doctorCheckQuery = "SELECT doctor_id, full_name FROM doctor WHERE email='" + doctorEmail + "'";
        Statement checkSt = conn.createStatement();
        ResultSet doctorRs = checkSt.executeQuery(doctorCheckQuery);
        
        if (!doctorRs.next()) {
            System.out.println("Debug: No doctor found with email: " + doctorEmail);
            // Let's see what doctors actually exist
            String allDoctorsQuery = "SELECT email, full_name FROM doctor";
            ResultSet allDoctors = checkSt.executeQuery(allDoctorsQuery);
            System.out.println("Debug: Available doctors in database:");
            while (allDoctors.next()) {
                System.out.println("  - Email: '" + allDoctors.getString("email") + "', Name: " + allDoctors.getString("full_name"));
            }
            return new Object[0][4];
        } else {
            int doctorId = doctorRs.getInt("doctor_id");
            String doctorName = doctorRs.getString("full_name");
            System.out.println("Debug: Found doctor - ID: " + doctorId + ", Name: " + doctorName);
            
            // Step 2: Check total appointments in database
            String totalAppQuery = "SELECT COUNT(*) as total FROM appointment";
            ResultSet totalRs = checkSt.executeQuery(totalAppQuery);
            if (totalRs.next()) {
                System.out.println("Debug: Total appointments in database: " + totalRs.getInt("total"));
            }
            
            // Step 3: Check appointments for this specific doctor
            String doctorAppQuery = "SELECT COUNT(*) as count FROM appointment WHERE doctor_id = " + doctorId;
            ResultSet countRs = checkSt.executeQuery(doctorAppQuery);
            if (countRs.next()) {
                int appointmentCount = countRs.getInt("count");
                System.out.println("Debug: Found " + appointmentCount + " appointments for doctor ID " + doctorId);
            }
        }
        
        // Step 4: Execute the main query
        String query = "SELECT p.full_name AS patient_name, a.appointment_date, a.appointment_time " +
                       "FROM appointment a " +
                       "JOIN patient p ON a.patient_id = p.patient_id " +
                       "JOIN doctor d ON a.doctor_id = d.doctor_id " +
                       "WHERE d.email='" + doctorEmail + "'";
        
        System.out.println("Debug: Executing main query: " + query);
        
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        // Use ArrayList to store data instead of trying to count rows first
        java.util.ArrayList<Object[]> dataList = new java.util.ArrayList<>();
        
        while(rs.next()){
            Object[] row = new Object[3];
            row[0] = rs.getString("patient_name");
            row[1] = rs.getString("appointment_date");
            row[2] = rs.getString("appointment_time");
            dataList.add(row);
            System.out.println("Debug: Found appointment - Patient: " + row[0] + ", Date: " + row[1] + ", Time: " + row[2]);
        }
        
        // Convert ArrayList to 2D array
        Object[][] data = new Object[dataList.size()][3];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        
        System.out.println("Debug: Returning " + data.length + " appointments");
        return data;
    } catch(Exception ex){
        System.out.println("Error: "+ex.getMessage());
        ex.printStackTrace();
        return new Object[0][4];
    }
}

public static void main(String[] args) {
    if (args.length == 0 || args[0].trim().isEmpty()) {
        System.out.println("Error: Please provide a doctor email as an argument.");
        return;
    }
    String email = args[0];
    new ShowDrAppointmentPage(email).setVisible(true);
}
}
