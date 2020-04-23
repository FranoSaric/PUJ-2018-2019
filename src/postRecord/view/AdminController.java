/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;

import com.jfoenix.controls.JFXTextField;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import postRecord.model.Baza;
import static postRecord.model.Baza.DB;
import postRecord.view.AdminController.Post2;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AdminController implements Initializable {

    ObservableList<Post2> list = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Post2> tableView;
    @FXML
    private TableColumn<Post2, String> name;
    @FXML
    private TableColumn<Post2, String> role;
    @FXML
    private JFXTextField nazivField;
    @FXML
    private JFXTextField ulogaField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
                
        loadData();
    }    

    
    private void initCol() {
        SimpleIntegerProperty id = new SimpleIntegerProperty();
       name.setCellValueFactory(new PropertyValueFactory<>("name"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void loadData() {
        Baza handler = new Baza();
        
        // isprazni listu
        list = FXCollections.observableArrayList();
        try {
           
            ResultSet rs = DB.select("SELECT * FROM korisnik");
            while (rs.next()) {
                        Integer id = rs.getInt("id");
                        String name = rs.getString("ime");
                        String role = rs.getString("uloga_fk");
                        
                        list.add(new Post2(id, name, role));
            }
            
        } catch (SQLException ex) {
            System.out.println("Greška: " + ex.getMessage());
        }
        
        tableView.setItems(list);
    }
    
    Post2 odabranaPosta;
    @FXML
    private void odaberiPostu(MouseEvent event) {
        this.odabranaPosta = (Post2) this.tableView.getSelectionModel().getSelectedItem();
        this.nazivField.setText(this.odabranaPosta.getName());
        this.ulogaField.setText(this.odabranaPosta.getRole());
        
    }

    @FXML
    private void urediPosiljku(ActionEvent event) {
        
        this.odabranaPosta.setName(this.nazivField.getText());
        this.odabranaPosta.setRole((this.ulogaField.getText()));
        this.odabranaPosta.uredi();   
        this.loadData();
        
    }
    
    
    
    
    @FXML
    private void brisiPosiljku(ActionEvent event) {
        if (this.odabranaPosta != null) {
        this.odabranaPosta.brisi();
        }
        this.loadData(); // Ovo ce ucitati sve ispocetka
        //Ovdje mi treba nešto za refresh stranice kad se izbriše pošta, probavao sam nesto ali nisam uspio rijesit
    }

  
    
     public class Post2{
        private SimpleIntegerProperty id;
        private SimpleStringProperty name;
        private SimpleStringProperty role;
        
        Post2(Integer id,String name, String role){
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.role = new SimpleStringProperty(role);
            
        }
        
        
        
        public void brisi () {
        try {
            PreparedStatement upit = DB.exec("DELETE FROM korisnik WHERE id=?");
            upit.setInt(1, this.getId());
            upit.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspješno ste izbrisali korisnika!");
            alert.showAndWait();
            } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja poste u bazu:" + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste uspijeli izbrisati korisnika!");
            alert.showAndWait();
            }
        }
        
        public void uredi () {
        try {
        PreparedStatement upit = DB.exec("UPDATE korisnik SET ime=?,uloga_fk=? WHERE id=?");
        upit.setString(1, this.getName());
        upit.setString(2, this.getRole());
        upit.setInt(3, this.getId());
        upit.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspješno ste uradili izmjene!");
            alert.showAndWait();
        } catch (SQLException ex) {
        System.out.println("Greška prilikom spasavanja poste u bazu:" + ex.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste uspijeli uraditi izmjene!");
            alert.showAndWait();
        }
        }

        public Integer getId() {
            return id.get();
        }

        public void setId(SimpleIntegerProperty id) {
            this.id = id;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getRole() {
            return role.get();
        }

        public void setRole(String role) {
            this.role = new SimpleStringProperty(role);
        }

        

        

       

        
    
     }
}
