package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Paiment {

	private StringProperty CIN,nom,prenom,titre,date,montant,observation;
	private String id;

	public Paiment(){}

	public Paiment(String id,String CIN, String nom,String prenom,String titre, String date,String montant, String observation){
		this.CIN = new SimpleStringProperty(CIN);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.titre = new SimpleStringProperty(titre);
		this.date = new SimpleStringProperty(date);
		this.montant = new SimpleStringProperty(montant);
		this.observation = new SimpleStringProperty(observation);
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

    public String getTitre() {
        return titre.get();
    }
    public void setTitre(String titre) {
        this.titre.set(titre);
    }
    public StringProperty titreProperty() {
        return titre;
    }

    public String getDate() {
        return date.get();
    }
    public void setDateOperation(String date) {
        this.date.set(date);
    }
    public StringProperty dateProperty() {
        return date;
    }

    public String getObservation() {
        return observation.get();
    }
    public void setObservation(String observation) {
        this.observation.set(observation);
    }
    public StringProperty observationProperty() {
        return observation;
    }

    public String getMontant() {
        return montant.get();
    }
    public void setMontant(String montant) {
        this.montant.set(montant);
    }
    public StringProperty montant() {
        return montant;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



}
