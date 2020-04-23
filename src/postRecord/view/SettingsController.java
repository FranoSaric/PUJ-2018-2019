package postRecord.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import static postRecord.model.Baza.DB;


public class SettingsController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    LoginController preferences;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }   
    
     private void closeStage() {
        ((Stage)username.getScene().getWindow()).close();
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event){
        //System.out.println(String.valueOf(preferences.getPassword()));
        //String lozinka=new String();
        //lozinka= preferences.getPassword();
        String id = String.valueOf(LoginController.userid);
        try {
        PreparedStatement upit = DB.exec("UPDATE korisnik SET ime=?, sifra=? WHERE id=?");
        upit.setString(1, username.getText());
        upit.setString(2, password.getText());
        upit.setString(3, id);
        upit.executeUpdate();
        closeStage();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspiješno ste promijenili ime i lozinku!");
            alert.showAndWait();
        } catch (SQLException ex) {
        System.out.println("Greška prilikom spremanja lozinke u bazu:" + ex.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste uspijeli promjeniti ime i lozinku!");
            alert.showAndWait();           
        }
        
    }
    
    private void initDefaultValues() {
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ((Stage)username.getScene().getWindow()).close();
    }
    
}
