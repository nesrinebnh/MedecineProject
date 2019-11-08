package Model;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ordonnance {
	private StringProperty CIN,nom,prenom,observation;
	private StringProperty medicament,usage;
	private String id;

	public Ordonnance(){}



	public Ordonnance(String id,String cIN, String nom, String prenom, String observation, String medicament,String usage) {
		super();
		this.CIN = new SimpleStringProperty(cIN);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.observation = new SimpleStringProperty(observation);
		this.medicament = new SimpleStringProperty(medicament);
		this.usage = new SimpleStringProperty(usage);
		this.id = id;
	}

	public String getCINOrdonnance() {
        return CIN.get();
    }
    public void setCINOrdonnance(String CIN) {
        this.CIN.set(CIN);
    }
    public StringProperty CINPropertyOrdonnance() {
        return CIN;
    }

    public String getNomOrdonnance() {
        return nom.get();
    }
    public void setNomOrdonnance(String nom) {
        this.nom.set(nom);
    }
    public StringProperty nomPropertyOrdonnance() {
        return nom;
    }

    public String getPrenomOrdonnance() {
        return prenom.get();
    }
    public void setPrenomOrdonnance(String prenom) {
        this.prenom.set(prenom);
    }
    public StringProperty prenomPropertyOrdonnance() {
        return prenom;
    }

    public String getObservationOrdonnance() {
        return observation.get();
    }
    public void setObservationOrdonnance(String observation) {
        this.observation.set(observation);
    }
    public StringProperty observationPropertyOrdonnance() {
        return observation;
    }


    public String getUsageOrdonnance() {
        return usage.get();
    }
    public void setUsageOrdonnance(String observation) {
        this.usage.set(observation);
    }
    public StringProperty usagePropertyOrdonnance() {
        return usage;
    }

    public String getMedicamentOrdonnance() {
        return medicament.get();
    }
    public void setMedicamentOrdonnance(String observation) {
        this.medicament.set(observation);
    }
    public StringProperty medicamentPropertyOrdonnance() {
        return medicament;
    }



	public String getId() {
		return id;
	}










}
