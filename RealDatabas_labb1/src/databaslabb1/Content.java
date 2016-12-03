/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.util.ArrayList;

/**
 *
 * @author swehu
 */
public class Content {
    int contentID, timestamp;
    ContentType type;
    String title, addedBy;
    ArrayList<String> genre;
    ArrayList<Creator> creators;

    public Content(int contentID, ContentType type, String title, int releaseDate, String addedBy) {
        this.contentID = contentID;
        this.timestamp = releaseDate;
        this.type = type;
        this.title = title;
        this.addedBy = addedBy;
    }
    
    public void addCreator(Creator creator){
        creators.add(creator);
    }
    
    public Creator removeCreator(int index){
        return creators.remove(index);
    }
    
    public boolean removeCreator(Creator creator){
        return creators.remove(creator);
    }
    public ArrayList<Creator> getCreators(){
        return (ArrayList<Creator>)creators.clone();
    }
    
}
