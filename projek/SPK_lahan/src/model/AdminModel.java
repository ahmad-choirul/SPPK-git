/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entitas.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import utility.ConnectionUtility;

/**
 *
 * @author Fadli
 */
public class AdminModel {

    private Connection con; 
    private List<Admin> list; 
    
    public AdminModel(){
        con = ConnectionUtility.getConnection();
        list = new ArrayList<>();
    }
    
    public List<Admin> getAll(){
        try {
            String sql = "SELECT * FROM admin";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
            while(resultSet.next()){
                Admin admin = new Admin( resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                list.add(admin);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Retrieve Data\n"+ex);
        }
        return list;
    }

    public Admin getAdmin(String id){
        Admin admin = null;
        try {
            String sql = "SELECT * FROM admin WHERE id=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, id);
            ResultSet resultSet = prepare.executeQuery();
            list = new ArrayList<>();
            while(resultSet.next()){
               admin = new Admin( resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
        return admin;
    }
    
    public String getId(){
        String id = "";
        String sql = "SELECT id FROM admin ORDER BY id DESC";
        try{
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                id = resultSet.getString(1);
            }
        }catch(SQLException e){
            
        }
        return id;
    }

    public boolean insert(Admin admin){
        String sql = "INSERT INTO admin (id, username, password) VALUES (?, ?, ?)";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for(int i=0 ; i<3 ; i++){
                prepare.setObject(i+1, admin.getObject(i));
            }
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Insert Data\n"+ex);
            try {
                con.rollback();
                prepare.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n"+ex1);
            }
            return false;
        }
        return true;
    }
    
    public boolean update(Admin admin){
        String sql = "UPDATE admin SET username=?, password=? WHERE id=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for(int i=1 ; i<3 ; i++){
                prepare.setObject(i, admin.getObject(i));
                prepare.setObject(3, admin.getId());
            }
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Update Data\n"+ex);
            try {
                con.rollback();
                prepare.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n"+ex1);
            }
            return false;
        }
        return true;
    }
    
    public boolean delete(String id){
        String sql = "DELETE FROM admin WHERE id=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            prepare.setString(1, id);
            prepare.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Delete Data\n"+ex);
            try {
                con.rollback();
                prepare.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error When Rollback Connection\n"+ex1);
            }
            return false;
        }
        return true;
    }
    
    public Admin login(String username){
        Admin user = null;
        String sql = "SELECT * FROM admin WHERE username = ?";
        try {
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, username);
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                user = new Admin(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException e) {
            
        }
        return user;
    }
}
