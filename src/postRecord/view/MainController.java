/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainController implements Initializable {

    @FXML
    private StackPane rootPane;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
     private void dodajPosiljku(ActionEvent event) {
         loadWindow("AddPost.fxml", "Dodaj novu pošiljku");
  }

    @FXML
    private void dodajKorisnika(ActionEvent event) {
        loadWindow("AddUser.fxml", "Dodaj novog korisnika");
    }
    @FXML
    private void pregledKorisnika(ActionEvent event){
        loadWindow("ListUser.fxml","Pregled");
    }

    @FXML
    private void ucitajPostavke(ActionEvent event) {
        loadWindow("settings.fxml","Postavke");
    }
    
    
    @FXML
    private void pregledPosiljki(ActionEvent event) {
        loadWindow("ListPost.fxml", "Ispis pošiljki");
    }
    
    void loadWindow(String loc, String title){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        }catch(IOException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void handleMenuClose(ActionEvent event) {
        ((Stage)rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void handleMenuAddPost(ActionEvent event) {
        loadWindow("AddPost.fxml", "Dodaj novu pošiljku");
    }

    @FXML
    private void handleMenuAddMember(ActionEvent event) {
        loadWindow("AddUser.fxml", "Dodaj novog primaoca");
    }

    @FXML
    private void handleMenuViewPost(ActionEvent event) {
        loadWindow("ListPost.fxml", "Ispis pošiljki");
    }

    @FXML
    private void handleMenuViewMember(ActionEvent event) {
        loadWindow("ListUser.fxml","Pregled");
    }

    @FXML
    private void handleMenuFullScreen(ActionEvent event) {
        Stage stage = ((Stage)rootPane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }

    

}
   

