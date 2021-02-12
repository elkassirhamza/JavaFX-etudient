/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionetudient;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hamza
 */
public class MysqlConnect {
    private static String USERNAME = "root";
    private static String PASSWORD ;
    private static final String URL = "jdbc:mysql://localhost/gestionetudient";
    Connection conn = null;
    
    public static Connection ConnectedDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
           Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionetudient");
            //JOptionPane.showConfirmDialog(null, "connectionEstablished");
            return conn;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
            
        }
    }
    
    /*
    public static ObservableList<Users> getDataUsers(){
         
        Connection conn = ConnectedDb();
        ObservableList<Users> list = FXCollections.observableArrayList();
        
        try {
            PreparedStatement ps =(PreparedStatement) conn.prepareStatement("Select * from users");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                
                list.add(new Users(Integer.parseInt(rs.getString("user_id")), rs.getString("user_nom"), rs.getString("user_prenom"), rs.getString("user_email"), rs.getString("user_filiere")));
            }
          
            
            
        } catch (Exception e) {
        }
        
        return null;
    }
*/
    
    
}
