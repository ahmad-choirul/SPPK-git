/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entitas.KriteriaLahan;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import model.KriteriaLahanModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.DataAwalTableModel;
import utility.ConnectionUtility;
import view.AnalisaView;

/**
 *
 * @author ahmad choirul
 */
public class AnalisaController {

    private AnalisaView analisaView;
    private KriteriaLahanModel kriterialahanmodel;
    private KriteriaLahan kriterialahan;

    public AnalisaController(AnalisaView analisaView, KriteriaLahanModel kriterialahanmodel) {
        this.analisaView = analisaView;
        this.kriterialahanmodel = kriterialahanmodel;
    }

    public void refreshTable() {
        analisaView.setDataAwalTableModel(new DataAwalTableModel());
        analisaView.getDataAwalTableModel().setListKriteria_lahan(kriterialahanmodel.getAll());
        analisaView.getDataAwalTable().setModel(analisaView.getDataAwalTableModel());
        analisaView.getDataAwalTable().getTableHeader().setFont(new Font("Segoe UI", 0, 14));
    }

    private String[] getKriteria() {
        return kriterialahanmodel.getIdKriteria();
    }

    private int getjumlahlahan() {
        return kriterialahanmodel.getNamaLahan().length;
    }

    private String[] getnamalahan() {
        return kriterialahanmodel.getNamaLahan();
    }

    private int getNilai(String himpunan) {
        Integer nilai = kriterialahanmodel.getNilai(himpunan);
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
        Object[][] data = new Object[getjumlahlahan()][getKriteria().length];
        int a = 0;
        for (int i = 0; i < getjumlahlahan(); i++) {
            for (int j = 0; j < getKriteria().length; j++) {
                String id_subkriteria = analisaView.getDataAwalTable().getValueAt(a, 2).toString();
                data[i][j] = getNilai(id_subkriteria);
                a++;
            }
        }
        DefaultTableModel df = new DefaultTableModel(data, getKriteria());
        analisaView.getmKeputusanTable().setModel(df);
    }
//nilai pembagi

    public void getMatrikNormalisasi() {
        Object[][] data = new Object[getjumlahlahan()][getKriteria().length];
        Double[] pembagi = new Double[getKriteria().length];
        Integer[] bobotKriteria = new Integer[getKriteria().length];
        for (int i = 0; i < getKriteria().length; i++) {
            bobotKriteria[i] = kriterialahanmodel.getBobot(analisaView.getmKeputusanTable().getColumnName(i).toString());
            Double nilaiTambah = 0.0;
            for (int j = 0; j < getjumlahlahan(); j++) {
                Double nilai = Double.parseDouble(analisaView.getmKeputusanTable().getValueAt(j, i).toString());
                nilai = Math.pow(nilai, 2);//nilai pembaginya
                nilaiTambah += nilai;
                pembagi[i] = nilaiTambah;
            }
            pembagi[i] = Math.sqrt(pembagi[i]);
            System.out.println("nilai pembagi " + i + " = " + pembagi[i]);
        }

        for (int i = 0; i < getjumlahlahan(); i++) {
            for (int j = 0; j < getKriteria().length; j++) {
                Integer nilai = Integer.valueOf(analisaView.getmKeputusanTable().getValueAt(i, j).toString());
                data[i][j] = (nilai / pembagi[j]) * bobotKriteria[j];
            }
        }
        DefaultTableModel df = new DefaultTableModel(data, getKriteria());
        analisaView.getmNormalisasiTable().setModel(df);
    }

