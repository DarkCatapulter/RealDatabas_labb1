/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author swehu
 */
public class DatabasLabb1 extends Application {
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Labb1 app");
            
            showLoginWindow();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void showLoginWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/LoginWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMainWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSearchWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("search/SearchWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showAddWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add/AddWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
