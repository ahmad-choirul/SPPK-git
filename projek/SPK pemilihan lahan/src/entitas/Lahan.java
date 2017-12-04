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

    public Lahan(String nidn, String nama) {
        this.nidn = nidn;
        this.nama = nama;
    }
    
    public String getNo(){
        return nidn;
    }

    public String getNama(){
        return nama;
    }

    public void setNo(String nidn){
        this.nidn = nidn;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public Object getObject(int index){
        switch(index){
            case 0 : return nidn;
            case 1 : return nama;
            default : return null;
        }
    }
}
