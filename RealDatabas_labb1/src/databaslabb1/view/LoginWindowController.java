/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1.view;

import databaslabb1.DBConnection;
import databaslabb1.DefinedStatements;
import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            new Thread() {

                public void run() {
                    DefinedStatements dstm = new DefinedStatements();
                    boolean loginSuccess = dstm.login(Email.getText(), Password.getText());
                    javafx.application.Platform.runLater(new Runnable() {
                        public void run() {
                            if (loginSuccess == true) {
                                try {
                                    dbcon.setUser(Email.getText());
                                    Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    ((Stage) ((Node) event.getTarget()).getScene().getWindow()).hide();
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("Wrong credentials");
                                alert.setHeaderText("Wrong credentials");
                                alert.setContentText("You have entered either the email or password incorrectly. Please try again.");

                                alert.showAndWait();
                            }
                        }
                    });
                }
            }.start();

        } catch (Exception Ex) {
            System.out.println(Ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
    }

}
