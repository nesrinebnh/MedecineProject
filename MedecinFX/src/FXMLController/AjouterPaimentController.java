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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AjouterPaimentController implements Initializable {

    @FXML
    private JFXButton enregistrerPaiment;

    @FXML
    private JFXButton annulerPaiment;

    @FXML
    private TextArea observationPaiment;

    @FXML
    private TextField CINPaiment;

    @FXML
    private TextField nomPaiment;

    @FXML
    private TextField prenomPaiment;

    @FXML
    private TextField montantPaiment;

    @FXML
    private TextField titrePaiment;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		hundler = new DbHundler();

	}

	@FXML
	public void enregistrerPaimentAction() throws IOException{
		if(validerInput() == false){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Paiment");
			alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
			alert.setContentText("les champs qui sont en rouges indique que vous n'avez inserer l'information qu'elle est nécessaire!");

			alert.showAndWait();
		}else{
			String observation ="'"+ this.observationPaiment.getText()+"'";
			String CIN = "'"+this.CINPaiment.getText()+"'";
			String montant = "'"+this.montantPaiment.getText()+"'";
			String titre = "'"+this.titrePaiment.getText()+"'";
			String nom = "'"+this.nomPaiment.getText()+"'";
			String prenom ="'"+ this.prenomPaiment.getText()+"'";
			Date date = Calendar.getInstance().getTime();
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		    String strDate = dateFormat.format(date);

			String insert = "INSERT INTO `cabinetdemedecine`.`paiment` (`CIN`, `nom`, `prenom`, `titre`, `date`, `totale`, `observation`) VALUES ("+CIN+","+ nom+","+ prenom+","+ titre+","+strDate+","+ montant+","+ observation+")";
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

			this.enregistrerPaiment.getScene().getWindow().hide();
			Stage paiment = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Paiment.fxml"));

	        Scene scene = new Scene(root);
	        paiment.setScene(scene);
	        paiment.show();
	        paiment.setResizable(false);
		}



	}

	@FXML
	public void annulerPaimentAction() throws IOException{
		this.annulerPaiment.getScene().getWindow().hide();
		Stage paiment = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Paiment.fxml"));

        Scene scene = new Scene(root);
        paiment.setScene(scene);
        paiment.show();
        paiment.setResizable(false);
	}

	private boolean validerInput(){
		boolean valide = true;
		if(this.CINPaiment.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			this.CINPaiment.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.CINPaiment.setText("vous devez inserer un CIN");
			valide = false;
		}
		if(this.nomPaiment.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			 this.nomPaiment.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			 this.nomPaiment.setText("vous devez inserer un nom");
			valide = false;
		}
		if(this.prenomPaiment.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			 this.prenomPaiment.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			 this.prenomPaiment.setText("vous devez inserer un prenom");
			valide = false;
		}
		if(this.montantPaiment.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			 this.montantPaiment.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			 this.montantPaiment.setText("vous devez inserer un prenom");
			valide = false;
		}
		if(this.titrePaiment.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			titrePaiment.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			titrePaiment.setText("vous devez inserer un prix");
			valide = false;
		}
		if(this.observationPaiment.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			observationPaiment.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			observationPaiment.setText("vous devez inserer une observation");
			valide = false;
		}


		return valide;
	}

}
