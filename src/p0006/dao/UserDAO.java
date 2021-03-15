/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p0006.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Vector;


/**
 *
 * @author USER
 */
public class UserDAO {
    public boolean login(String userid, String password) throws Exception{
        String sql = "SELECT u.userID, u.password, u.status\n" +
                        "FROM tbl_users u\n" +
                        "WHERE u.userID = ? AND u.password = ? AND u.status = ?";
        Vector<Vector<String>> vectors = SQLQuery.executeQuery(sql, userid, password, 1);
        Vector<String> user = vectors.get(0);
        return user.get(0).equals(userid) && user.get(1).equals(password);
    }
    
    public String getUserName(String userid) throws Exception{
        String sql = "SELECT u.full_name\n" 
                + "FROM tbl_users u\n"
                + "WHERE u.userID = ?";
        Vector<Vector<String>> results = SQLQuery.executeQuery(sql, userid);
        return results.get(0).get(0);
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            System.out.println(new UserDAO().login("linhmd", "spirit1233"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
