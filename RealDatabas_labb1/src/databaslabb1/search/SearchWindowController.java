/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1.search;

import databaslabb1.Content;
import databaslabb1.DBConnection;
import databaslabb1.DatabasLabb1;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import databaslabb1.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author Mattias Kågström
 */
public class SearchWindowController implements Initializable {
    
    private DatabasLabb1 databasLabb1;
    
    @FXML
    private TextField titleField;
    @FXML
    private TextField creatorField;
    @FXML
    private TextField genreField;
    @FXML
    private ChoiceBox<String> ratingBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TextField selectedField;
    @FXML
    private TextArea reviewArea;
    @FXML
    private ChoiceBox<String> scoreBox;
    @FXML
    private Button submitBtn;
    @FXML
    private TableView<Content> resultsTable;
    @FXML
    private TableColumn<Content, String> Title;
    @FXML
    private TableColumn<Content, String> Creator;
    @FXML
    private TableColumn<Content, String> Genre;
    @FXML
    private TableColumn<Content, String> Rating;

    DBConnection dbcon;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dbcon = new DBConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ratingBox.setItems(FXCollections.observableArrayList("All", "1", "2", "3", "4", "5"));
        Title.setCellValueFactory(Title -> Title.getValue().titleProperty());
        Creator.setCellValueFactory(Creator -> Creator.getValue().creatorProperty());
        Rating.setCellValueFactory(Rating -> Rating.getValue().ratingProperty());
        Genre.setCellValueFactory(Genre -> Genre.getValue().genreProperty());
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        DefinedStatements dstmt = new databaslabb1.DefinedStatements(dbcon);
        int rating = 0;
        if (!ratingBox.getSelectionModel().getSelectedItem().toString().contains("All")) {
            rating = Integer.parseInt(ratingBox.getSelectionModel().getSelectedItem().toString());
        }
        ArrayList<Content> result = dstmt.searchContent(titleField.getText(), genreField.getText(), creatorField.getText(), rating);
        ObservableList<Content> data = FXCollections.observableArrayList();
        for (Content content : result) {
            data.add(content);
        }
        resultsTable.setItems(data);
    }
}
