package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Consultation {
	private StringProperty CIN,nom,prenom,operation,dateOperation,poinds,prix,observation;
	String id;


	public Consultation(){

	}

	public Consultation(String id,String CIN, String nom,String prenom,String operation, String dateOperation,String poinds, String prix, String observation){
		this.CIN = new SimpleStringProperty(CIN);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.operation = new SimpleStringProperty(operation);
		this.dateOperation = new SimpleStringProperty(dateOperation);
		this.poinds = new SimpleStringProperty(poinds);
		this.prix = new SimpleStringProperty(prix);
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

    public String getPoinds() {
        return poinds.get();
    }
    public void setPoinds(String poinds) {
        this.poinds.set(poinds);
    }
    public StringProperty poindsProperty() {
        return poinds;
    }

    public String getPrix() {
        return prix.get();
    }
    public void setPrix(String prix) {
        this.prix.set(prix);
    }
    public StringProperty prixProperty() {
        return prix;
    }

    public String getOperation() {
        return operation.get();
    }
    public void setOperation(String operation) {
        this.operation.set(operation);
    }
    public StringProperty operationProperty() {
        return operation;
    }

    public String getDateOperation() {
        return dateOperation.get();
    }
    public void setDateOperation(String dateOperation) {
        this.dateOperation.set(dateOperation);
    }
    public StringProperty dateOperationProperty() {
        return dateOperation;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}





}
