/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entitas.Dosen;
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
public class DosenModel {

    private Connection con; 
    private List<Dosen> list; 
    
    public DosenModel(){
        con = ConnectionUtility.getConnection();
        list = new ArrayList<>();
    }
    
    public List<Dosen> getAll(){
        try {
            String sql = "SELECT * FROM dosen ORDER BY nidn";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = new ArrayList<>();
            while(resultSet.next()){
                Dosen dosen = new Dosen(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                list.add(dosen);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error When Retrieve Data\n"+ex);
        }
        return list;
    }

    public Dosen getDosen(String nidn){
        Dosen dosen = null;
        try {
            String sql = "SELECT * FROM dosen WHERE nidn=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, nidn);
            ResultSet resultSet = prepare.executeQuery();
            list = new ArrayList<>();
            while(resultSet.next()){
               dosen = new Dosen( resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
        return dosen;
    }
    
    public String getNidn(){
        String nidn = "";
        String sql = "SELECT nidn FROM dosen ORDER BY nidn DESC";
        try{
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                nidn = resultSet.getString(1);
            }
        }catch(SQLException e){
            
        }
        return nidn;
    }

    public boolean insert(Dosen dosen){
        String sql = "INSERT INTO dosen (nidn, nama, jenis_kelamin) VALUES (?, ?, ?)";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for(int i=0 ; i<3 ; i++){
                prepare.setObject(i+1, dosen.getObject(i));
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
    
    public boolean update(Dosen dosen){
        String sql = "UPDATE dosen SET nama=?, jenis_kelamin=? WHERE nidn=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            for(int i=1 ; i<3 ; i++){
                prepare.setObject(i, dosen.getObject(i));
                prepare.setObject(3, dosen.getNidn());
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
    
    public boolean delete(String nidn){
        String sql = "DELETE FROM dosen WHERE nidn=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            prepare.setString(1, nidn);
            prepare.executeUpdate();
            deleteKriteria(nidn);
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
    
    public boolean deleteKriteria(String nidn){
        String sql = "DELETE FROM kriteria_dosen WHERE nidn=?";
        PreparedStatement prepare = null;
        try {
            prepare = con.prepareStatement(sql);
            con.setAutoCommit(false);
            prepare.setString(1, nidn);
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
