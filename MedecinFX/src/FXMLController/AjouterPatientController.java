package FXMLController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import DBConnection.DbHundler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AjouterPatientController implements Initializable {

    @FXML
    private JFXButton enregistrerPatient;

    @FXML
    private JFXButton annulerPatient;

    @FXML
    private TextField nomPatient;

    @FXML
    private TextField CINPatient;

    @FXML
    private TextField prenomPatient;

    @FXML
    private TextField datePatient;

    @FXML
    private TextField telephonePatient;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		hundler = new DbHundler();

	}

	@FXML
	public void enregistrerPatientAction() throws IOException{
		if(validerInput() == false){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Patient");
			alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
			alert.setContentText("les champs qui sont en rouges indique que vous n'avez inserer l'information qu'elle est nécessaire!");

			alert.showAndWait();
		}else{
			String CIN = "'"+this.CINPatient.getText()+"'";
			String nom = "'"+this.nomPatient.getText()+"'";
			String prenom ="'"+ this.prenomPatient.getText()+"'";
		    String strDate = "'"+ this.datePatient.getText()+"'";
		    String telephone = "'"+ this.telephonePatient.getText()+"'";

			String insert = "INSERT INTO `cabinetdemedecine`.`patient` (`nom`, `prenom`, `date`, `telephone`,`cin`) VALUES ("+ nom+","+ prenom+","+strDate+","+ telephone+","+ CIN+")";
			connection = hundler.getConnection();
			try {
				pst = (PreparedStatement) connection.prepareStatement(insert);
				System.out.println(insert);
				pst.execute(insert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}



			System.out.println("done");

			this.enregistrerPatient.getScene().getWindow().hide();
			Stage paiment = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Patient.fxml"));

	        Scene scene = new Scene(root);
	        paiment.setScene(scene);
	        paiment.show();
	        paiment.setResizable(false);
		}



	}

	@FXML
	public void annulerPatientAction() throws IOException{
		this.annulerPatient.getScene().getWindow().hide();
		Stage paiment = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Paiment.fxml"));

        Scene scene = new Scene(root);
        paiment.setScene(scene);
        paiment.show();
        paiment.setResizable(false);
	}

	private boolean validerInput(){
		boolean valide = true;
		if(this.CINPatient.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			this.CINPatient.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.CINPatient.setText("vous devez inserer un CIN");
			valide = false;
		}
		if(this.nomPatient.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			 this.nomPatient.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			 this.nomPatient.setText("vous devez inserer un nom");
			valide = false;
		}
		if(this.prenomPatient.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			 this.prenomPatient.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			 this.prenomPatient.setText("vous devez inserer un prenom");
			valide = false;
		}
		if(this.datePatient.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			 this.datePatient.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			 this.datePatient.setText("vous devez inserer une date de naissance");
			valide = false;
		}
		if(this.telephonePatient.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			telephonePatient.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			telephonePatient.setText("vous devez inserer un telephone");
			valide = false;
		}


		return valide;
	}

}
