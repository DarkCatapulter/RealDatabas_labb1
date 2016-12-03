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
public enum ContentType {
    
    MOVIE("Movie"), ALBUM("Album"), SONG("Song");
    
    public String getValue() {
        return value;
    }

    private final String value;

    private ContentType(String value) {
        this.value = value;
    }
}
