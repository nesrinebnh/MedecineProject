package FXMLController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AcceuilController implements Initializable {

	@FXML
    private AnchorPane bodybg;

    @FXML
    private JFXButton ConsultationAcceuil;

    @FXML
    private JFXButton OrdonnanceAcceuil;

    @FXML
    private JFXButton rendezvous;

    @FXML
    private JFXButton patientAcceuil;

    @FXML
    private JFXButton medicaments;

    @FXML
    private JFXButton gestionDePaiment;

    @FXML
    private JFXButton analyseRadiologique;

    @FXML
    private JFXButton statistique;

    @FXML
    private JFXButton droitDAcces;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void consultationAcceuilAction() throws IOException{
		System.out.println("ok, consultation");
		ConsultationAcceuil.getScene().getWindow().hide();
		Stage consultation = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Consultation.fxml"));

        Scene scene = new Scene(root);
        consultation.setScene(scene);
        consultation.show();
        consultation.setResizable(false);
	}

	@FXML
	public void ordonnanceAcceuilAction() throws IOException{
		System.out.println("ok, ordonnance");
		OrdonnanceAcceuil.getScene().getWindow().hide();
		Stage ordonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Ordannance.fxml"));

        Scene scene = new Scene(root);
        ordonnance.setScene(scene);
        ordonnance.show();
        ordonnance.setResizable(false);
	}

	@FXML
	public void gestionDePaimentAction() throws IOException{
		gestionDePaiment.getScene().getWindow().hide();
		Stage ordonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Paiment.fxml"));
        Scene scene = new Scene(root);
        ordonnance.setScene(scene);
        ordonnance.show();
        ordonnance.setResizable(false);
	}

	@FXML
	public void patientAcceuilAction() throws IOException{
		patientAcceuil.getScene().getWindow().hide();
		Stage patientAcceuil = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Patient.fxml"));
        Scene scene = new Scene(root);
        patientAcceuil.setScene(scene);
        patientAcceuil.show();
        patientAcceuil.setResizable(false);
	}

	@FXML
	public void rendezvousAction() throws IOException{
		rendezvous.getScene().getWindow().hide();
		Stage patientAcceuil = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/RendezVous.fxml"));
        Scene scene = new Scene(root);
        patientAcceuil.setScene(scene);
        patientAcceuil.show();
        patientAcceuil.setResizable(false);
	}

}
