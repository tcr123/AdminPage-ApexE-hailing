package com.example.ehailingapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class AdminMain {
    public static void main(String[] args) {
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/maya2";
            String username = "root";
            String password = "jack55092080";
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
