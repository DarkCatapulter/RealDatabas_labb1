/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

/**
 *
 * @author swehu
 */
public class User {
    String name, email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
}
