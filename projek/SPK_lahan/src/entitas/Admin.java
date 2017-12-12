/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;

/**
 *
 * @author Fadli Hudaya
 */
public class Admin {
    
    private String id;
    private String username;
    private String password;

    public Admin(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public String getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Object getObject(int index){
        switch(index){
            case 0 : return id;
            case 1 : return username;
            case 2 : return password;
            default : return null;
        }
    }
    
    public Object getObjects(int index){
        switch(index){
            case 0 : return id;
            case 1 : return username;
            default : return null;
        }
    }
}
