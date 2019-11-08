package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient {

	private StringProperty CIN,nom,prenom,date,telephone;
	private String id;

	public Patient(){}

	public Patient(String id,String CIN, String nom,String prenom,String date,String telephone){
		this.CIN = new SimpleStringProperty(CIN);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.date = new SimpleStringProperty(date);
		this.telephone = new SimpleStringProperty(telephone);
		this.id = id;
	}

	public String getCIN() {
        return CIN.get();
    }
    public void setCIN(String CIN) {
        this.CIN.set(CIN);
    }
    public StringProperty CINProperty() {
        return CIN;
    }

    public String getNom() {
        return nom.get();
    }
    public void setNom(String nom) {
        this.nom.set(nom);
    }
    public StringProperty nomProperty() {
        return nom;
    }

    public String getPrenom() {
        return prenom.get();
    }
    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }
    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }
    public StringProperty dateProperty() {
        return date;
    }

    public String getTelephone() {
        return telephone.get();
    }
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }
    public StringProperty telephoneProperty() {
        return telephone;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
