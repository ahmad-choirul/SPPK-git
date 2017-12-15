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
public class Subkriteria {
    
    private String id;
    private String id_kriteria;
    private String nama;
    private Integer bobot;

    public Subkriteria(String id, String id_kriteria, String nama, Integer bobot) {
        this.id = id;
        this.id_kriteria = id_kriteria;
        this.nama = nama;
        this.bobot = bobot;
    }
    
    public Subkriteria(String id, String nama, Integer bobot) {
        this.id = id;
        this.id_kriteria = id_kriteria;
        this.nama = nama;
        this.bobot = bobot;
    }
    
    public String getId(){
        return id;
    }

    public String getIdKriteria(){
        return id_kriteria;
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

    public void setIdKriteria(String id_kriteria){
        this.id_kriteria = id_kriteria;
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
            case 1 : return id_kriteria;
            case 2 : return nama;
            case 3 : return bobot;
            default : return null;
        }
    }
    
    public Object getObjects(int index){
        switch(index){
            case 0 : return id;
            case 1 : return nama;
            case 2 : return bobot;
            default : return null;
        }
    }
}
