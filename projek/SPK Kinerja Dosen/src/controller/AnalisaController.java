/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.KriteriaDosen;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KriteriaDosenModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.DataAwalTableModel;
import utility.ConnectionUtility;
import view.AnalisaView;

/**
 *
 * @author Fadli Hudaya
 */
public class AnalisaController {

    private AnalisaView analisaView;
    private KriteriaDosenModel kriteriaDosenModel;
    private KriteriaDosen kriteriaDosen;

    public AnalisaController(AnalisaView analisaView, KriteriaDosenModel kriteriaDosenModel) {
        this.analisaView = analisaView;
        this.kriteriaDosenModel = kriteriaDosenModel;
    }

    public void refreshTable() {
        analisaView.setDataAwalTableModel(new DataAwalTableModel());
        analisaView.getDataAwalTableModel().setListKriteria_dosen(kriteriaDosenModel.getAll());
        analisaView.getDataAwalTable().setModel(analisaView.getDataAwalTableModel());
        analisaView.getDataAwalTable().getTableHeader().setFont(new Font("Segoe UI", 0, 14));
    }

    private String[] getKriteria() {
        return kriteriaDosenModel.getIdKriteria();
    }

    private int getJumlahDosen() {
        return kriteriaDosenModel.getNamaDosen().length;
    }

    private String[] getNamaDosen() {
        return kriteriaDosenModel.getNamaDosen();
    }

    private int getNilai(String himpunan) {
        Integer nilai = kriteriaDosenModel.getNilai(himpunan);
        return nilai;
    }

    public double getR11(Double value, Double[] nilai) {
        Double max = getMax(nilai);
        Double hasil = value / max;
        return hasil;
    }

    public double getR12(Double value, Double[] nilai) {
        Double min = getMin(nilai);
        Double hasil = min / value;
        return hasil;
    }

    public double getMax(Double[] nilai) {
        Double max = 0.0;
        for (int i = 0; i < nilai.length; i++) {
            if (i == 0) {
                max = Math.max(nilai[i], nilai[i + 1]);
            } else if (i == nilai.length - 1) {
                break;
            } else {
                max = Math.max(max, nilai[i + 1]);
            }
        }
        return max;
    }

    public double getMin(Double[] nilai) {
        Double min = 0.0;
        for (int i = 0; i < nilai.length; i++) {
            if (i == 0) {
                min = Math.min(nilai[i], nilai[i + 1]);
            } else if (i == nilai.length - 1) {
                break;
            } else {
                min = Math.min(min, nilai[i + 1]);
            }
        }
        return min;
    }

    public void getMatriksKeputusan() {
        Object[][] data = new Object[getJumlahDosen()][getKriteria().length];
        int a = 0;
        for (int i = 0; i < getJumlahDosen(); i++) {
            for (int j = 0; j < getKriteria().length; j++) {
                String id_subkriteria = analisaView.getDataAwalTable().getValueAt(a, 2).toString();
                data[i][j] = getNilai(id_subkriteria);
                a++;
            }
        }
        DefaultTableModel df = new DefaultTableModel(data, getKriteria());
        analisaView.getmKeputusanTable().setModel(df);
    }

    public void getMatrikNormalisasi() {
        Object[][] data = new Object[getJumlahDosen()][getKriteria().length];
        Double[] pembagi = new Double[getKriteria().length];
        Integer[] bobotKriteria = new Integer[getKriteria().length];
        for (int i = 0; i < getKriteria().length; i++) {
            bobotKriteria[i] = kriteriaDosenModel.getBobot(analisaView.getmKeputusanTable().getColumnName(i).toString());
            Double nilaiTambah = 0.0;
            for (int j = 0; j < getJumlahDosen(); j++) {
                Double nilai = Double.parseDouble(analisaView.getmKeputusanTable().getValueAt(j, i).toString());
                nilai = Math.pow(nilai, 2);
                nilaiTambah += nilai;
                pembagi[i] = nilaiTambah;
            }
            pembagi[i] = Math.sqrt(pembagi[i]);
        }

        for (int i = 0; i < getJumlahDosen(); i++) {
            for (int j = 0; j < getKriteria().length; j++) {
                Integer nilai = Integer.valueOf(analisaView.getmKeputusanTable().getValueAt(i, j).toString());
                data[i][j] = (nilai / pembagi[j]) * bobotKriteria[j];
            }
        }
        DefaultTableModel df = new DefaultTableModel(data, getKriteria());
        analisaView.getmNormalisasiTable().setModel(df);
    }

