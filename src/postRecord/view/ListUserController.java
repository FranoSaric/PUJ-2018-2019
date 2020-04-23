/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import postRecord.model.Baza;
import static postRecord.model.Baza.DB;
import postRecord.model.postRecord;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ListUserController implements Initializable {
    ObservableList<Post> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPanel;
    @FXML
    private TableView<Post> tableView;
    @FXML
    private TableColumn<Post, String> nameCol;
    @FXML
    private TableColumn<Post, String> lastnameCol;
    @FXML
    private TableColumn<Post, String> subjectCol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
                
        loadData();
    }    

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
    }

    private void loadData() {
        Baza handler = new Baza();
        String userID = String.valueOf(LoginController.userid);
        try {
           
            ResultSet rs = DB.select("SELECT * FROM primaoci WHERE korisnik_fk=\'"+userID+"\'");
            while (rs.next()) {
                        String name = rs.getString("ime");
                        String lastname = rs.getString("prezime");
                        String subject = rs.getString("adresa");
                        
                        list.add(new Post(name, lastname, subject));
            }
            
        } catch (SQLException ex) {
            System.out.println("Greška: " + ex.getMessage());
        }
        
        tableView.getItems().setAll(list);
    }
    
    public class Post{
        private final SimpleStringProperty name;
        private final SimpleStringProperty lastname;
        private final SimpleStringProperty subject;
        
        Post(String name, String lastname, String subject){
            this.name = new SimpleStringProperty(name);
            this.lastname = new SimpleStringProperty(lastname);
            this.subject = new SimpleStringProperty(subject);
        }

        public String getName() {
            return name.get();
        }

        public String getLastname() {
            return lastname.get();
        }

        public String getSubject() {
            return subject.get();
        }

        
        
        
    }
}
