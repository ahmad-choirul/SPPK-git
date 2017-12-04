/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.KriteriaLahan;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.KriteriaLahanModel;
import tablemodel.KriteriaLahanTableModel;
import view.FormInputKriteria;
import view.KriteriaLahanView;

/**
 *
 * @author Fadli Hudaya
 */
public class KriteriaLahanController {

    private KriteriaLahanView kriteriaDosenView;
    private KriteriaLahanModel kriteriaDosenModel;
    private KriteriaLahan kriteria_dosen;
    private FormInputKriteria formInputKriteria;
    //private FormInputKriteriaDosen formInputKriteriaDosen;
    private JLabel namaLabel = new JLabel();
    private JButton button;
    private JTextField fieldNama;
    private JLabel[] kriteriaLabel;
    private JComboBox[] field;
    private String[] himpunan = new String[]{"Sangat Baik", "Baik", "Cukup", "Buruk", "Sangat Buruk"};

    public KriteriaLahanController(KriteriaLahanView kriteriaDosenView, KriteriaLahanModel kriteriaDosenModel) {
        this.kriteriaDosenView = kriteriaDosenView;
        this.kriteriaDosenModel = kriteriaDosenModel;
    }

    public void setInputForm(KriteriaLahanModel kriteriaDosenModel, FormInputKriteria formInputKriteria) {
        this.kriteriaDosenModel = kriteriaDosenModel;
        this.formInputKriteria = formInputKriteria;
    }

    public void refreshKriteriaDosenTable(String nidn) {
        kriteriaDosenView.setKriteriaDosenTableModel(new KriteriaLahanTableModel());
        kriteriaDosenView.getKriteriaDosenTableModel().setListKriteria_dosen(kriteriaDosenModel.getAll(nidn));
        kriteriaDosenView.getKriteriaDosenTable().setModel(kriteriaDosenView.getKriteriaDosenTableModel());
        kriteriaDosenView.getKriteriaDosenTable().getTableHeader().setFont(new Font("Segoe UI", 0, 14));
        // ResizeColumnUtility.dynamicResize(kriteriaDosenView.getKriteriaDosenTable());
    }

    public void generateField() {
        formInputKriteria.getPanel().removeAll();
        fieldNama = new JTextField(kriteriaDosenView.getNamaDosenField().getSelectedItem().toString());
        fieldNama.setEnabled(false);
        button = new JButton("Simpan");
        kriteriaLabel = new JLabel[kriteriaDosenModel.getNamaKriteria().length];
        field = new JComboBox[kriteriaDosenModel.getNamaKriteria().length];
        namaLabel.setText("Nama Dosen : ");
        namaLabel.setFont(new Font("Segoe UI", 0, 12));
        fieldNama.setFont(new Font("Segoe UI", 0, 12));
        GridLayout layout = new GridLayout(0, 2, 10, 10);
        formInputKriteria.getPanel().setLayout(layout);
        formInputKriteria.getPanel().add(namaLabel);
        formInputKriteria.getPanel().add(fieldNama);
        addAction(button);
        for (int i = 0; i < field.length; i++) {
            kriteriaLabel[i] = new JLabel();
            kriteriaLabel[i].setFont(new Font("Segoe UI", 0, 12));
            kriteriaLabel[i].setText(kriteriaDosenModel.getKriteria()[i]);

            field[i] = new JComboBox();
            field[i].setFont(new Font("Segoe UI", 0, 12));

            for (int j = 0; j < himpunan.length; j++) {
                field[i].addItem(himpunan[j]);
            }

            formInputKriteria.getPanel().add(kriteriaLabel[i]);
            formInputKriteria.getPanel().add(field[i]);
        }
        formInputKriteria.getPanel().add(button);
        formInputKriteria.getPanel().updateUI();
        formInputKriteria.revalidate();
        formInputKriteria.pack();
    }

    private void addAction(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createKriteriaDosen();
            }
        });
    }

    private void createKriteriaDosen() {
        System.out.println(kriteriaLabel.length);
        for (int i = 0; i < kriteriaLabel.length; i++) {
            String nidn = kriteriaDosenModel.getNidn(fieldNama.getText());
            String id_kriteria = kriteriaDosenModel.getIdKriteria(kriteriaLabel[i].getText());
            String id_sub = kriteriaDosenModel.getIdSubKriteria(id_kriteria, field[i].getSelectedItem().toString());
            kriteria_dosen = new KriteriaLahan(nidn, id_kriteria, id_sub);
            kriteriaDosenModel.insert(kriteria_dosen);
        }
        formInputKriteria.dispose();
        //JOptionPane.showMessageDialog(formInputKriteria, "Data Berhasil Di Tambah");
        refreshKriteriaDosenTable(kriteriaDosenView.getNidn());
    }

    public void saveOrDelete() {
        if (kriteriaDosenView.getNamaDosenField().getSelectedIndex() != -1) {
            if (JOptionPane.showConfirmDialog(kriteriaDosenView, "Anda yakin ingin menghapus data ini?", "Hapus data",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (kriteriaDosenModel.delete(kriteriaDosenView.getNidn())) {
                    refreshKriteriaDosenTable(kriteriaDosenView.getNidn());
                    JOptionPane.showMessageDialog(kriteriaDosenView, "Delete Data KriteriaDosen Sukses.");
                } else {
                    JOptionPane.showMessageDialog(kriteriaDosenView, "Delete Data KriteriaDosen Gagal !!!");
                }
            }
        }
    }

    public void loadNamaDosen() {
        String[] nama = kriteriaDosenModel.getNamaDosen();
        kriteriaDosenView.getNamaDosenField().removeAllItems();
        for (int i = 0; i < nama.length; i++) {
            kriteriaDosenView.getNamaDosenField().addItem(nama[i]);
        }
        kriteriaDosenView.getNamaDosenField().setSelectedIndex(-1);
    }

    public void setAction() {
        kriteriaDosenView.getNamaDosenField().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (kriteriaDosenView.getNamaDosenField().getSelectedIndex() != -1) {
                    kriteriaDosenView.setNidn(kriteriaDosenModel.getNidn(kriteriaDosenView.getNamaDosenField().getSelectedItem().toString()));
                    refreshKriteriaDosenTable(kriteriaDosenView.getNidn());
                }
            }
        });
    }

}
