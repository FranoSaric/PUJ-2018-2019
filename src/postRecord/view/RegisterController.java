/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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

/**
 * FXML Controller class
 *
 * @author ACER NITRO
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXTextField ime;
    @FXML
    private JFXPasswordField lozinka;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addUser(ActionEvent event) {
        String name = ime.getText();
        String password = lozinka.getText();
        
        
        if(name.isEmpty()||password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo popunite sva polja");
            alert.showAndWait();
            return;
        }
        try {
            PreparedStatement stmnt = Baza.DB.konekcija.prepareStatement(
                    "INSERT INTO korisnik VALUES (null, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmnt.setString(1, name);
            stmnt.setString(2, password);
            stmnt.setString(3, "");
            stmnt.setString(4, "2");
            stmnt.executeUpdate();

            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            generatedKeys.next();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspiješno ste se registrirali!");
            alert.showAndWait();
          
        } catch (SQLException e) {
            System.out.println("Greska prilikom stvaranja korisnika u bazi:"
                    + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste se uspijeli registrirat!");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    

  
    
}
