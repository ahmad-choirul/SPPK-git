/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;

/**
 *
 * @author ahmad choirul
 */
public class Kriteria {
    
    private String id;
    private String nama;
    private Integer bobot;

    public Kriteria(String id, String nama, Integer bobot) {
        this.id = id;
        this.nama = nama;
        this.bobot = bobot;
    }
    
    public String getId(){
        return id;
    }

    public String getNama(){
        return nama;
    }

    public Integer getBobot(){
        return bobot;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public void setBobot(Integer bobot){
        this.bobot = bobot;
    }

    public Object getObject(int index){
        switch(index){
            case 0 : return id;
            case 1 : return nama;
            case 2 : return bobot;
            default : return null;
        }
    }
}
