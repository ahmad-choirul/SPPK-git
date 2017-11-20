/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.Dosen;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.DosenModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.DosenTableModel;
import utility.ConnectionUtility;
import view.DosenView;

/**
 *
 * @author Fadli Hudaya
 */
public class DosenController {

    private DosenView dosenView;
    private DosenModel dosenModel;
    private Dosen dosen;

    public DosenController(DosenView dosenView, DosenModel dosenModel) {
        this.dosenView = dosenView;
        this.dosenModel = dosenModel;
    }

    public void refreshDosenTable() {
        dosenView.setDosenTableModel(new DosenTableModel());
        dosenView.getDosenTableModel().setListDosen(dosenModel.getAll());
        dosenView.getDosenTable().setModel(dosenView.getDosenTableModel());
        dosenView.getDosenTable().getTableHeader().setFont(new Font("Segoe UI", 0, 14));
        // ResizeColumnUtility.dynamicResize(dosenView.getDosenTable());
    }

    public void addValueComponent(String nidn) {
        dosen = dosenModel.getDosen(nidn);
        dosenView.getNidnField().setText(dosen.getNidn());
        dosenView.getNamaField().setText(dosen.getNama());
        dosenView.getJenisKelaminField().setSelectedItem(dosen.getJenisKelamin());
    }

    private Dosen createDosen() {
        dosen = new Dosen(dosenView.getNidnField().getText(), dosenView.getNamaField().getText(), 
                dosenView.getJenisKelaminField().getSelectedItem().toString());
        return dosen;
    }

    private boolean isEmptyField() {
        boolean result = true;
        if (dosenView.getNamaField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(dosenView, "Nama Masih Kosong !!!");
        } else if (dosenView.getJenisKelaminField().getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(dosenView, "Tempat lahir Masih Kosong !!!");
        } else {
            result = false;
        }
        return result;
    }

    public void saveOrNew() {
        if (!isEmptyField()) {
            if (dosenModel.insert(createDosen())) {
                refreshDosenTable();
                resetData();
                JOptionPane.showMessageDialog(dosenView, "Insert Data Dosen Sukses.");
            } else {
                JOptionPane.showMessageDialog(dosenView, "Insert Data Dosen Gagal !!!");
            }
        }
    }
    
    public String autoNumber() {
        String number = dosenModel.getNidn();
        if (number.equals("")) {
            number = "0000000001";
        } else {
            //number = number.substring(2);
            int angka = Integer.parseInt(number);
            angka++;
            if (angka < 10) {
                number = "000000000" + angka;
            } else if (angka >= 10 && angka < 100) {
                number = "00000000" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "0000000" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "000000" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "00000" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "0000" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "000" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "00" + angka;
            } else if (angka >= 100 && angka < 1000) {
                number = "0" + angka;
            } else {
                number = String.valueOf(angka);
            }
            if (number.length() > 10) {
                number = number.substring(number.length() - 10, number.length());
            }
            number = "" + number;
        }
        return number;
    }

    public void saveOrUpdate() {
        if (!isEmptyField()) {
            if (dosenModel.update(createDosen())) {
                refreshDosenTable();
                resetData();
                JOptionPane.showMessageDialog(dosenView, "Update Data Dosen Sukses.");
            } else {
                JOptionPane.showMessageDialog(dosenView, "Update Data Dosen Gagal !!!");
            }
        }
    }

    public void saveOrDelete(String nidn) {
        if (dosenView.getDosenTable().getSelectedRow() != -1) {
            if (JOptionPane.showConfirmDialog(dosenView, "Anda yakin ingin menghapus data ini?", "Hapus data",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (dosenModel.delete(nidn)) {
                    resetData();
                    JOptionPane.showMessageDialog(dosenView, "Delete Data Dosen Sukses.");
                } else {
                    JOptionPane.showMessageDialog(dosenView, "Delete Data Dosen Gagal !!!");
                }
            }
        }
    }

    public void newData() {
        if (dosenView.getBaruButton().getText().equals("Baru")) {
            dosenView.getBaruButton().setText("Batal");
            dosenView.getTambahButton().setEnabled(true);
            dosenView.getUpdateButton().setEnabled(false);
            dosenView.getHapusButton().setEnabled(false);
            dosenView.getNidnField().setEnabled(false);
            dosenView.getNidnField().setText(autoNumber());
            dosenView.getNamaField().setEnabled(true);
            dosenView.getNamaField().setText("");
            dosenView.getJenisKelaminField().setEnabled(true);
            dosenView.getJenisKelaminField().setSelectedIndex(-1);
        } else {
            resetData();
        }
    }

    public void resetData() {
        dosenView.getBaruButton().setText("Baru");
        dosenView.getUpdateButton().setText("Update");
        dosenView.getTambahButton().setEnabled(false);
        dosenView.getUpdateButton().setEnabled(true);
        dosenView.getHapusButton().setEnabled(true);
        dosenView.getNidnField().setEnabled(false);
        dosenView.getNidnField().setText("");
        dosenView.getNamaField().setEnabled(false);
        dosenView.getNamaField().setText("");
        dosenView.getJenisKelaminField().setEnabled(false);
        dosenView.getJenisKelaminField().setSelectedItem(-1);
        refreshDosenTable();
    }

    public void updateData() {
        if (dosenView.getUpdateButton().getText().equals("Update")) {
            dosenView.getBaruButton().setText("Batal");
            dosenView.getTambahButton().setEnabled(false);
            dosenView.getUpdateButton().setText("Simpan");
            dosenView.getHapusButton().setEnabled(false);
            dosenView.getNidnField().setEnabled(true);
            dosenView.getNamaField().setEnabled(true);
            dosenView.getJenisKelaminField().setEnabled(true);
        } else {
            saveOrUpdate();
        }
    }

    public void setAction() {
        dosenView.getDosenTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (dosenView.getDosenTable().getSelectedRow() != -1) {
                    dosenView.setNidn(dosenView.getDosenTable().getValueAt(
                            dosenView.getDosenTable().getSelectedRow(), 0).toString());
                    addValueComponent(dosenView.getNidn());
                }
            }
        });
    }
    
    public void getReport() {
        InputStream stream;
        Map<String, Object> map;
        stream = getClass().getResourceAsStream("report/Laporan Dosen.jasper");
        map = new HashMap<>();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, map, ConnectionUtility.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            //Logger.getLogger(KonsultasiController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