    public void getSolusiIdeal() {
        Double[] max = new Double[getKriteria().length];
        Double[] min = new Double[getKriteria().length];
        for (int i = 0; i < getKriteria().length; i++) {
            double dapatmax = 0;

            for (int j = 0; j < getjumlahlahan(); j++) {
                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
                if (dapatmax < nilai) {
                    max[i] = nilai;
                    dapatmax = nilai;
                }
            }
        }
        for (int i = 0; i < getKriteria().length; i++) {
            double dapatmin = 1000000000;
            for (int j = 0; j < getjumlahlahan(); j++) {
                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
                if (dapatmin > nilai) {
                    min[i] = nilai;
                    dapatmin = nilai;
                }
            }
        }
        Object columnNames[] = kriterialahanmodel.getKriteria();
        Object rowdatamax[][] = {max};
        Object rowdatamin[][] = {min};
        DefaultTableModel dfPositif = new DefaultTableModel(rowdatamax, columnNames);
        DefaultTableModel dfNegatif = new DefaultTableModel(rowdatamin, columnNames);

        analisaView.getSIPositifTable().setModel(dfPositif);
        analisaView.getSINegatifTable().setModel(dfNegatif);
    }

    public void getHasil() {
        String[] jarakidealpositif = new String[getjumlahlahan()];//array untuk tabel
        String[] jarakidealnegatif = new String[getjumlahlahan()];
        String[] ranking = new String[getjumlahlahan()];
        for (int i = 0; i < getjumlahlahan(); i++) {
            Double totalPositif = 0.0;
            Double totalNegatif = 0.0;

            for (int j = 0; j < getKriteria().length; j++) {
                double getapositif = Double.valueOf(analisaView.getSIPositifTable().getValueAt(0, j).toString());//nilaiD-
                double getanegatif = Double.valueOf(analisaView.getSINegatifTable().getValueAt(0, j).toString());//nilaiD+
                double getkriteria = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(i, j).toString());//nilaiD+
                double hitung = getapositif - getkriteria;
                hitung = Math.pow(hitung, 2);
                totalPositif += hitung;
                hitung = getanegatif - getkriteria;
                hitung = Math.pow(hitung, 2);
                totalNegatif += hitung;
            }

            totalPositif = Math.sqrt(totalPositif);
            totalNegatif = Math.sqrt(totalNegatif);
            jarakidealnegatif[i] = totalNegatif + "";
            jarakidealpositif[i] = totalPositif + "";
            Double hasil = totalNegatif / (totalPositif + totalNegatif);//hitung v
            ranking[i] = hasil + "";
        }

        Object columnNameskriteria[] = kriterialahanmodel.getKriteria();
        Object columnNameslahan[] = kriterialahanmodel.getlahan();

        Object columnamehasil[] = {"no", "nama lahan", "nilai"};
        Object rowhasil[][] = new Object[getjumlahlahan()][3];
        for (int i = 0; i < rowhasil.length; i++) {

        }
        for (int i = 0; i < getnamalahan().length; i++) {
            rowhasil[i][0] = i + 1;
            rowhasil[i][1] = columnNameslahan[i];
            rowhasil[i][2] = ranking[i];

        }

        Object rowidealpositif[][] = {jarakidealpositif};
        Object rowdatanegatif[][] = {jarakidealnegatif};

        DefaultTableModel jarakpositif = new DefaultTableModel(rowidealpositif, columnNameslahan);
        DefaultTableModel jaraknegatif = new DefaultTableModel(rowdatanegatif, columnNameslahan);
        DefaultTableModel hasilranking = new DefaultTableModel(rowhasil, columnamehasil);

        analisaView.getjarakpositif().setModel(jarakpositif);
        analisaView.getjaraknegatif().setModel(jaraknegatif);
        analisaView.getHasilAnalisaTable().setModel(hasilranking);

    }

    public void saveHasil() {
        kriterialahanmodel.truncate();
        for (int i = 0; i < analisaView.getHasilAnalisaTable().getRowCount(); i++) {
            String no = kriterialahanmodel.getno(analisaView.getHasilAnalisaTable().getValueAt(i, 0).toString());
            Double nilai = Double.valueOf(analisaView.getHasilAnalisaTable().getValueAt(i, 1).toString());
            String kinerja = analisaView.getHasilAnalisaTable().getValueAt(i, 2).toString();
            kriterialahanmodel.insertKinerja(no, nilai, kinerja);
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
        }

    }
}
