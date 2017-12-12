/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.Kriteria;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.KriteriaModel;
import tablemodel.KriteriaTableModel;
import view.KriteriaView;

/**
 *
 * @author Fadli Hudaya
 */
public class KriteriaController {

    private KriteriaView kriteriaView;
    private KriteriaModel kriteriaModel;
    private Kriteria kriteria;

    public KriteriaController(KriteriaView kriteriaView, KriteriaModel kriteriaModel) {
        this.kriteriaView = kriteriaView;
        this.kriteriaModel = kriteriaModel;
    }

    public void refreshKriteriaTable() {
        kriteriaView.setKriteriaTableModel(new KriteriaTableModel());
        kriteriaView.getKriteriaTableModel().setListKriteria(kriteriaModel.getAll());
        kriteriaView.getKriteriaTable().setModel(kriteriaView.getKriteriaTableModel());
        kriteriaView.getKriteriaTable().getTableHeader().setFont(new Font("Century Gothic", 0, 14));
        // ResizeColumnUtility.dynamicResize(kriteriaView.getKriteriaTable());
    }

    public void addValueComponent(String id) {
        kriteria = kriteriaModel.getKriteria(id);
        kriteriaView.getIdField().setText(kriteria.getId());
        kriteriaView.getNamaField().setText(kriteria.getNama());
        kriteriaView.getBobotField().setSelectedItem(String.valueOf(kriteria.getBobot()));
    }

    private Kriteria createKriteria() {
        kriteria = new Kriteria(kriteriaView.getIdField().getText(), kriteriaView.getNamaField().getText(),
                Integer.parseInt(kriteriaView.getBobotField().getSelectedItem().toString()));
        return kriteria;
    }

    private boolean isEmptyField() {
        boolean result = true;
        if (kriteriaView.getNamaField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(kriteriaView, "Nama Masih Kosong !!!");
        } else if (kriteriaView.getBobotField().getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(kriteriaView, "Bobot Masih Kosong !!!");
        } else {
            result = false;
        }

        return result;
    }

    public void saveOrNew() {
        if (!isEmptyField()) {
            if (kriteriaModel.insert(createKriteria())) {
                refreshKriteriaTable();
                resetData();
                JOptionPane.showMessageDialog(kriteriaView, "Insert Data Kriteria Sukses.");
            } else {
                JOptionPane.showMessageDialog(kriteriaView, "Insert Data Kriteria Gagal !!!");
            }
        }
    }

    public void saveOrUpdate() {
        if (!isEmptyField()) {
            if (kriteriaModel.update(createKriteria())) {
                refreshKriteriaTable();
                resetData();
                JOptionPane.showMessageDialog(kriteriaView, "Update Data Kriteria Sukses.");
            } else {
                JOptionPane.showMessageDialog(kriteriaView, "Update Data Kriteria Gagal !!!");
            }
        }
    }

    public void saveOrDelete(String id) {
        if (kriteriaView.getKriteriaTable().getSelectedRow() != -1) {
            if (JOptionPane.showConfirmDialog(kriteriaView, "Anda yakin ingin menghapus data ini?", "Hapus data",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (kriteriaModel.delete(id)) {
                    resetData();
                    JOptionPane.showMessageDialog(kriteriaView, "Delete Data Kriteria Sukses.");
                } else {
                    JOptionPane.showMessageDialog(kriteriaView, "Delete Data Kriteria Gagal !!!");
                }
            }
        }
    }

    public String autoNumber() {
        String number = kriteriaModel.getId();
        if (number.equals("")) {
            number = "KR001";
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
            number = "KR" + number;
        }
        return number;
    }

    public void newData() {
        if (kriteriaView.getBaruButton().getText().equals("Baru")) {
            kriteriaView.getBaruButton().setText("Batal");
            kriteriaView.getTambahButton().setEnabled(true);
            kriteriaView.getUpdateButton().setEnabled(false);
            kriteriaView.getHapusButton().setEnabled(false);
            kriteriaView.getIdField().setText(autoNumber());
            kriteriaView.getNamaField().setEnabled(true);
            kriteriaView.getNamaField().setText("");
            kriteriaView.getBobotField().setEnabled(true);
            kriteriaView.getBobotField().setSelectedIndex(-1);
        } else {
            resetData();
        }
    }

    public void resetData() {
        kriteriaView.getBaruButton().setText("Baru");
        kriteriaView.getUpdateButton().setText("Update");
        kriteriaView.getTambahButton().setEnabled(false);
        kriteriaView.getUpdateButton().setEnabled(true);
        kriteriaView.getHapusButton().setEnabled(true);
        kriteriaView.getIdField().setEnabled(false);
        kriteriaView.getIdField().setText("");
        kriteriaView.getNamaField().setEnabled(false);
        kriteriaView.getNamaField().setText("");
        kriteriaView.getBobotField().setEnabled(false);
        kriteriaView.getBobotField().setSelectedIndex(-1);

        refreshKriteriaTable();
    }

    public void updateData() {
        if (kriteriaView.getUpdateButton().getText().equals("Update")) {
            kriteriaView.getBaruButton().setText("Batal");
            kriteriaView.getTambahButton().setEnabled(false);
            kriteriaView.getUpdateButton().setText("Simpan");
            kriteriaView.getHapusButton().setEnabled(false);
            kriteriaView.getIdField().setEnabled(true);
            kriteriaView.getNamaField().setEnabled(true);
            kriteriaView.getBobotField().setEnabled(true);

        } else {
            saveOrUpdate();
        }
    }

    public void setAction() {
        kriteriaView.getKriteriaTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (kriteriaView.getKriteriaTable().getSelectedRow() != -1) {
                    kriteriaView.setId(kriteriaView.getKriteriaTable().getValueAt(
                            kriteriaView.getKriteriaTable().getSelectedRow(), 0).toString());
                    addValueComponent(kriteriaView.getId());
                }
            }
        });
    }

}
