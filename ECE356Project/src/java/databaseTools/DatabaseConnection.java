/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Bo
 */
public class DatabaseConnection {
//    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
//    public static final String user = "user_b26zhao";
//    public static final String pwd = "user_b26zhao";
    
    //private static final Connection = DriverManager.getConnection(url, user, pwd);
    
    
    
    public static void testConnection()
            throws ClassNotFoundException, SQLException {

        Statement stmt;
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
        stmt = con.createStatement();
        con.close();
    }
}
