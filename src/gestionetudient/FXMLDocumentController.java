/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionetudient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static gestionetudient.MysqlConnect.ConnectedDb;

/**
 *
 * @author Hamza
 */
public class FXMLDocumentController implements Initializable {
    
    /*@FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }*/
    
        @FXML
    private TableView<Users> table_users;

    @FXML
    private TableColumn<Users, Integer> col_id;

    @FXML
    private TableColumn<Users, String> col_nom;

    @FXML
    private TableColumn<Users, String> col_prenom;

    @FXML
    private TableColumn<Users, String> col_email;

    @FXML
    private TableColumn<Users, String> col_filiere;
    
    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_filiere;

    @FXML
    private TextField txt_id;
    
    ObservableList<Users> listM;
    
    int index = -1;
    
    Connection conn = null;
    ResultSet rs=null;
    PreparedStatement pst = null;
    
    
    public static ObservableList<Users> getDataUsers(){
         
        Connection conn = ConnectedDb();
        ObservableList<Users> list = FXCollections.observableArrayList();
        
        try {
            com.mysql.jdbc.PreparedStatement ps =(com.mysql.jdbc.PreparedStatement) conn.prepareStatement("Select * from users");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()){
                
                list.add(new Users(Integer.parseInt(rs.getString("user_id")), rs.getString("user_nom"), rs.getString("user_prenom"), rs.getString("user_email"), rs.getString("user_filiere")));
            }
          
            
            
        } catch (Exception e) {
        }
        
        return list;
    }
    
    
    
    
    
    public void Add_users(){
        
        conn = MysqlConnect.ConnectedDb();
        String sql = "insert into users (user_nom,user_prenom,user_email,user_filiere)values(?,?,?,?)";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_nom.getText());
            pst.setString(2, txt_prenom.getText());
            pst.setString(3, txt_email.getText());
            pst.setString(4, txt_filiere.getText());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Users Add Succes");
            updateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "nnnnn");
        }
    }
    
    ///// Select User ////////
    @FXML
    public void getselected(){
        //table_users.setOnMouseClicked(e->{
        
        index = table_users.getSelectionModel().getSelectedIndex();
        
        if(index <= -1){
            return;
        }
        
        txt_id.setText(col_id.getCellData(index).toString());
        txt_nom.setText(col_nom.getCellData(index).toString());
        txt_prenom.setText(col_prenom.getCellData(index).toString());
        txt_email.setText(col_email.getCellData(index).toString());
        txt_filiere.setText(col_filiere.getCellData(index).toString());
       //}); 
    }
    
    //// Edit ////////////
    
    public void update(){
        
        try {
            conn = MysqlConnect.ConnectedDb();
            String value1 = txt_id.getText();
            String value2 = txt_nom.getText();
            String value3 = txt_prenom.getText();
            String value4 = txt_email.getText();
            String value5 = txt_filiere.getText();
            
            String sql ="update users set user_id = '"+value1+"', user_nom = '"+value2+"', user_prenom= '"+value3+"',"
                    + " user_email = '"+value4+"', user_email = '"+value5+"' where user_id='"+value1+"' ";
            
            pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Updated");
            updateTable();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void delete(){
        conn = MysqlConnect.ConnectedDb();
        String sql = "delete from users where user_id=?";
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            updateTable();
            
        } catch (Exception e) {
            return;
        }
        
    }
    
    
    

    public void updateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Users,Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Users,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<Users,String>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
        col_filiere.setCellValueFactory(new PropertyValueFactory<Users,String>("filiere"));
    
        listM = getDataUsers();
        table_users.setItems(getDataUsers());
        getselected();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        updateTable();
        
    }    
    
}
