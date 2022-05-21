package com.example.ehailingapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class AdminMain {
    public static void main(String[] args) {
        Connection conn;
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/maya2";
            String username = ""; //username
            String password = ""; //password
            Class.forName(driver);

            conn = DriverManager.getConnection(url,username,password);
            //System.out.println("Connected");
        }catch(Exception e){
            System.out.println(e);
        }
        AdminLoginPage.accessAdminLoginPage();
    }
}
