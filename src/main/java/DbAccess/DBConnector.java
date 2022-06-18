package DbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnector {
    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://10.210.21.151:3306/apex";
            String username = "apex";
            String password = "1118";
//            String url = "jdbc:mysql://localhost/apexTesting";
//            String username = "root";
//            String password = "jack55092080";
            Class.forName(driver);
//            System.out.println("Connected to database");
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // METHOD TO CREATE ACCOUNT, IF ACCOUNT ALREADY EXIST, RETURN FALSE
    public static boolean createAcc(String username, String phone, String pass) throws Exception {
        try {
            Connection con = getConnection();

            //CHECK EXISTENCE OF USERNAME
            PreparedStatement statement = con.prepareStatement("SELECT COUNT(username) AS got FROM apex.user WHERE username = '"+ username +"'  ");
            ResultSet exist = statement.executeQuery();
            int got = 0;
            while (exist.next()) {
                got = exist.getInt("got");
            }
            if (got == 1) {
                System.out.println("\nSorry, your Username is already occupied ");
                return false;
            }

            //CHECK EXISTENCE OF PHONE
            statement = con.prepareStatement("SELECT COUNT(phone) AS got FROM user WHERE phone = '" + phone + "' ");
            exist = statement.executeQuery();
            int got1 = 0;
            while (exist.next()) {
                got1 = exist.getInt("got");
            }
            if (got1 == 1) {
                System.out.println("\nSorry, your Phone is already occupied ");
                return false;
            }

            //INSERT INTO member TABLE
            statement = con.prepareStatement("INSERT INTO apex.user (username,phone,password)VALUES ( '" + username + "', '" + phone + "' , '" + pass + "' )");
            statement.executeUpdate();

            //CREATE student TABLE for each student
            PreparedStatement create = con.prepareStatement("CREATE TABLE "+username+" (number INT NOT NULL AUTO_INCREMENT, ModuleCode VARCHAR(255), ModuleName VARCHAR(255), Activity VARCHAR(255), Tutor VARCHAR(255), Occurrence INT, credithour INT, Week VARCHAR(255), TIME1 INT, TIME2 INT, TIME3 INT, PRIMARY KEY (number));");
            create.executeUpdate();


        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    // A FUNCTION TO LOG IN TO USER ACCOUNT BY CHECKING CREDENTIAL ON MYSQL USER TABLE,
    // IF WRONG USERNAME, RETURN 1, IF WRONG PASSWORD, RETURN 2, IF SUCCESS, RETURN 0
    public static int UserLogin (String Credential, String password) throws Exception {
        Connection con = getConnection();


        //CHECK EXISTENCE OF CREDENTIAL
        PreparedStatement statement = con.prepareStatement("SELECT COUNT(username) AS got FROM apex.user WHERE username = '"+ Credential +"' OR phone = '" + Credential + "'   ");
        ResultSet exist = statement.executeQuery();
        int got = 0;
        while (exist.next()) {
            got = exist.getInt("got");
        }
        if (got == 0) {
            System.out.println("\nSorry, your account does not exists!");
            return 1;
        }

        //CHECK PASSWORD IS COLLECT ACCORDING TO ID AND IDENTITY
        statement = con.prepareStatement("SELECT COUNT(id) AS got FROM user WHERE username = '" + Credential + "' OR phone = '" + Credential + "' AND password = '" + password + "' ");
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

    // A FUNCTION TO LOG IN TO ADMIN ACCOUNT BY CHECKING CREDENTIAL ON MYSQL USER TABLE
    // IF WRONG USERNAME, RETURN 1, IF WRONG PASSWORD, RETURN 2, IF SUCCESS, RETURN 0
    public static int AdminLogin (String Credential, String password) throws Exception {
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

    public static void showModules(ResultSet result) throws Exception{
        try{
            int startTime = 0, endTime = 0, g, j;
            String a, b, c, d, e, f, h, i;
            boolean noTime;

            System.out.printf("%-15s%-67s%-12s%-14s%-12s%-16s%-10s%-12s%-50s%-11s%n","Module Code", "Module Name" , "MAV Name", "Occurrence" ,"Week" ,"Time" ,"Target", "Activity", "Tutor","Credit Hour");
            while(result.next()){
                noTime = false;
                if((result.getInt("TIME3")!=0)){
                    startTime = result.getInt("TIME1");
                    endTime = result.getInt("TIME3")+1;
                }
                else if((result.getInt("TIME2")!=0)){
                    startTime = result.getInt("TIME1");
                    endTime = result.getInt("TIME2")+1;
                }
                else if((result.getInt("TIME1")!=0)){
                    startTime = result.getInt("TIME1");
                    endTime = result.getInt("TIME1")+1;
                }
                else{
                    noTime = true;
                }
                a = result.getString("ModuleCode");
                b = result.getString("ModuleName");
                c = result.getString("MAVName");
                d = result.getString("Occurrence");
                if((result.getString("Week"))==null){
                    e = "-----";
                }
                else{
                    e = result.getString("Week");
                }
                if(noTime){
                    f = "-----";
                }
                else{
                    f = ""+startTime+":00 - "+endTime+":00";
                }
                g = result.getInt("Target");
                h = result.getString("Activity");
                i = result.getString("Tutor");
                j = result.getInt("credithour");
                System.out.printf("%-15s%-67s%-12s%-14s%-12s%-16s%-10d%-12s%-50s%-11d\n",a,b,c,d,e,f,g,h,i,j);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }



}
