/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import postRecord.model.Baza;
import static postRecord.model.Baza.DB;
import postRecord.view.LoginController;
/**
 * FXML Controller class
 *
 * @author ACER NITRO
 */
public class AddPostController implements Initializable {

    @FXML
    private JFXTextField nazivposiljke;
    @FXML
    private JFXTextField cijena;
    @FXML
    private JFXTextField kolicina;
    @FXML
    private JFXTextField kupon;
    @FXML
    private JFXTextField dostava;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton odustani;

    /**
     * Initializes the controller class.
     */
    
    LoginController info;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void closeStage() {
        ((Stage)nazivposiljke.getScene().getWindow()).close();
    }

    @FXML
    private void addPost(ActionEvent event) {
         String naziv = nazivposiljke.getText();
        String price = cijena.getText();
        String amount = kolicina.getText();
        String coupon = kupon.getText();
        String shipping = dostava.getText();
        
         if(naziv.isEmpty()||price.isEmpty()||amount.isEmpty()||coupon.isEmpty()||shipping.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo popunite sva polja");
            alert.showAndWait();
            return;
        }
         //Ovdje samo dodati id korisnika koji je se postavio prilikom prijave
         String id = String.valueOf(LoginController.userid);
          
     try {
            PreparedStatement stmnt = Baza.DB.konekcija.prepareStatement(
                    "INSERT INTO paket VALUES (null, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmnt.setString(1, amount);
            stmnt.setString(2, coupon);
            stmnt.setString(3, shipping);
            //Ovde bitrebao spremiti id od prijavljenog korisnika
            stmnt.setString(4, id);
            stmnt.executeUpdate();

            PreparedStatement stmnt2 = Baza.DB.konekcija.prepareStatement(
                    "INSERT INTO proizvod VALUES (null, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmnt2.setString(1, naziv);
            stmnt2.setString(2, price);
            stmnt2.executeUpdate();
            
            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            generatedKeys.next();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspiješno ste dodali pošiljku!");
            alert.showAndWait();
            closeStage();
            
          
        } catch (SQLException e) {
            System.out.println("Greska prilikom stvaranja paketa u bazi:"
                    + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste uspijeli dodat novu pošiljku!");
            alert.showAndWait();
        }
    
     
    }

    
    
    @FXML
    private void odustani(ActionEvent event) {
         Stage stage = (Stage) odustani.getScene().getWindow();
        stage.close();
    }
    
}
