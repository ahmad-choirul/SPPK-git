/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.Lahan;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.LahanModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.LahanTableModel;
import utility.ConnectionUtility;
import view.LahanView;

/**
 *
 * @author Fadli Hudaya
 */
public class LahanController {

    private LahanView LahanView;
    private LahanModel lahanmodel;
    private Lahan lahan;

    public LahanController(LahanView lahanView, LahanModel lahanModel) {
        this.LahanView = lahanView;
        this.lahanmodel = lahanModel;
    }

    public void refreshlahanTable() {
        LahanView.setLahanTableModel(new LahanTableModel());
        LahanView.getlahanTableModel().setListlahan(lahanmodel.getAll());
        LahanView.getLahanTable().setModel(LahanView.getlahanTableModel());
        LahanView.getLahanTable().getTableHeader().setFont(new Font("Segoe UI", 0, 14));
        // ResizeColumnUtility.dynamicResize(lahanView.getlahanTable());
    }

    public void addValueComponent(String no) {
        lahan = lahanmodel.getlahan(no);
        LahanView.getNoField().setText(lahan.getNo());
        LahanView.getNamaField().setText(lahan.getNama());
        LahanView.getalamtField().setText(lahan.getalamat());
    }

    private Lahan createlahan() {
        lahan = new Lahan(LahanView.getNoField().getText(), LahanView.getNamaField().getText(), 
                LahanView.getalamtField().getText());
        return lahan;
    }

    private boolean isEmptyField() {
        boolean result = true;
        if (LahanView.getNamaField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(LahanView, "Nama Masih Kosong !!!");
        } else if (LahanView.getalamtField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(LahanView, "Tempat lahir Masih Kosong !!!");
        } else {
            result = false;
        }
        return result;
    }

    public void saveOrNew() {
        if (!isEmptyField()) {
            if (lahanmodel.insert(createlahan())) {
                refreshlahanTable();
                resetData();
                JOptionPane.showMessageDialog(LahanView, "Insert Data lahan Sukses.");
            } else {
                JOptionPane.showMessageDialog(LahanView, "Insert Data lahan Gagal !!!");
            }
        }
    }
    
    public String autoNumber() {
        String number = lahanmodel.getno();
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
            if (lahanmodel.update(createlahan())) {
                refreshlahanTable();
                resetData();
                JOptionPane.showMessageDialog(LahanView, "Update Data lahan Sukses.");
            } else {
                JOptionPane.showMessageDialog(LahanView, "Update Data lahan Gagal !!!");
            }
        }
    }

    public void saveOrDelete(String no) {
        if (LahanView.getLahanTable().getSelectedRow() != -1) {
            if (JOptionPane.showConfirmDialog(LahanView, "Anda yakin ingin menghapus data ini?", "Hapus data",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (lahanmodel.delete(no)) {
                    resetData();
                    JOptionPane.showMessageDialog(LahanView, "Delete Data lahan Sukses.");
                } else {
                    JOptionPane.showMessageDialog(LahanView, "Delete Data lahan Gagal !!!");
                }
            }
        }
    }

    public void newData() {
        if (LahanView.getBaruButton().getText().equals("Baru")) {
            LahanView.getBaruButton().setText("Batal");
            LahanView.getTambahButton().setEnabled(true);
            LahanView.getUpdateButton().setEnabled(false);
            LahanView.getHapusButton().setEnabled(false);
            LahanView.getNoField().setEnabled(false);
            LahanView.getNoField().setText(autoNumber());
            LahanView.getNamaField().setEnabled(true);
            LahanView.getNamaField().setText("");
            LahanView.getalamtField().setEnabled(true);
            LahanView.getalamtField().setText("");
        } else {
            resetData();
        }
    }

    public void resetData() {
        LahanView.getBaruButton().setText("Baru");
        LahanView.getUpdateButton().setText("Update");
        LahanView.getTambahButton().setEnabled(false);
        LahanView.getUpdateButton().setEnabled(true);
        LahanView.getHapusButton().setEnabled(true);
        LahanView.getNoField().setEnabled(false);
        LahanView.getNoField().setText("");
        LahanView.getNamaField().setEnabled(false);
        LahanView.getNamaField().setText("");
        LahanView.getalamtField().setEnabled(false);
        LahanView.getalamtField().setText("");
        refreshlahanTable();
    }

    public void updateData() {
        if (LahanView.getUpdateButton().getText().equals("Update")) {
            LahanView.getBaruButton().setText("Batal");
            LahanView.getTambahButton().setEnabled(false);
            LahanView.getUpdateButton().setText("Simpan");
            LahanView.getHapusButton().setEnabled(false);
            LahanView.getNoField().setEnabled(true);
            LahanView.getNamaField().setEnabled(true);
            LahanView.getalamtField().setEnabled(true);
        } else {
            saveOrUpdate();
        }
    }

    public void setAction() {
        LahanView.getLahanTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (LahanView.getLahanTable().getSelectedRow() != -1) {
                    LahanView.setno(LahanView.getLahanTable().getValueAt(
                            LahanView.getLahanTable().getSelectedRow(), 0).toString());
                    addValueComponent(LahanView.getno());
                }
            }
        });
    }
    
    public void getReport() {
        InputStream stream;
        Map<String, Object> map;
        stream = getClass().getResourceAsStream("report/Laporan lahan.jasper");
        map = new HashMap<>();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, map, ConnectionUtility.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            //Logger.getLogger(KonsultasiController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
