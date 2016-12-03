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
public class Creator {
    int creatorID;
    String name, role, nation, addedBy;

    public Creator(int creatorID, String name, String role, String nation, String addedBy) {
        this.creatorID = creatorID;
        this.name = name;
        this.role = role;
        this.nation = nation;
        this.addedBy = addedBy;
    }
    
    public String getName(){
        return name;
    }
    public String getRole(){
        return role;
    }
    public String getNation(){
        return nation;
    }
    public String getAddedBy(){
        return addedBy;
    }
    public int getCreatiorID(){
        return creatorID;
    }
     public void setName(String name){
        this.name = name;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setNation(String nation){
        this.nation = nation;
    }
    public void setAddedBy(String addedBy){
        this.addedBy = addedBy;
    }
}
