/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;

import java.util.Date;

/**
 *
 * @author Fadli Hudaya
 */
public class Lahan {
    
    private String no;
    private String nama;
    private String alamat;

    public Lahan(String no, String nama, String jenis_kelamin) {
        this.no = no;
        this.nama = nama;
        this.alamat = jenis_kelamin;
    }
    
    public String getNo(){
        return no;
    }

    public String getNama(){
        return nama;
    }

    public void setno(String no){
        this.no = no;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getalamat() {
        return alamat;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.alamat = jenisKelamin;
    }
    
    public Object getObject(int index){
        switch(index){
            case 0 : return no;
            case 1 : return nama;
            case 2 : return alamat;
            default : return null;
        }
    }
}
