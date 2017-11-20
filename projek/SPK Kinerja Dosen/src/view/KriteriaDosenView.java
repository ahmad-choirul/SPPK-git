/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.KriteriaDosenController;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.KriteriaDosenModel;
import tablemodel.KriteriaDosenTableModel;

/**
 *
 * @author Fadli Hudaya
 */
public class KriteriaDosenView extends javax.swing.JInternalFrame {

    /**
     * Creates new form KriteriaDosenView
     */
    public KriteriaDosenView() {
        initComponents();
        kriteriaDosenModel = new KriteriaDosenModel();
        kriteriaDosenController = new KriteriaDosenController(this, kriteriaDosenModel);
        kriteriaDosenController.loadNamaDosen();
        kriteriaDosenController.setAction();
        setLocation((1366 / 2) - (getWidth() / 2), (768 / 2) - (getHeight() / 2));
    }

    public JButton getBaruButton() {
        return baruButton;
    }

    public JButton getHapusButton() {
        return hapusButton;
    }

    public JTable getKriteriaDosenTable() {
        return kriteriaDosenTable;
    }

    public JComboBox getNamaDosenField() {
        return namaDosenField;
    }

    public KriteriaDosenTableModel getKriteriaDosenTableModel() {
        return kriteriaDosenTableModel;
    }

    public void setKriteriaDosenTableModel(KriteriaDosenTableModel kriteriaDosenTableModel) {
        this.kriteriaDosenTableModel = kriteriaDosenTableModel;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getId_kriteria() {
        return id_kriteria;
    }

    public void setId_kriteria(String id_kriteria) {
        this.id_kriteria = id_kriteria;
    }

    public String getId_subkriteria() {
        return id_subkriteria;
    }

    public void setId_subkriteria(String id_subkriteria) {
        this.id_subkriteria = id_subkriteria;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        baruButton = new javax.swing.JButton();
        hapusButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        namaDosenField = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        kriteriaDosenTable = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);

        jToolBar1.setFloatable(false);

        baruButton.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        baruButton.setText("Tambah");
        baruButton.setFocusable(false);
        baruButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        baruButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        baruButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baruButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(baruButton);

        hapusButton.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        hapusButton.setText("Hapus");
        hapusButton.setFocusable(false);
        hapusButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hapusButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        hapusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(hapusButton);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Nama Dosen :");

        namaDosenField.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        namaDosenField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                namaDosenFieldItemStateChanged(evt);
            }
        });

        kriteriaDosenTable.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        kriteriaDosenTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        kriteriaDosenTable.setRowHeight(22);
        jScrollPane1.setViewportView(kriteriaDosenTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(namaDosenField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(namaDosenField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void baruButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baruButtonActionPerformed
        if (kriteriaDosenTable.getRowCount() == 0) {
            FormInputKriteria fik = new FormInputKriteria(null, true, kriteriaDosenController);
            fik.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Kriteria Dosen Sudah Ada, Hapus Dahulu Jika Ingin Menambah Kriteria");
        }
// TODO add your handling code here:
    }//GEN-LAST:event_baruButtonActionPerformed

    private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
        kriteriaDosenController.saveOrDelete();
    }//GEN-LAST:event_hapusButtonActionPerformed

    private void namaDosenFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_namaDosenFieldItemStateChanged

    }//GEN-LAST:event_namaDosenFieldItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton baruButton;
    private javax.swing.JButton hapusButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable kriteriaDosenTable;
    private javax.swing.JComboBox namaDosenField;
    // End of variables declaration//GEN-END:variables
    private KriteriaDosenModel kriteriaDosenModel;
    private KriteriaDosenTableModel kriteriaDosenTableModel;
    private String nidn, id_kriteria, id_subkriteria;
    private KriteriaDosenController kriteriaDosenController;
}
