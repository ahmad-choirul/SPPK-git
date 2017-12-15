/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import entitas.KriteriaLahan;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fadli
 */
public class KriteriaLahanTableModel extends AbstractTableModel{

    private List<KriteriaLahan> list;
    private String[] header = {"NO", "Kriteria", "Sub Kriteria"};

    public KriteriaLahanTableModel() {
        list = new ArrayList<>();
    }
    
    public void setListKriteria_lahan(List<KriteriaLahan> list){
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
