/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import entitas.Lahan;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fadli
 */
public class LahanTableModel extends AbstractTableModel{

    private List<Lahan> list;
    private String[] header = {"NO", "Nama", "alamat"};

    public LahanTableModel() {
        list = new ArrayList<>();
    }
    
    public void setListDosen(List<Lahan> list){
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
