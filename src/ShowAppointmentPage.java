import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ShowAppointmentPage extends JFrame {
Connection conn=null;
JLabel label_title;
JLabel label_search;
JTextField search_field;
JButton search_button;
JButton clear_button;
JPanel panel;
JPanel searchPanel;
JTable table;
JScrollPane scrollPane;
JButton logoutButton;
JButton backButton;

ShowAppointmentPage(){
super("Show Appointments Page");
label_title=new JLabel("Below is the complete list of appointments scheduled for all doctors:");
label_title.setFont(new Font("Arial", Font.BOLD, 16));

// Create search components
label_search = new JLabel("Search by Doctor Name:");
search_field = new JTextField(20);
search_button = new JButton("Search");
clear_button = new JButton("Clear");

// Create search panel
searchPanel = new JPanel();
searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
searchPanel.add(label_search);
searchPanel.add(search_field);
searchPanel.add(search_button);
searchPanel.add(clear_button);

panel=new JPanel();
panel.setLayout(new BorderLayout());
panel.add(label_title, BorderLayout.NORTH);

// Create a combined panel for search and table
JPanel contentPanel = new JPanel();
contentPanel.setLayout(new BorderLayout());
contentPanel.add(searchPanel, BorderLayout.NORTH);

JPanel tablePanel = new JPanel();
tablePanel.setLayout(new BorderLayout());

String[] columnNames = {"Patient Name", "Doctor Name", "Date", "Time"};
Object[][] data = getAppointmentsData("");
table = new JTable(data, columnNames);
table.setEnabled(false);
scrollPane = new JScrollPane(table);
tablePanel.add(scrollPane, BorderLayout.CENTER);

contentPanel.add(tablePanel, BorderLayout.CENTER);
panel.add(contentPanel, BorderLayout.CENTER);

logoutButton = new JButton("Logout");
logoutButton.setBackground(new Color(255, 182, 193)); // Light pink for logout
backButton = new JButton("Back");
backButton.setBackground(new Color(135, 206, 250));

JPanel bottomPanel = new JPanel();
bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
bottomPanel.add(backButton);
bottomPanel.add(logoutButton);
tablePanel.add(bottomPanel, BorderLayout.SOUTH);

add(panel, "Center");
setSize(800,500);
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Add search functionality
search_button.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     String searchText = search_field.getText().trim();
     refreshTable(searchText);
 }
});

clear_button.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     search_field.setText("");
     refreshTable("");
 }
});

backButton.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     dispose();
     new AdminSelectionPage().setVisible(true);
 }
});

logoutButton.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     dispose();
     new WelcomePage().setVisible(true);
 }
});
}

// Method to refresh table based on search
private void refreshTable(String searchText) {
    String[] columnNames = {"Patient Name", "Doctor Name", "Date", "Time"};
    Object[][] data = getAppointmentsData(searchText);
    
    // Update table model
    table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    table.setEnabled(false);
}

private Object[][] getAppointmentsData(String searchText) {
    try {
        conn = DBconnection.getConnection();
        
        String query;
        if (searchText == null || searchText.trim().isEmpty()) {
            // Show all appointments
            query = "SELECT p.full_name AS patient_name, d.full_name AS doctor_name, a.appointment_date, a.appointment_time " +
                   "FROM appointment a " +
                   "JOIN patient p ON a.patient_id = p.patient_id " +
                   "JOIN doctor d ON a.doctor_id = d.doctor_id";
        } else {
            // Search by doctor name
            query = "SELECT p.full_name AS patient_name, d.full_name AS doctor_name, a.appointment_date, a.appointment_time " +
                   "FROM appointment a " +
                   "JOIN patient p ON a.patient_id = p.patient_id " +
                   "JOIN doctor d ON a.doctor_id = d.doctor_id " +
                   "WHERE d.full_name LIKE '%" + searchText + "%'";
        }
        
        System.out.println("Debug: Executing query: " + query);
        
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        // Use ArrayList to store data instead of trying to count rows first
        java.util.ArrayList<Object[]> dataList = new java.util.ArrayList<>();
        
        while(rs.next()){
            Object[] row = new Object[4];
            row[0] = rs.getString("patient_name");
            row[1] = rs.getString("doctor_name");
            row[2] = rs.getString("appointment_date");
            row[3] = rs.getString("appointment_time");
            dataList.add(row);
            System.out.println("Debug: Found appointment - Patient: " + row[0] + ", Doctor: " + row[1] + ", Date: " + row[2] + ", Time: " + row[3]);
        }
        
        // Convert ArrayList to 2D array
        Object[][] data = new Object[dataList.size()][4];
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
    new ShowAppointmentPage().setVisible(true);
}
}
