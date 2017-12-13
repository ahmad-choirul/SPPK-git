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

    private KriteriaLahanView Kriterialahanview;
    private KriteriaLahanModel kriterialahanmodel;
    private KriteriaLahan kriteria_dosen;
    private FormInputKriteria formInputKriteria;
    //private FormInputKriteriaDosen formInputKriteriaDosen;
    private JLabel namaLabel = new JLabel();
    private JButton button;
    private JTextField fieldNama;
    private JLabel[] kriteriaLabel;
    private JComboBox[] field;
    private String[] himpunan = new String[5];

    public KriteriaLahanController(KriteriaLahanView kriteriaDosenView, KriteriaLahanModel kriteriaDosenModel) {
        this.Kriterialahanview = kriteriaDosenView;
        this.kriterialahanmodel = kriteriaDosenModel;
        setkriteria();
    }

    public void setInputForm(KriteriaLahanModel kriteriaDosenModel, FormInputKriteria formInputKriteria) {
        this.kriterialahanmodel = kriteriaDosenModel;
        this.formInputKriteria = formInputKriteria;
    }

    public void refreshKriteriaLahanTable(String no) {
        Kriterialahanview.setKriteriaDosenTableModel(new KriteriaLahanTableModel());
        Kriterialahanview.getKriteriaDosenTableModel().setListKriteria_dosen(kriterialahanmodel.getAll(no));
        Kriterialahanview.getKriteriaDosenTable().setModel(Kriterialahanview.getKriteriaDosenTableModel());
        Kriterialahanview.getKriteriaDosenTable().getTableHeader().setFont(new Font("Segoe UI", 0, 14));
        // ResizeColumnUtility.dynamicResize(kriteriaDosenView.getKriteriaDosenTable());
    }

    public void generateField() {
        formInputKriteria.getPanel().removeAll();
        fieldNama = new JTextField(Kriterialahanview.getNamaLahanField().getSelectedItem().toString());
        fieldNama.setEnabled(false);
        button = new JButton("Simpan");
        
        namaLabel.setText("Nama Lahan : ");
        namaLabel.setFont(new Font("Segoe UI", 0, 12));
        fieldNama.setFont(new Font("Segoe UI", 0, 12));
        GridLayout layout = new GridLayout(0, 2, 10, 10);
        formInputKriteria.getPanel().setLayout(layout);
        formInputKriteria.getPanel().add(namaLabel);
        formInputKriteria.getPanel().add(fieldNama);
        addAction(button);
        for (int i = 0; i < kriteriaLabel.length; i++) {
            formInputKriteria.getPanel().add(kriteriaLabel[i]);
            formInputKriteria.getPanel().add(field[i]);
        }

        formInputKriteria.getPanel().add(button);
        formInputKriteria.getPanel().updateUI();
        formInputKriteria.revalidate();
        formInputKriteria.pack();
    }

    public void setkriteria() {
        kriteriaLabel = new JLabel[kriterialahanmodel.getNamaKriteria().length];
        field = new JComboBox[kriterialahanmodel.getNamaKriteria().length];
        for (int i = 0; i < field.length; i++) {
            kriteriaLabel[i] = new JLabel();
            kriteriaLabel[i].setFont(new Font("Segoe UI", 0, 12));
            kriteriaLabel[i].setText(kriterialahanmodel.getKriteria()[i]);
            field[i] = new JComboBox();
            field[i].setFont(new Font("Segoe UI", 0, 12));
            himpunan = kriterialahanmodel.getsubkriteria(i +1);
            for (int j = 0; j < himpunan.length; j++) {
                field[i].addItem(himpunan[j]);
                }
        }
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
            String no = kriterialahanmodel.getno(fieldNama.getText());
            System.out.println("kriteria label = " + kriteriaLabel[i].getText());
            String id_kriteria = kriterialahanmodel.getIdKriteria(kriteriaLabel[i].getText());
            String id_sub = kriterialahanmodel.getIdSubKriteria(id_kriteria, field[i].getSelectedItem().toString());
            kriteria_dosen = new KriteriaLahan(no, id_kriteria, id_sub);
            kriterialahanmodel.insert(kriteria_dosen);
        }
        formInputKriteria.dispose();
        //JOptionPane.showMessageDialog(formInputKriteria, "Data Berhasil Di Tambah");
        refreshKriteriaLahanTable(Kriterialahanview.getNo());
    }

    public void saveOrDelete() {
        if (Kriterialahanview.getNamaLahanField().getSelectedIndex() != -1) {
            if (JOptionPane.showConfirmDialog(Kriterialahanview, "Anda yakin ingin menghapus data ini?", "Hapus data",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (kriterialahanmodel.delete(Kriterialahanview.getNo())) {
                    refreshKriteriaLahanTable(Kriterialahanview.getNo());
                    JOptionPane.showMessageDialog(Kriterialahanview, "Delete Data KriteriaLahan Sukses.");
                } else {
                    JOptionPane.showMessageDialog(Kriterialahanview, "Delete Data KriteriaLahan Gagal !!!");
                }
            }
        }
    }

    public void loadNamaDosen() {
        String[] nama = kriterialahanmodel.getNamaLahan();
        Kriterialahanview.getNamaLahanField().removeAllItems();
        for (int i = 0; i < nama.length; i++) {
            Kriterialahanview.getNamaLahanField().addItem(nama[i]);
        }
        Kriterialahanview.getNamaLahanField().setSelectedIndex(-1);
    }

    public void setAction() {
        Kriterialahanview.getNamaLahanField().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (Kriterialahanview.getNamaLahanField().getSelectedIndex() != -1) {
                    Kriterialahanview.setno(kriterialahanmodel.getno(Kriterialahanview.getNamaLahanField().getSelectedItem().toString()));
                    refreshKriteriaLahanTable(Kriterialahanview.getNo());
                }
            }
        });
    }

}
