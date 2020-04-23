   
package postRecord.view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import static postRecord.model.Baza.DB;


public class LoginController implements Initializable {
    
   
    public static int userid;
    public static int userRole;
   
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

   
    
    @FXML
    private Label titleLabel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        titleLabel.setText("Prijava");
        titleLabel.setStyle("-fx-background-color:black; -fx-text-fill:white");
        
        String korisnikString = this.username.getText();
        String lozinkaString = this.password.getText();
        if (korisnikString.equals("") && lozinkaString.equals("")) {
            titleLabel.setText("Greška: Molimo unesite korisničko ime i lozinku.");
            titleLabel.setStyle("-fx-background-color:#d32f2f; -fx-text-fill:white");
        } else if (korisnikString.equals("")) {
            titleLabel.setText("Greška: Molimo unesite korisničko ime");
            titleLabel.setStyle("-fx-background-color:#d32f2f; -fx-text-fill:white");
            
        } else if (lozinkaString.equals("")) {
            titleLabel.setText("Greška: Molimo unesite lozinku.");
            titleLabel.setStyle("-fx-background-color:#d32f2f; -fx-text-fill:white");
        } else {
            try {
                PreparedStatement upit = DB.prepare("SELECT * FROM korisnik WHERE ime=? and sifra=?");
                upit.setString(1, korisnikString);
                upit.setString(2, lozinkaString);
                ResultSet rs = upit.executeQuery();
                if (!rs.isBeforeFirst()) {
                    titleLabel.setText("Greška: Korisničko ime i lozinka nisu ispravni.");
                    titleLabel.setStyle("-fx-background-color:#d32f2f; -fx-text-fill:white");
                } else {
                    rs.first();
                    LoginController.userid = rs.getInt(1);
                    LoginController.userRole = rs.getInt(5); 
                    if(userRole==1){
                        closeStage();
                        loadAdmin();
                    }
                    else if(userRole==2){
                        closeStage();
                        loadUser();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage)username.getScene().getWindow()).close();
    }
    
    void loadAdmin(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Pregled pošte");
            stage.setScene(new Scene(parent));
            stage.show();
        }catch(IOException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    void loadUser(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Pregled pošte");
            stage.setScene(new Scene(parent));
            stage.show();
        }catch(IOException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
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
    private void handleRegisterButtonAction(ActionEvent event) {
        loadWindow("Register.fxml", "Registracija");
    }
    
    public JFXTextField getUsername() {
        return username;
    }

    public void setUsername(JFXTextField username) {
        this.username = username;
    }

    public JFXPasswordField getPassword() {
        return password;
    }

    public void setPassword(JFXPasswordField password) {
        this.password = password;
    }

    

}

