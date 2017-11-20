/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.Subkriteria;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.SubkriteriaModel;
import tablemodel.SubkriteriaTableModel;
import view.SubkriteriaView;

/**
 *
 * @author Fadli Hudaya
 */
public class SubkriteriaController {

    private SubkriteriaView subkriteriaView;
    private SubkriteriaModel subkriteriaModel;
    private Subkriteria subkriteria;

    public SubkriteriaController(SubkriteriaView subkriteriaView, SubkriteriaModel subkriteriaModel) {
        this.subkriteriaView = subkriteriaView;
        this.subkriteriaModel = subkriteriaModel;
    }

    public void refreshSubkriteriaTable(String id) {
        subkriteriaView.setSubkriteriaTableModel(new SubkriteriaTableModel());
        subkriteriaView.getSubkriteriaTableModel().setListSubkriteria(subkriteriaModel.getAll(id));
        subkriteriaView.getSubkriteriaTable().setModel(subkriteriaView.getSubkriteriaTableModel());
        subkriteriaView.getSubkriteriaTable().getTableHeader().setFont(new Font("Century Gothic", 0, 14));
        // ResizeColumnUtility.dynamicResize(subkriteriaView.getSubkriteriaTable());
    }

    public void addValueComponent(String id) {
        subkriteria = subkriteriaModel.getSubkriteria(id);
        subkriteriaView.getIdField().setText(subkriteria.getId());
        subkriteriaView.getNamaField().setText(subkriteria.getNama());
        subkriteriaView.getBobotField().setSelectedItem(String.valueOf(subkriteria.getBobot()));
    }

    private Subkriteria createSubkriteria() {
        subkriteria = new Subkriteria(subkriteriaView.getIdField().getText(), 
                subkriteriaView.getId_kriteria(),
                subkriteriaView.getNamaField().getText(), 
                Integer.parseInt(subkriteriaView.getBobotField().getSelectedItem().toString()));
        return subkriteria;
    }

    private boolean isEmptyField() {
        boolean result = true;
        if (subkriteriaView.getNamaField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(subkriteriaView, "Nama Masih Kosong !!!");
        } else if (subkriteriaView.getBobotField().getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(subkriteriaView, "Bobot Masih Kosong !!!");
        } else {
            result = false;
        }

        return result;
    }

    public void saveOrNew() {
        if (!isEmptyField()) {
            if (subkriteriaModel.insert(createSubkriteria())) {
                refreshSubkriteriaTable(subkriteriaView.getId_kriteria());
                resetData();
                JOptionPane.showMessageDialog(subkriteriaView, "Insert Data Subkriteria Sukses.");
            } else {
                JOptionPane.showMessageDialog(subkriteriaView, "Insert Data Subkriteria Gagal !!!");
            }
        }
    }

    public void saveOrUpdate() {
        if (!isEmptyField()) {
            if (subkriteriaModel.update(createSubkriteria())) {
                refreshSubkriteriaTable(subkriteriaView.getId_kriteria());
                resetData();
                JOptionPane.showMessageDialog(subkriteriaView, "Update Data Subkriteria Sukses.");
            } else {
                JOptionPane.showMessageDialog(subkriteriaView, "Update Data Subkriteria Gagal !!!");
            }
        }
    }

    public void saveOrDelete(String id) {
        if (subkriteriaView.getSubkriteriaTable().getSelectedRow() != -1) {
            if (JOptionPane.showConfirmDialog(subkriteriaView, "Anda yakin ingin menghapus data ini?", "Hapus data",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (subkriteriaModel.delete(id)) {
                    resetData();
                    JOptionPane.showMessageDialog(subkriteriaView, "Delete Data Subkriteria Sukses.");
                } else {
                    JOptionPane.showMessageDialog(subkriteriaView, "Delete Data Subkriteria Gagal !!!");
                }
            }
        }
    }

    public String autoNumber() {
        String number = subkriteriaModel.getId();
        if (number.equals("")) {
            number = "SB001";
        } else {
            number = number.substring(2);
            int angka = Integer.parseInt(number);
            angka++;
            if (angka < 10) {
                number = "00" + angka;
            } else if (angka >= 10 && angka < 100) {
                number = "0" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "" + angka;
            } else {
                number = String.valueOf(angka);
            }
            if (number.length() > 5) {
                number = number.substring(number.length() - 5, number.length());
            }
            number = "SB" + number;
        }
        return number;
    }
    
    public void loadNamaKriteria(){
        String[] nama = subkriteriaModel.getNamaKriteria();
        subkriteriaView.getNamaKriteriaField().removeAllItems();
        for (int i = 0; i < nama.length; i++) {
            subkriteriaView.getNamaKriteriaField().addItem(nama[i]);
        }
        subkriteriaView.getNamaKriteriaField().setSelectedIndex(-1);
    }
    
    public void setIdKriteria(){
        String nama = subkriteriaView.getNamaKriteriaField().getSelectedItem().toString();
        String kode = subkriteriaModel.getIdKriteria(nama);
        subkriteriaView.setId_kriteria(kode);
    }

    public void newData() {
        if (subkriteriaView.getBaruButton().getText().equals("Baru")) {
            subkriteriaView.getBaruButton().setText("Batal");
            subkriteriaView.getTambahButton().setEnabled(true);
            subkriteriaView.getUpdateButton().setEnabled(false);
            subkriteriaView.getHapusButton().setEnabled(false);
            subkriteriaView.getIdField().setText(autoNumber());
            subkriteriaView.getNamaField().setEnabled(true);
            subkriteriaView.getNamaField().setText("");
            subkriteriaView.getBobotField().setEnabled(true);
            subkriteriaView.getBobotField().setSelectedIndex(-1);
        } else {
            resetData();
        }
    }

    public void resetData() {
        subkriteriaView.getBaruButton().setText("Baru");
        subkriteriaView.getUpdateButton().setText("Update");
        subkriteriaView.getTambahButton().setEnabled(false);
        subkriteriaView.getUpdateButton().setEnabled(true);
        subkriteriaView.getHapusButton().setEnabled(true);
        subkriteriaView.getIdField().setEnabled(false);
        subkriteriaView.getIdField().setText("");
        subkriteriaView.getNamaField().setEnabled(false);
        subkriteriaView.getNamaField().setText("");
        subkriteriaView.getBobotField().setEnabled(false);
        subkriteriaView.getBobotField().setSelectedIndex(-1);
        refreshSubkriteriaTable(subkriteriaView.getId_kriteria());
    }

    public void updateData() {
        if (subkriteriaView.getUpdateButton().getText().equals("Update")) {
            subkriteriaView.getBaruButton().setText("Batal");
            subkriteriaView.getTambahButton().setEnabled(false);
            subkriteriaView.getUpdateButton().setText("Simpan");
            subkriteriaView.getHapusButton().setEnabled(false);
            subkriteriaView.getIdField().setEnabled(true);
            subkriteriaView.getNamaField().setEnabled(true);
            subkriteriaView.getBobotField().setEnabled(true);
        } else {
            saveOrUpdate();
        }
    }

    public void setAction() {
        subkriteriaView.getSubkriteriaTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (subkriteriaView.getSubkriteriaTable().getSelectedRow() != -1) {
                    subkriteriaView.setId(subkriteriaView.getSubkriteriaTable().getValueAt(
                            subkriteriaView.getSubkriteriaTable().getSelectedRow(), 0).toString());
                    addValueComponent(subkriteriaView.getId());
                }
            }
        });
    }

}
