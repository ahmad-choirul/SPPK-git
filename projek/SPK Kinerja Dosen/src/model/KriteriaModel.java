/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entitas.Kriteria;
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
public class KriteriaModel {

    private Connection con; 
    private List<Kriteria> list; 
    
    public KriteriaModel(){
        con = ConnectionUtility.getConnection();
        list = new ArrayList<>();
    }
    
    public List<Kriteria> getAll(){
        try {
            String sql = "SELECT * FROM kriteria";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
            while(resultSet.next()){
                Kriteria kriteria = new Kriteria( resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
                list.add(kriteria);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Retrieve Data\n"+ex);
        }
        return list;
    }

    public Kriteria getKriteria(String id){
        Kriteria kriteria = null;
        try {
            String sql = "SELECT * FROM kriteria WHERE id=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, id);
            ResultSet resultSet = prepare.executeQuery();
            list = new ArrayList<>();
            while(resultSet.next()){
               kriteria = new Kriteria( resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
            }
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
        return kriteria;
    }
    
    public String getId(){
        String id = "";
        String sql = "SELECT id FROM kriteria ORDER BY id DESC";
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

    public boolean insert(Kriteria kriteria){
        String sql = "INSERT INTO kriteria (id, nama, bobot) VALUES (?, ?, ?)";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for(int i=0 ; i<3 ; i++){
                prepare.setObject(i+1, kriteria.getObject(i));
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
    
    public boolean update(Kriteria kriteria){
        String sql = "UPDATE kriteria SET nama=?, bobot=? WHERE id=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for(int i=1 ; i<3 ; i++){
                prepare.setObject(i, kriteria.getObject(i));
                prepare.setObject(3, kriteria.getId());
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
        String sql = "DELETE FROM kriteria WHERE id=?";
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
}
