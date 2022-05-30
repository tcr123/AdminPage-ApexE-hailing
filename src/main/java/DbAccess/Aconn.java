package DbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Aconn {
    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://10.210.31.31:3306/apex";
            String username = "apex";
            String password = "1118";
            Class.forName(driver);

            //System.out.println("Connected");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {

            System.out.println(e);
        }
        return null;
    }
    public static int AdminLogin (String Credential, String password) throws Exception {
        // A FUNCTION TO LOG IN TO ADMIN ACCOUNT BY CHECKING CREDENTIAL ON MYSQL USER TABLE
        // IF WRONG USERNAME, RETURN 1, IF WRONG PASSWORD, RETURN 2, IF SUCCESS, RETURN 0
        Connection con = getConnection();


        //CHECK EXISTENCE OF CREDENTIAL
        PreparedStatement statement = con.prepareStatement("SELECT COUNT(username) AS got FROM apex.authenication WHERE name = '"+ Credential +"'  ");
        ResultSet exist = statement.executeQuery();
        int got = 0;
        while (exist.next()) {
            got = exist.getInt("got");
        }
        if (got == 0) {
            System.out.println("\nSorry, your credential does not exists!");
            return 1;
        }

        //CHECK PASSWORD IS COLLECT ACCORDING TO ID AND IDENTITY
        statement = con.prepareStatement("SELECT COUNT(id) AS got FROM authenication WHERE name = '" + Credential + "' AND password = '" + password + "' ");
        exist = statement.executeQuery();
        int got1 = 0;
        while (exist.next()) {
            got1 = exist.getInt("got");
        }
        if (got1 == 0) {
            System.out.println("\nWrong password!");
            return 2;
        }

        return 0;
    }
    public static ResultSet ShowDriver() throws Exception {
        //show all driver
        Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM apex.driver  ");
        ResultSet exist = statement.executeQuery();
        return exist;
    }
    public static void AddDriver(String name, int cap,String location, double rate) throws Exception {
        //add driver
        Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement("INSERT INTO `apex`.`driver` (`name`, `capacity`, `location`, `status`, `rating`) VALUES ('"+name+"', '"+cap+"', '"+location+"', 'Available','"+rate+"')  ");
        ResultSet exist = statement.executeQuery();
    }
    public static ResultSet ShowUser() throws Exception {
        //show all User
        Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM apex.user  ");
        ResultSet exist = statement.executeQuery();
        return exist;
    }

    public static void DelDriver(String name) throws Exception {
        //add driver
        Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement("DELETE FROM `apex`.`driver` WHERE (`name` = '"+name+"'); ");
        ResultSet exist = statement.executeQuery();
    }
}
