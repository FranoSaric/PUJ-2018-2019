/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postRecord.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import static postRecord.model.Baza.DB;

/**
 *
 * @author Daniel
 */
public class postRecord implements CRUD {
    private Integer id;
    private String ime;
    private String prezime;
    private String adresa;
    private String broj_mobitela;
    private String grad;
    private String predmet;

    public postRecord(Integer id, String ime, String prezime, String adresa, String broj_mobitela, String grad, String predmet) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.broj_mobitela = broj_mobitela;
        this.grad = grad;
        this.predmet = predmet;
    }
    
    public static List<postRecord> dajSve () {
        try {
            List<postRecord> posta = new ArrayList <>();
            ResultSet rs = DB.select("SELECT * FROM adduser");
            while (rs.next()) {
                posta.add (new postRecord (
                        rs.getInt("id"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("adresa"),
                        rs.getString("broj_mobitela"),
                        rs.getString("grad"),
                        rs.getString("predmet")
                ));
            }
            return posta;
        } catch (SQLException ex) {
            System.out.println("Greška: " + ex.getMessage());
            return null;
        }
    }
    
   
    public static postRecord daj (Integer id) {
        try {
            PreparedStatement iskaz = DB.prepare("SELECT * FROM addpost WHERE id = ?");
            iskaz.setInt(1, id);
            ResultSet rs = iskaz.executeQuery();
            if (rs.first()) {
                return new postRecord (
                        rs.getInt("id"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("adresa"),
                        rs.getString("broj_mobitela"),
                        rs.getString("grad"),
                        rs.getString("predmet")
                );
            } else {
                System.out.println("U bazi ne postoji ta pošta");
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom izvršavanja upita: "+ex.getMessage());
        }
        return null;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBroj_mobitela() {
        return broj_mobitela;
    }

    public void setBroj_mobitela(String broj_mobitela) {
        this.broj_mobitela = broj_mobitela;
    }
    
    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }
    
    public String getPredmet() {
        return grad;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", adresa=" + adresa + ", broj_mobitela=" + broj_mobitela + ", grad=" + grad + ", predmet=" + predmet + '}';
    }

    @Override
    public void spremi() {
        if(ime.isEmpty()||prezime.isEmpty()||adresa.isEmpty()||broj_mobitela.isEmpty()||grad.isEmpty()||predmet.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo popunite sva polja!!!!!!!");
            alert.showAndWait();
            return;
        }
        try {
            PreparedStatement iskaz = DB.prepare("INSERT INTO adduser VALUES(null, ?, ?, ?, ?, ?, ?)");
            iskaz.setString(1, this.ime);
            iskaz.setString(2, this.prezime);
            iskaz.setString(3, this.adresa);
            iskaz.setString(4, this.broj_mobitela);
            iskaz.setString(5, this.grad);
            iskaz.setString(6, this.predmet);
            
            iskaz.execute();
        } catch (SQLException ex) {
            System.out.println("Pošta nije dodan greška " + ex.getMessage());
        }
    }
}
/*
    @Override
    public void uredi() {
        try {
            PreparedStatement iskaz = DB.prepare("UPDATE addpost SET ime=?, prezime=?, adresa=?, broj_mobitela=?, grad=? WHERE id=?)");
            iskaz.setString(1, this.ime);
            iskaz.setString(2, this.prezime);
            iskaz.setString(3, this.adresa);
            iskaz.setString(4, this.broj_mobitela);
            iskaz.setString(5, this.grad);
            iskaz.setInt(6, this.id);
            iskaz.execute();
        } catch (SQLException ex) {
            System.out.println("Pošta nije uređena greška " + ex.getMessage());
        }
    }

    @Override
    public void brisi() {
        try {
            PreparedStatement iskaz = DB.prepare("DELETE FROM addpost WHERE id=?)");
            iskaz.setInt(1, this.id);
            iskaz.execute();
        } catch (SQLException ex) {
            System.out.println("pošta nije pobrisana greška " + ex.getMessage());
        }
    }
}
*/