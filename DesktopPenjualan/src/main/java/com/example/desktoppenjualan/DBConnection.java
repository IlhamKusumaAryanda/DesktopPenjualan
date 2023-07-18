package com.example.desktoppenjualan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    static String user="root";
    static String pass="";
    static String url = "jdbc:mysql://localhost:3306/javacrud";
    static String driver="com.mysql.cj.jdbc.Driver";
    public static Connection getConn(){
        try{
            Class.forName(driver);
            return DriverManager.getConnection(url,user,pass);
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException();
        }
    }
}
