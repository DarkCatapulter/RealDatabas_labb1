/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1.view;

import databaslabb1.DBConnection;
import databaslabb1.DefinedStatements;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author swehu
 */
public class LoginWindowController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField Email;
    @FXML
    private PasswordField Password;
    DBConnection dbcon;

    public LoginWindowController() {
        try {
            dbcon = new DBConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {

            DefinedStatements dstm = new DefinedStatements(dbcon);
            if (dstm.login(Email.getText(), Password.getText()) == true) {
                dbcon.close();
                Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                ((Stage) ((Node) event.getTarget()).getScene().getWindow()).hide();
                stage.show();
            }

        } catch (Exception Ex) {
            System.out.println(Ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
