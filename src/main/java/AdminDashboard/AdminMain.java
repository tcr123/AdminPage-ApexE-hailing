package AdminDashboard;

import java.sql.Connection;
import java.sql.DriverManager;

public class AdminMain {
    public static void main(String[] args) throws Exception {
        //Connection conn = getConnection();
        AdminLogin.accessAdminLoginPage();
    }

    public static Connection getConnection() throws Exception{
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/xxxx";
            String username = ""; //username
            String password = ""; //password
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            //System.out.println("Connected");
            return conn;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
