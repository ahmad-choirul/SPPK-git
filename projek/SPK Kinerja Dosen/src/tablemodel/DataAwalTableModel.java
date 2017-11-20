/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import entitas.KriteriaDosen;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fadli
 */
public class DataAwalTableModel extends AbstractTableModel{

    private List<KriteriaDosen> list;
    private String[] header = {"Nidn", "ID Kriteria", "ID Sub Kriteria"};

    public DataAwalTableModel() {
        list = new ArrayList<>();
    }
    
    public void setListKriteria_dosen(List<KriteriaDosen> list){
        this.list = list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return list.get(rowIndex).getObject(columnIndex);
    }
    
    @Override
    public String getColumnName(int index){
        return header[index];
    }
    
}
