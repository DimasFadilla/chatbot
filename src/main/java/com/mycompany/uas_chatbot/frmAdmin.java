/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.uas_chatbot;
import com.mycompany.uas_chatbot.Koneksi;
import data.koneksi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import menu.ExportToExcel;
/**
 *
 * @author ACER
 */
public class frmAdmin extends javax.swing.JFrame {
    
    Statement stm;
    DefaultTableModel tabModel;
    ResultSet RsProduk=null;
    
    Boolean ada = false;
    String sSatuan;
    Boolean edit=false;
    
    private Object[][] dataTable = null;
    private String[] header = {"id","username","Date"};


    /**
     * Creates new form frmBarang
     */
    public frmAdmin() {
        initComponents();
        
         Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize=this.getSize();
        if(frameSize.height > screenSize.height){
        frameSize.height=screenSize.height;
        }
        if(frameSize.width > screenSize.width){
        frameSize.width=screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
        (screenSize.height = screenSize.height) / 20); 
        
        //fungsi Tampil data dan set edit off dijalankan saat aplikasi di run
        baca_data();
        SetEditOff();
        
        
//        baca_data();
        aktif(false);
        setTombol(true);
    }
    
    private void setField()
 {
    int row=TabelProduk.getSelectedRow();
    jTextKodeProduk.setText((String)TabelProduk.getValueAt(row,0));
    jTextNama.setText((String)TabelProduk.getValueAt(row,1)); 

 }
   
    
    
    //method baca data dari Mysql dimasukkan ke table pada form
    private void baca_data()
    {
         String cari = jTcari.getText();
        try{
            Object[] judul_kolom = {"ID", "User Name","CreateDate" };
           tabModel=new DefaultTableModel(null,judul_kolom);
            TabelProduk.setModel(tabModel);
            
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            RsProduk=stt.executeQuery("SELECT * from users WHERE username LIKE'%"+cari+"%' ");  
            while(RsProduk.next()){
                Object[] data={
                    RsProduk.getString("id"),
                    RsProduk.getString("username"),
                    RsProduk.getString("CreateDate"),
                    RsProduk.getString("password")
                        
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
        // disable button
        jButtonSave.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonCancel.setEnabled(false);
    } 
    
     //menampilkan data ke form saat data pada tabel di klik 
   void tblKeForm(){
        jTextKodeProduk.setText(tabModel.getValueAt(TabelProduk.getSelectedRow(),0)+"");
        jTextNama.setText(tabModel.getValueAt(TabelProduk.getSelectedRow(),1)+"");
        
        jButtonUpdate.setEnabled(true);
        jButtonDelete.setEnabled(true);
        jButtonCancel.setEnabled(true);
        jButtonSave.setEnabled(false);
    }
   
    
    //membersihkan form
   private void BersihData(){
        jTextKodeProduk.setText("");
        jTextNama.setText("");
    } 
   
   //disable form
   private void SetEditOff(){
        jTextKodeProduk.setEnabled(false);
        jTextNama.setEnabled(true);
   }
   
   
   private void seteditOn(){
        jTextKodeProduk.setEnabled(true);
        jTextNama.setEnabled(true);
   }
    //untuk mengkosongkan isian data
    private void kosong()
    {
    jTextKodeProduk.setText("");
    jTextNama.setText(""); 
    }
    
    //mengset aktif tidak isian data
    private void aktif(boolean x)
    {
    jTextKodeProduk.setEditable(x);
//    jTextNama.setEditable(x); 
    }
    
    //mengset tombol on/off
    private void setTombol(boolean t)
    {
    cmdTambah.setEnabled(t);
    jButtonUpdate.setEnabled(t);
    jButtonDelete.setEnabled(t);
    jButtonSave.setEnabled(!t);
    jButtonCancel.setEnabled(!t);
    cmdKeluar.setEnabled(t);
    }
    
    private void autonumber(){
        try{
            Connection conn = Koneksi.getKoneksi();
            Statement s = conn.createStatement();
            String sql = "SELECT * FROM users ORDER BY id DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoID = r.getString("id").substring(2);
                String ID = "" +(Integer.parseInt(NoID)+1);
                String Zero = "";
                
                if (ID.length()==1) 
                {Zero = "00";}
                else if(ID.length()==2)
                {Zero = "0";}
                else if(ID.length()==3)
                {Zero = "";}
                
                jTextKodeProduk.setText("AD" + Zero + ID);
            }else{
                jTextKodeProduk.setText("AD001");
            }
            r.close();
            s.close();
        }catch(Exception e){
            System.out.println("autonumber error");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextKodeProduk = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelProduk = new javax.swing.JTable();
        cmdTambah = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        cmdKeluar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cmdExport = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTpassword = new javax.swing.JTextField();
        jTextNama = new javax.swing.JTextField();
        jBcari = new javax.swing.JButton();
        jTcari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jTextKodeProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextKodeProdukActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Name");

        TabelProduk.setBorder(new javax.swing.border.MatteBorder(null));
        TabelProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "User Name", "Date"
            }
        ));
        TabelProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelProdukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelProduk);

