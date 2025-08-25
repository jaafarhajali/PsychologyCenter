import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class AppointmentPage extends JFrame {
Connection conn=null;
JLabel label_title;
JLabel label_patient;
JLabel label_doctor;
JLabel label_date;
JLabel label_time;
JLabel label_fees;
JTextField text_patient;
JTextField text_doctor;
JTextField text_fees;
JComboBox<String> date_dropdown;
JComboBox<String> time_dropdown;
JPanel panel;
JButton book_but;
JButton back_but;
int doctorId;
int patientId;
String selectedDoctor;
double sessionFees;
String previousCondition;
String previousSource;

AppointmentPage(int doctorId, String doctorName, double fees){
super("Appointment Page");
this.doctorId = doctorId;
this.selectedDoctor = doctorName;
this.sessionFees = fees;
this.previousCondition = "General"; // Default fallback
this.previousSource = "condition"; // Default fallback

// Create labels
label_title=new JLabel("Book Appointment");
label_patient=new JLabel("Patient Name:");
label_doctor=new JLabel("Doctor Name:");
label_date=new JLabel("Appointment Date:");
label_time=new JLabel("Appointment Time:");
label_fees=new JLabel("Session Fees:");

// Create text fields
text_patient=new JTextField();
text_doctor=new JTextField();
text_fees=new JTextField();

// Fill doctor name and fees (read-only)
text_doctor.setText(doctorName);
text_doctor.setEditable(false);
text_fees.setText("$" + fees);
text_fees.setEditable(false);

// Load current patient name from database
loadCurrentPatient();

// Create date dropdown (next 30 weekdays only)
date_dropdown=new JComboBox<>();
loadWeekdays();

// Create time dropdown (8 AM to 4 PM, 1-hour sessions)
String[] times = {
    "Select time...",
    "08:00 AM",
    "09:00 AM", 
    "10:00 AM",
    "11:00 AM",
    "12:00 PM",
    "01:00 PM",
    "02:00 PM",
    "03:00 PM",
    "04:00 PM"
};
time_dropdown=new JComboBox<>(times);

// Create buttons
book_but=new JButton("Book Appointment");
back_but=new JButton("Back");

// Create panel
panel=new JPanel();
panel.setLayout(new GridLayout(8,2));

// Add components to panel
panel.add(label_title);
panel.add(new JLabel("")); // Empty space
panel.add(label_patient);
panel.add(text_patient);
panel.add(label_doctor);
panel.add(text_doctor);
panel.add(label_fees);
panel.add(text_fees);
panel.add(label_date);
panel.add(date_dropdown);
panel.add(label_time);
panel.add(time_dropdown);
panel.add(book_but);
panel.add(back_but);

// Add panel to frame
add(panel,"Center");

// Set frame properties
setSize(500,450);
setResizable(true);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Book appointment button functionality
book_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
   try{
   // Get data from form
   String patientName=text_patient.getText();
   String doctorName=text_doctor.getText();
   String selectedDate=(String)date_dropdown.getSelectedItem();
   String selectedTime=(String)time_dropdown.getSelectedItem();
   
   // Check if all fields are filled
   if(patientName.isEmpty() || selectedDate.equals("Select date...") || selectedTime.equals("Select time...")){
       JOptionPane.showMessageDialog(AppointmentPage.this,"Please fill all fields!");
       return;
   }
   conn=DBconnection.getConnection();
   String dbTime = convertTimeForDB(selectedTime);
   // Insert appointment using new schema
   String query="insert into appointment (appointment_date, appointment_time, doctor_id, patient_id) values ('"
                +selectedDate+"','"+dbTime+"',"+doctorId+","+patientId+")";
   Statement st=conn.createStatement();
   int result=st.executeUpdate(query);
   if(result>0){
       JOptionPane.showMessageDialog(AppointmentPage.this,"Appointment booked successfully!");
       text_patient.setText("");
       date_dropdown.setSelectedIndex(0);
       time_dropdown.setSelectedIndex(0);
   }
   else{
       JOptionPane.showMessageDialog(AppointmentPage.this,"Failed to book appointment!");
   }
   }
   catch(Exception ex){
       System.out.println("Error: "+ex.getMessage());
       JOptionPane.showMessageDialog(AppointmentPage.this,"Booking failed: "+ex.getMessage());
   }
 }
});

// Back button functionality
back_but.addActionListener(new ActionListener(){
 public void actionPerformed(ActionEvent e){
     // Go back to previous page
     setVisible(false);
     new DoctorsListPage(previousCondition, previousSource).setVisible(true);
 }
});
}

// Overloaded constructor that accepts previous page information
AppointmentPage(int doctorId, String doctorName, double fees, String previousCondition, String previousSource){
    this(doctorId, doctorName, fees); // Call the main constructor
    this.previousCondition = previousCondition;
    this.previousSource = previousSource;
}

// Method to load current patient name and id from database (latest registered user)
private void loadCurrentPatient(){
    try{
        conn=DBconnection.getConnection();
        String query="select patient_id, full_name from patient order by patient_id desc";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(query);
        if(rs.next()){
            patientId = rs.getInt("patient_id");
            String patientName = rs.getString("full_name");
            text_patient.setText(patientName);
        }
    }
    catch(Exception ex){
        System.out.println("Error loading patient: "+ex.getMessage());
    }
}

// Method to load weekdays (Monday to Friday) for next 30 days
private void loadWeekdays(){
    date_dropdown.addItem("Select date...");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    int count = 0;
    while(count < 30){ // Show next 30 weekdays
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        
        // Check if it's Monday(2) to Friday(6)
        if(dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY){
            String dateStr = sdf.format(cal.getTime());
            date_dropdown.addItem(dateStr);
            count++;
        }
        
        // Move to next day
        cal.add(Calendar.DAY_OF_MONTH, 1);
    }
}

// Method to convert time format for database
private String convertTimeForDB(String time){
    // Convert "08:00 AM" to "08:00:00"
    String[] parts = time.split(" ");
    String timePart = parts[0];
    String ampm = parts[1];
    
    String[] hourMin = timePart.split(":");
    int hour = Integer.parseInt(hourMin[0]);
    
    // Convert to 24-hour format
    if(ampm.equals("PM") && hour != 12){
        hour += 12;
    }
    else if(ampm.equals("AM") && hour == 12){
        hour = 0;
    }
    
    return String.format("%02d:00:00", hour);
}

    public static void main(String[] args) {
        new AppointmentPage(1, "Dr. Test", 120.0).setVisible(true);
    }
}
