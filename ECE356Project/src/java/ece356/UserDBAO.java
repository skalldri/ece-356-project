package ece356;

import java.sql.*;

public class UserDBAO {
    public static final String url = "jdbc:mysql://autom8ed.com:3306/";
    //public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    public static final String user = "hospital_db";
    public static final String pwd = "ece356";
    
    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Statement stmt;
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, pwd);
        stmt = con.createStatement();
        con.close();
    }
}
