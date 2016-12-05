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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mattias Kågström
 */
public class AddWindowController implements Initializable {

    @FXML
    private ChoiceBox<String> typeSelector;
    @FXML
    private TextField titleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField creatorField;
    @FXML
    private Button addBtn;
    @FXML
    private DatePicker releaseDateSelector;

    DBConnection dbcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        typeSelector.setItems(FXCollections.observableArrayList("Album", "Movie"));
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        DefinedStatements dstmt = new databaslabb1.DefinedStatements();
        Content content = new Content(ContentType.ALBUM, titleField.getText(), Timestamp.valueOf(releaseDateSelector.getValue().atStartOfDay()), "matkag@kth.se"); //update!!
        content.addGenre(genreField.getText());
        content.addCreator(new Creator(creatorField.getText(), "Artist", "Sweden", "matkag@kth.se"));//Update!!
        dstmt.addContent(content);
    }
    @FXML
    private void quit(){
        dbcon.close();
    }
}