    public void getSolusiIdeal() {
        Object[][] dataPositif = new Object[getJumlahDosen()][getKriteria().length];
        Object[][] dataNegatif = new Object[getJumlahDosen()][getKriteria().length];
        Double[] max = new Double[getKriteria().length];
        Double[] min = new Double[getKriteria().length];
        for (int i = 0; i < getKriteria().length; i++) {
            Double tempPositif = 0.0;
            Double tempNegatif = 0.0;
            for (int j = 0; j < getJumlahDosen(); j++) {
                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
                if (nilai > tempPositif) {
                    tempPositif = nilai;
                    max[i] = nilai;
                }
                if (j == 0) {
                    min[i] = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
                } else {
                    if (nilai < min[i]) {
                        min[i] = nilai;
                    }
                }
            }
        }

        for (int i = 0; i < getJumlahDosen(); i++) {
            for (int j = 0; j < getKriteria().length; j++) {
                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(i, j).toString());
                //System.out.println(nilai);
                dataPositif[i][j] = Math.pow((nilai - max[j]), 2);
                dataNegatif[i][j] = Math.pow((nilai - min[j]), 2);
            }
        }

        DefaultTableModel dfPositif = new DefaultTableModel(dataPositif, getKriteria());
        DefaultTableModel dfNegatif = new DefaultTableModel(dataNegatif, getKriteria());

        analisaView.getSIPositifTable().setModel(dfPositif);
        analisaView.getSINegatifTable().setModel(dfNegatif);
    }

    public void getHasil() {
        Object[][] data = new Object[getJumlahDosen()][3];
        Double nilaiDosen[] = new Double[getJumlahDosen()];

        for (int i = 0; i < getJumlahDosen(); i++) {
            Double totalPositif = 0.0;
            Double totalNegatif = 0.0;
            for (int j = 0; j < getKriteria().length; j++) {
                totalPositif += Double.valueOf(analisaView.getSIPositifTable().getValueAt(i, j).toString());
                totalNegatif += Double.valueOf(analisaView.getSINegatifTable().getValueAt(i, j).toString());
            }
            totalPositif = Math.sqrt(totalPositif);
            totalNegatif = Math.sqrt(totalNegatif);
            Double hasil = totalNegatif / (totalPositif + totalNegatif);
            nilaiDosen[i] = hasil;
            //System.out.println(totalPositif + " / (" + totalPositif + " + " + totalNegatif + " = " + hasil);
        }

        for (int i = 0; i < getJumlahDosen(); i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    data[i][j] = getNamaDosen()[i];
                } else if (j == 1) {
                    data[i][j] = (nilaiDosen[i] * 100);
                } else if (j == 2) {
                    if ((nilaiDosen[i] * 100) <= 50) {
                        data[i][j] = "Sangat Buruk";
                    } else if ((nilaiDosen[i] * 100) > 50 && (nilaiDosen[i] * 100) < 60) {
                        data[i][j] = "Buruk";
                    } else if ((nilaiDosen[i] * 100) >= 60 && (nilaiDosen[i] * 100) < 70) {
                        data[i][j] = "Cukup";
                    } else if ((nilaiDosen[i] * 100) >= 70 && (nilaiDosen[i] * 100) < 80) {
                        data[i][j] = "Baik";
                    } else if ((nilaiDosen[i] * 100) > 80) {
                        data[i][j] = "Sangat Baik";
                    }
                }
            }
        }

        String[] header = {"Nama Dosen", "Nilai", "Kinerja"};
        DefaultTableModel dtm = new DefaultTableModel(data, header);
        analisaView.getHasilAnalisaTable().setModel(dtm);
    }
    
    public void saveHasil(){
        kriteriaDosenModel.truncate();
        for (int i = 0; i < analisaView.getHasilAnalisaTable().getRowCount(); i++) {
            String nidn = kriteriaDosenModel.getNidn(analisaView.getHasilAnalisaTable().getValueAt(i, 0).toString());
            Double nilai = Double.valueOf(analisaView.getHasilAnalisaTable().getValueAt(i, 1).toString());
            String kinerja = analisaView.getHasilAnalisaTable().getValueAt(i, 2).toString();
            kriteriaDosenModel.insertKinerja(nidn, nilai, kinerja);
            System.out.println("Inserted");
        }
        analisaView.dispose();
        getReport();
    }
    
    public void getReport() {
        InputStream stream;
        Map<String, Object> map;
        stream = getClass().getResourceAsStream("report/Laporan Hasil Kinerja.jasper");
        map = new HashMap<>();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, map, ConnectionUtility.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            //Logger.getLogger(KonsultasiController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
