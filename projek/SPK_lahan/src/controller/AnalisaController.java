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
 * @author Fadli Hudaya
 */
public class AnalisaController {

    private AnalisaView analisaView;
    private KriteriaLahanModel kriterialahanmodel;
    private KriteriaLahan kriterialahan;

    public AnalisaController(AnalisaView analisaView, KriteriaLahanModel kriteriaDosenModel) {
        this.analisaView = analisaView;
        this.kriterialahanmodel = kriteriaDosenModel;
    }

    public void refreshTable() {
        analisaView.setDataAwalTableModel(new DataAwalTableModel());
        analisaView.getDataAwalTableModel().setListKriteria_dosen(kriterialahanmodel.getAll());
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
//        for (int i = 0; i < getKriteria().length; i++) {
//            Double tempPositif = 0.0;
//            for (int j = 0; j < getjumlahlahan(); j++) {
//                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
//                if (nilai > tempPositif) {
//                    tempPositif = nilai;
//                    max[i] = nilai;
//                    
//                }
//                if (j == 0) {
//                    min[i] = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
//                } else {
//                    if (nilai < min[i]) {
//                        min[i] = nilai;
//                    }
//                }
//            }
//            
//        }
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
        for (int i = 0; i <getKriteria().length; i++) {
            double dapatmin = 1000000000;
          for (int j = 0; j < getjumlahlahan(); j++) {
                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(j, i).toString());
                if (dapatmin > nilai) {
                    min[i] = nilai;
                    dapatmin = nilai;
                }
            }
            
        }
//for (int j = 0; j < getKriteria().length; j++) {
//                System.out.println("nilai max = "+max[j]);
//            }
//        for (int i = 0; i < getjumlahlahan(); i++) {
//            for (int j = 0; j < getKriteria().length; j++) {
//                Double nilai = Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(i, j).toString());
//                //System.out.println(nilai);
//                dataPositif[i][j] = Math.pow((nilai - max[j]), 2);
//                dataNegatif[i][j] = Math.pow((nilai - min[j]), 2);
//            }
//        }
Object columnNames[] = kriterialahanmodel.getKriteria();
Object rowdatamax[][]={max};
Object rowdatamin[][]={min};
        DefaultTableModel dfPositif = new DefaultTableModel(rowdatamax, columnNames);
        DefaultTableModel dfNegatif = new DefaultTableModel(rowdatamin, columnNames);

        analisaView.getSIPositifTable().setModel(dfPositif);
        analisaView.getSINegatifTable().setModel(dfNegatif);
    }

    public void getHasil() {
        //=====================================================================================================
        String[][] jarakidealpositif = new String [getjumlahlahan()][];//array untuk tabel
        String[][] jarakidealnegatif = new String [getjumlahlahan()][];
        
        Double nilailahan[] = new Double[getjumlahlahan()];
        for (int i = 0; i < getjumlahlahan(); i++) {
            Double totalPositif = 0.0;
            Double totalNegatif = 0.0;
            for (int j = 0; j < getKriteria().length; j++) {
                double getapositif= Double.valueOf(analisaView.getSIPositifTable().getValueAt(j, i).toString());//nilaiD+
                for (int k = 0; k < nilailahan.length; k++) {
                    double getkriteria= Double.valueOf(analisaView.getmNormalisasiTable().getValueAt(k, j).toString());//nilaiD+
                    totalPositif += Math.sqrt(Math.pow(getapositif-getkriteria, 2));
                }
                jarakidealpositif[i][j]=totalPositif+"";
            }
            Double hasil = totalNegatif / (totalPositif + totalNegatif);//hitung v
            nilailahan[i] = hasil;//nilai V
        }
        
        Object columnNameskriteria[] = kriterialahanmodel.getKriteria();
        Object columnNameslahan[] = kriterialahanmodel.getlahan();
Object rowidealpositif[][]={jarakidealpositif};
Object rowdatanegatif[][]={jarakidealnegatif};
        DefaultTableModel jarakpositif = new DefaultTableModel(rowidealpositif, columnNameskriteria);
        DefaultTableModel jaraknegatif = new DefaultTableModel(rowdatanegatif, columnNameslahan);

        analisaView.getjarakpositif().setModel(jarakpositif);
        analisaView.getjaraknegatif().setModel(jaraknegatif);
  //      ========================================================================================================================
//        Object[][] data = new Object[getjumlahlahan()][3];
//        Double nilailahan[] = new Double[getjumlahlahan()];

//        for (int i = 0; i < getjumlahlahan(); i++) {
//            Double totalPositif = 0.0;
//            Double totalNegatif = 0.0;
//            for (int j = 0; j < getKriteria().length; j++) {
//                totalPositif += Double.valueOf(analisaView.getSIPositifTable().getValueAt(i, j).toString());
//                totalNegatif += Double.valueOf(analisaView.getSINegatifTable().getValueAt(i, j).toString());
//            }
//            totalPositif = Math.sqrt(totalPositif);
//            totalNegatif = Math.sqrt(totalNegatif);
//            Double hasil = totalNegatif / (totalPositif + totalNegatif);
//            nilailahan[i] = hasil;
//            //System.out.println(totalPositif + " / (" + totalPositif + " + " + totalNegatif + " = " + hasil);
//        }
//
//        for (int i = 0; i < getjumlahlahan(); i++) {
//            for (int j = 0; j < 3; j++) {
//                if (j == 0) {
//                    data[i][j] = getnamalahan()[i];
//                } else if (j == 1) {
//                    data[i][j] = (nilailahan[i] * 100);
//                } else if (j == 2) {
//                    if ((nilailahan[i] * 100) <= 50) {
//                        data[i][j] = "Sangat Buruk";
//                    } else if ((nilailahan[i] * 100) > 50 && (nilailahan[i] * 100) < 60) {
//                        data[i][j] = "Buruk";
//                    } else if ((nilailahan[i] * 100) >= 60 && (nilailahan[i] * 100) < 70) {
//                        data[i][j] = "Cukup";
//                    } else if ((nilailahan[i] * 100) >= 70 && (nilailahan[i] * 100) < 80) {
//                        data[i][j] = "Baik";
//                    } else if ((nilailahan[i] * 100) > 80) {
//                        data[i][j] = "Sangat Baik";
//                    }
//                }
//            }
//        }
//
//        String[] header = {"Nama lahan", "Nilai", "hasil"};
//        DefaultTableModel dtm = new DefaultTableModel(data, header);
//        analisaView.getHasilAnalisaTable().setModel(dtm);
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
            //Logger.getLogger(KonsultasiController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
