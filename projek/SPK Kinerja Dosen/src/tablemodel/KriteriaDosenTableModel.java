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
public class KriteriaDosenTableModel extends AbstractTableModel{

    private List<KriteriaDosen> list;
    private String[] header = {"NIDN", "Kriteria", "Sub Kriteria"};

    public KriteriaDosenTableModel() {
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
        return list.get(rowIndex).getObjects(columnIndex);
    }
    
    @Override
    public String getColumnName(int index){
        return header[index];
    }
    
}
