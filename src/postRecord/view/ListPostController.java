/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.view;



import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.AnchorPane;
import postRecord.model.Baza;
import static postRecord.model.Baza.DB;
import postRecord.view.ListPostController.Post;

/**
 * FXML Controller class
 *
 * @author ACER NITRO
 */
public class ListPostController implements Initializable {

    ObservableList<Post> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPanel;
    @FXML
    private TableView<Post> tableView;
    @FXML
    private TableColumn<Post, String> name;
    @FXML
    private TableColumn<Post, String> price;
    @FXML
    private TableColumn<Post, String> amount;
    @FXML
    private TableColumn<Post, String> coupon;
    @FXML
    private TableColumn<Post, String> delivery;
    @FXML
    private JFXTextField nazivField;
    @FXML
    private JFXTextField cijenaField;
    @FXML
    private JFXTextField kolicinaField;
    @FXML
    private JFXTextField kuponField;
    @FXML
    private JFXTextField dostavaField;

    
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
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        coupon.setCellValueFactory(new PropertyValueFactory<>("coupon"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
    }

    String idK = String.valueOf(LoginController.userid);
    private void loadData() {
        Baza handler = new Baza();
        
        // isprazni listu
        list = FXCollections.observableArrayList();
        try {
            String userID = String.valueOf(LoginController.userid);
           // WHERE korisnik_fk=\'"+role+"\'");
           //paket-proizvod pp INNER JOIN paket pa ON pa.id = pp.paket_fk INNER JOIN proizvod pr ON pr.id = pp.proizvod_fk
            ResultSet rs = DB.select("SELECT paket.id, paket.kolicina, paket.kupon, paket.dostava, proizvod.naziv, proizvod.cijena FROM paket LEFT JOIN proizvod ON paket.id = proizvod.id WHERE korisnik_fk=\'"+userID+"\'");
            while (rs.next()) {
                        
                        Integer id = rs.getInt("id");
                        String name = rs.getString("naziv");
                        String price = rs.getString("cijena");
                        String amount = rs.getString("kolicina");
                        String coupon = rs.getString("kupon");
                        String delivery = rs.getString("dostava");
                        
                        list.add(new Post(id,name, price, amount, coupon, delivery));
            }
            
        } catch (SQLException ex) {
            System.out.println("Greška: " + ex.getMessage());
        }
        
        tableView.setItems(list);
    }
    
    Post odabranaPosta;
    

    @FXML
    private void odaberiPostu(MouseEvent event) {
        this.odabranaPosta = (Post) this.tableView.getSelectionModel().getSelectedItem();
        this.nazivField.setText(this.odabranaPosta.getName());
        this.cijenaField.setText(this.odabranaPosta.getPrice());
        this.kolicinaField.setText(this.odabranaPosta.getAmount());
        this.kuponField.setText(this.odabranaPosta.getCoupon());
        this.dostavaField.setText(this.odabranaPosta.getDelivery());
    }

    @FXML
    private void urediPosiljku(ActionEvent event) {
        this.odabranaPosta.setName(this.nazivField.getText());
        this.odabranaPosta.setPrice(this.cijenaField.getText());
        this.odabranaPosta.setAmount(this.kolicinaField.getText());
        this.odabranaPosta.setCoupon(this.kuponField.getText());
        this.odabranaPosta.setDelivery(this.dostavaField.getText());
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

  
    
     public class Post{
        private SimpleIntegerProperty id;
        private SimpleStringProperty name;
        private SimpleStringProperty price;
        private SimpleStringProperty amount;
        private SimpleStringProperty coupon;
        private SimpleStringProperty delivery;
        
        Post(Integer id,String name, String price, String amount, String coupon, String delivery){
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleStringProperty(price);
            this.amount = new SimpleStringProperty(amount);
            this.coupon = new SimpleStringProperty(coupon);
            this.delivery = new SimpleStringProperty(delivery);
        }
        
        public Integer getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getPrice() {
            return price.get();
        }

        public void setPrice(String price) {
            this.price = new SimpleStringProperty(price);
        }

        

        

        public String getAmount() {
            return amount.get();
        }

        public String getCoupon() {
            return coupon.get();
        }

        public String getDelivery() {
            return delivery.get();
        }
        
        public void brisi () {
        try {
            PreparedStatement upit = DB.exec("DELETE paket,proizvod FROM paket INNER JOIN proizvod WHERE paket.id=proizvod.id and paket.id=?");
            upit.setInt(1,this.getId());
            upit.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspiješno ste obrisali podatke!");
            alert.showAndWait();
            
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja poste u bazu:" + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste uspijeli izbrisati podatke!");
            alert.showAndWait();
            }
       
        
        }
        
        public void uredi () {
        try {
           
            PreparedStatement upit = DB.exec("UPDATE paket,proizvod SET proizvod.naziv=?, proizvod.cijena=?, paket.kolicina=?, paket.kupon=?, paket.dostava=? WHERE paket.id=proizvod.id");
            upit.setString(1, this.getName());
            upit.setString(2, this.getPrice());
            upit.setString(3, this.getAmount());
            upit.setString(4, this.getCoupon());
            upit.setString(5, this.getDelivery());
            upit.executeUpdate();
            
         
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Uspiješno ste uredili podatke!");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.out.println("Greška prilikom spasavanja poste u bazu:" + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Niste uspijeli urediti podatke!");
            alert.showAndWait();
        }
        
        }
        public void setId(Integer id) {
            this.id = new SimpleIntegerProperty(id);
        }


        public void setAmount(String amount) {
            this.amount =  new SimpleStringProperty(amount);
        }

        public void setCoupon( String coupon) {
            this.coupon =  new SimpleStringProperty(coupon);
        }

        public void setDelivery(String delivery) {
            this.delivery =  new SimpleStringProperty(delivery);
        }
        
        
       

       

        
    }
    
    
    
}
