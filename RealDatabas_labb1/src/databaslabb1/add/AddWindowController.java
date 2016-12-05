/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1.add;

import databaslabb1.Content;
import databaslabb1.ContentType;
import databaslabb1.Creator;
import databaslabb1.DBConnection;
import databaslabb1.DefinedStatements;
import databaslabb1.search.SearchWindowController;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * FXML Controller class
 *
 * @author Mattias Kågström
 */
public class AddWindowController implements Initializable {

    @FXML
    private ChoiceBox<String> typeSelector;
    @FXML
    private GridPane gridpane;
    @FXML
    private TextField titleField;
    @FXML
    private Button addBtn;
    @FXML
    private DatePicker releaseDateSelector;
    @FXML
    private TextField genreField;
    @FXML
    private Button genrePlus;
    @FXML
    private Button creatorPlus;
    @FXML
    private TextField creatorField;
    @FXML
    private TextField nationalityField;
    @FXML
    private TextField creatorTypeField;
    
    DBConnection dbcon;
    private final ArrayList<TextField> genreFields = new ArrayList<>();
    private final ArrayList<TextField> creatorFields = new ArrayList<>();
    private final ArrayList<TextField> creatorTypeFields = new ArrayList<>();
    private final ArrayList<TextField> nationalityFields = new ArrayList<>();
    
    public AddWindowController() {
        genreFields.add(genreField);
        creatorTypeFields.add(creatorTypeField);
        nationalityFields.add(nationalityField);
    }

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
        typeSelector.setItems(FXCollections.observableArrayList("Album", "Movie"));
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        DefinedStatements dstmt = new databaslabb1.DefinedStatements(dbcon);
        Content content = new Content(ContentType.ALBUM, titleField.getText(), Timestamp.valueOf(releaseDateSelector.getValue().atStartOfDay()), "fatih13y@hotmail.com"); //update!!
        for (TextField genreField1 : genreFields) {
            content.addGenre(genreField1.getText());
        }
        int i = 0;
        for (TextField creatorField1 : creatorFields){
            content.addCreator(new Creator(creatorField1.getText(), creatorTypeFields.get(i).getText(), nationalityFields.get(i).getText(), "fatih13y@hotmail.com" ));
            i++;
        }
        dstmt.addContent(content);
    }

    @FXML
    private void handleGenrePlusAction(ActionEvent event) {
        int numRows = gridpane.getRowConstraints().size();
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        rc.setMaxHeight(40);
        rc.setPrefHeight(30);
        gridpane.getRowConstraints().add(rc);
        gridpane.add(new Label("More Genre:"), 0, numRows);
        TextField tmptxt = new TextField();
        genreFields.add(tmptxt);
        gridpane.add(tmptxt, 1, numRows);
        addBtn.setLayoutY(addBtn.getLayoutY() + 40);
    }

    @FXML
    private void handleCreatorPlusAction(ActionEvent event) {
        int numRows = gridpane.getRowConstraints().size();
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        rc.setMaxHeight(40);
        rc.setPrefHeight(30);
        for (int i = 0; i <= 2; i++) {
            String label = "";

            gridpane.getRowConstraints().add(rc);
            switch (i) {
                case 0:
                    label = "Creator Type:";
                    break;
                case 1:
                    label = "Creator Name:";
                    break;
                case 2:
                    label = "Nationality:";
                    break;
            }
            gridpane.add(new Label(label), 0, numRows + i);
            TextField tmptxt = new TextField();
            switch (i) {
                case 0:
                    creatorTypeFields.add(tmptxt);
                    break;
                case 1:
                    creatorFields.add(tmptxt);
                    break;
                case 2:
                    nationalityFields.add(tmptxt);
                    break;
            }
            gridpane.add(tmptxt, 1, numRows + i);
        }
        addBtn.setLayoutY(addBtn.getLayoutY() + (3 * 40));
    }
}
