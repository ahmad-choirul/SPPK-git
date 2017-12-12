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
    
    private String nidn;
    private String nama;
    private String jenisKelamin;

    public Lahan(String nidn, String nama, String jenis_kelamin) {
        this.nidn = nidn;
        this.nama = nama;
        this.jenisKelamin = jenis_kelamin;
    }
    
    public String getNidn(){
        return nidn;
    }

    public String getNama(){
        return nama;
    }

    public void setNidn(String nidn){
        this.nidn = nidn;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    
    public Object getObject(int index){
        switch(index){
            case 0 : return nidn;
            case 1 : return nama;
            case 2 : return jenisKelamin;
            default : return null;
        }
    }
}
