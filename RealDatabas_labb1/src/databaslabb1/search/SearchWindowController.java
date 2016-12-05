/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1.search;

import databaslabb1.Content;
import databaslabb1.DBConnection;
import databaslabb1.view.LoginWindowController;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Mattias Kågström
 */
public class SearchWindowController implements Initializable {

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

        ratingBox.setItems(FXCollections.observableArrayList("All", "1", "2", "3", "4", "5"));
        scoreBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        Title.setCellValueFactory(Title -> Title.getValue().titleProperty());
        Creator.setCellValueFactory(Creator -> Creator.getValue().creatorProperty());
        Rating.setCellValueFactory(Rating -> Rating.getValue().ratingProperty());
        Genre.setCellValueFactory(Genre -> Genre.getValue().genreProperty());
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        DefinedStatements dstmt = new databaslabb1.DefinedStatements();
        ArrayList<Content> result = dstmt.searchContent(titleField.getText(), genreField.getText(), creatorField.getText(), ratingBox.getSelectionModel().getSelectedItem().toString());
        ObservableList<Content> data = FXCollections.observableArrayList();
        for (Content content : result) {
            data.add(content);
        }
        resultsTable.setItems(data);
    }

    @FXML
    private void handleTableviewClick(MouseEvent event) {
        selectedField.setText(resultsTable.getSelectionModel().getSelectedItem().getTitle());

    }

    @FXML
    private void handleReviewAction(ActionEvent event) {
        new Thread() {
            public void run() {
                DefinedStatements dstmt = new databaslabb1.DefinedStatements();
                Content content = resultsTable.getSelectionModel().getSelectedItem();
                dstmt.addReview(content, reviewArea.getText(), scoreBox.getValue());
                javafx.application.Platform.runLater(
                        new Runnable() {
                    public void run() {
                        selectedField.setText("");
                        reviewArea.setText("");
                        scoreBox.setValue("");
                    }
                });
            }
        }.start();
    }
}
