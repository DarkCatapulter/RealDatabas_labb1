/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1.view;

import databaslabb1.DatabasLabb1;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Faddy
 */
public class MainWindowController {
    
    private DatabasLabb1 databasLabb1 = new DatabasLabb1();
    
    @FXML
    private Button menuSearchBtn;
    @FXML
    private Button menuAddBtn;
    @FXML
    private void menuSearchBtnClicked(ActionEvent event) throws IOException{
        databasLabb1.showSearchWindow();
    }
    @FXML
    private void menuAddBtnClicked(ActionEvent event) throws IOException{
        databasLabb1.showAddWindow();
    }
    
}