        cmdTambah.setText("Tambah");
        cmdTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTambahActionPerformed(evt);
            }
        });

        jButtonSave.setText("Simpan");
        jButtonSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSaveMouseClicked(evt);
            }
        });
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Hapus");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Batal");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        cmdKeluar.setText("Keluar");
        cmdKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeluarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DATA PENGGUNA");

        cmdExport.setText("Export");
        cmdExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExportActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Password");

        jTpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTpasswordActionPerformed(evt);
            }
        });

        jBcari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBcari.setText("Cari");
        jBcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBcariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 161, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmdTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSave)
                                .addGap(12, 12, 12)
                                .addComponent(jButtonUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmdKeluar)
                                .addGap(18, 18, 18)
                                .addComponent(cmdExport))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextKodeProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                    .addComponent(jTextNama)
                                    .addComponent(jTpassword))
                                .addGap(43, 43, 43)
                                .addComponent(jBcari)
                                .addGap(18, 18, 18)
                                .addComponent(jTcari, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextKodeProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jBcari)
                    .addComponent(jTcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextNama, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jTpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdTambah)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonDelete)
                    .addComponent(jButtonCancel)
                    .addComponent(cmdKeluar)
                    .addComponent(cmdExport))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextKodeProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextKodeProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextKodeProdukActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
   String id=jTextKodeProduk.getText();
        String username=jTextNama.getText();
        String password=jTpassword.getText();
        java.util.Date tanggal = new java.util.Date();
        java.text.SimpleDateFormat TanggalFormat= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CreateDate=TanggalFormat.format(tanggal);

 if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null,"nama tidak boleh kosong");
            jTextNama.requestFocus();
}else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null,"pass tidak boleh kosong");
            jTpassword.requestFocus();

        }else{
            try{
                Connection conn=(Connection)koneksi.koneksiDB();
                Statement stt=conn.createStatement();
                stt.executeUpdate("UPDATE users SET username='"+username+"', password='"+password+"', CreateDate='"+CreateDate+"'  WHERE id='"+id+"'");
                BersihData();
                baca_data();
                SetEditOff();
                setTombol(true);
                JOptionPane.showMessageDialog(this,"Data berhasil diubah","Success",JOptionPane.INFORMATION_MESSAGE);
            } catch(SQLException e){
                JOptionPane.showMessageDialog(this,"Ubah data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
      String id=jTextKodeProduk.getText();

        if (id.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"id tidak boleh kosong");
            jTextKodeProduk.requestFocus();
        }else if(JOptionPane.showConfirmDialog(null,"Apakah anda yakin akan menghapus data ini?",
            "Informasi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION){
        try{
            Connection conn=(Connection)koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            stt.executeUpdate("DELETE FROM users WHERE id='"+id+"'");
            BersihData();
            baca_data();
            SetEditOff();
            setTombol(true);
            JOptionPane.showMessageDialog(this,"Data berhasil di hapus","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Delete data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void TabelProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelProdukMouseClicked
    setField();
    }//GEN-LAST:event_TabelProdukMouseClicked

    private void cmdTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTambahActionPerformed
 BersihData();
 autonumber();
        jTextKodeProduk.requestFocus();
        jButtonSave.setEnabled(true);
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
        seteditOn();
   
    }//GEN-LAST:event_cmdTambahActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed

aktif(false);
setTombol(true);
kosong();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void cmdKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeluarActionPerformed
    dispose();
    }//GEN-LAST:event_cmdKeluarActionPerformed

    private void jButtonSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseClicked
//String tKode=jTextKodeProduk.getText();
//String tNama=jTextNama.getText();
//String tpassword=jTpassword.getText();
////double hrg=Double.parseDouble(txtHarga.getText());
////int stk=Integer.parseInt(txtStok.getText());
////int stkMin=Integer.parseInt(txtStokMin.getText());
//try{
//if (edit==true){
//stm.executeUpdate("update users set username='"+tNama+"'password='"+tpassword+"' where id='" + tKode + "'");
//}else{
//stm.executeUpdate("INSERT into users VALUES('"+tKode+"','"+tNama+"','"+tpassword+"')");
//}
//TabelProduk.setModel(new DefaultTableModel(dataTable,header));
//baca_data();
//aktif(false);
//setTombol(true);
//}catch(SQLException e) {
//JOptionPane.showMessageDialog(null, e);
//}
    }//GEN-LAST:event_jButtonSaveMouseClicked

    private void cmdExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExportActionPerformed
    try{
        ExportToExcel ex=new ExportToExcel(TabelProduk, new File(""));
            JOptionPane.showMessageDialog(null, "sukses export data..." );
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cmdExportActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
         String KodeProduk=jTextKodeProduk.getText();
        String NamaProduk=jTextNama.getText();
        String password=jTpassword.getText();

        java.util.Date tanggal = new java.util.Date();
        java.text.SimpleDateFormat TanggalFormat= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CreateDate=TanggalFormat.format(tanggal);

        if (KodeProduk.isEmpty() ) {
            JOptionPane.showMessageDialog(null,"id tidak boleh kosong");
            jTextKodeProduk.requestFocus();
        }else if (NamaProduk.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Nama  tidak boleh kosong");
            jTextNama.requestFocus();
        }else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null,"password produk tidak boleh kosong");
            jTpassword.requestFocus();
        } else{
            try{
                Connection conn=(Connection)koneksi.koneksiDB();
                Statement stt=conn.createStatement();
                stt.executeUpdate("insert into users(id,username,password,CreateDate)"+
                    "VALUES('"+KodeProduk+"','"+NamaProduk+"','"+password+"','"+CreateDate+"')");
                BersihData();
                baca_data();
                SetEditOff();
                setTombol(true);
                JOptionPane.showMessageDialog(this,"Data berhasil disimpan","Success",JOptionPane.INFORMATION_MESSAGE);
            } catch(SQLException e){
                JOptionPane.showMessageDialog(this,"Simpan data gagal\n"+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jTpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTpasswordActionPerformed

    private void jBcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBcariActionPerformed
        // TODO add your handling code here:
        baca_data();
    }//GEN-LAST:event_jBcariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelProduk;
    private javax.swing.JButton cmdExport;
    private javax.swing.JButton cmdKeluar;
    private javax.swing.JButton cmdTambah;
    private javax.swing.JButton jBcari;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTcari;
    private javax.swing.JTextField jTextKodeProduk;
    private javax.swing.JTextField jTextNama;
    private javax.swing.JTextField jTpassword;
    // End of variables declaration//GEN-END:variables

    
}
