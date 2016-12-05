/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author swehu
 */
public class Content {

    int contentID;
    ContentType type;
    String title, addedBy;
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<Creator> creators = new ArrayList<>();
    Timestamp timestamp;
    private final StringProperty Title;
    private final StringProperty Creator;
    private final StringProperty Genre;
    private final StringProperty Rating;

    public Content(int contentID, ContentType type, String title, Timestamp releaseDate, String addedBy, String rating) {//Create object from content in the database
        this.contentID = contentID;
        this.timestamp = releaseDate;
        this.type = type;
        this.title = title;
        this.addedBy = addedBy;
        this.Title = new SimpleStringProperty(title);
        this.Creator = new SimpleStringProperty();
        this.Genre = new SimpleStringProperty();
        this.Rating = new SimpleStringProperty(rating);
    }

    public Content(ContentType type, String title, Timestamp releaseDate, String addedBy) {//Create a new one in the application
        this.timestamp = releaseDate;
        this.type = type;
        this.title = title;
        this.addedBy = addedBy;
        this.Title = new SimpleStringProperty(title);
        this.Creator = new SimpleStringProperty();
        this.Genre = new SimpleStringProperty();
        this.Rating = new SimpleStringProperty();
    }

    public void addCreator(Creator creator) {
        creators.add(creator);
    }

    public Creator removeCreator(int index) {
        return creators.remove(index);
    }

    public boolean creatorExists(int creatorID) {
        for (Creator creator : creators) {
            if (creator.getCreatiorID() == creatorID) {
                return true;
            }
        }
        return false;
    }

    public boolean removeCreator(Creator creator) {
        return creators.remove(creator);
    }

    public ArrayList<Creator> getCreators() {
        return (ArrayList<Creator>) creators.clone();
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public String removeGenre(int index) {
        return genres.remove(index);
    }

    public boolean genreExists(String genre) {
        for (String genre1 : genres) {
            if (genre1 == genre) {
                return true;
            }
        }
        return false;
    }

    public boolean removeGenre(String genre) {
        return creators.remove(genre);
    }

    public ArrayList<Creator> getGenres() {
        return (ArrayList<Creator>) genres.clone();
    }

    public StringProperty titleProperty() {
        return Title;
    }

    public StringProperty creatorProperty() {
        String cr = "";
        for (Creator cre : creators) {
            cr += cre.getName() + ", ";
        }
        Creator.set(cr.substring(0, cr.length() - 2));
        return Creator;
    }

    public StringProperty ratingProperty() {
        return Rating;
    }

    public StringProperty genreProperty() {
        String gr = "";
        for (String gre : genres) {
            gr += gre + ", ";
        }
        Genre.set(gr.substring(0, gr.length() - 2));
        return Genre;
    }

    public ContentType getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getTitle() {
        return title;
    }
}
