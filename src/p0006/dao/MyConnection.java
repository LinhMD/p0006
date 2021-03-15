package p0006.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class MyConnection {
    public static Connection makeConnection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=product_management";
//            String url = "jdbc:sqlserver://localhost:1433;databaseName=p0013";
            return DriverManager.getConnection(url, "sa", "123456");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to make connection to database!!");
            return null;
        }
    }

}
