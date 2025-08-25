import java.sql.*;
public class DBconnection{
    static String URL="jdbc:sqlserver://JAAFARHAJALI\\MSSQLSERVER2;databaseName=PsychologyCenterDB;encrypt=false;trustServerCertificate=true";
    static String username="sa";
    static String password="12345";
    public static Connection getConnection(){
      Connection conn=null;
      try{
          conn=DriverManager.getConnection(URL,username,password);
          System.out.println("Connection to database has been established");

      }
      catch(Exception e){

            System.out.println("Error"+e.getMessage());
      }


      return conn;

    }
    public static void main(String[] args) {
        try (Connection conn = DBconnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
}
    }
}