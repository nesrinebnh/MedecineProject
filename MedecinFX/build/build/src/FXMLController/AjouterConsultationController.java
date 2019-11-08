package FXMLController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.prism.paint.Color;

import DBConnection.DbHundler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.print.PrinterJob;
import Model.*;

public class AjouterConsultationController implements Initializable {

    @FXML
    private TextField CINConsultation;

    @FXML
    private DatePicker dateConsultation;

    @FXML
    private TextField poidsConsultation;

    @FXML
    private TextField prixConsultation;

    @FXML
    private TextArea observationConsultation;

    @FXML
    private JFXButton enregistrerConsultation;

    @FXML
    private JFXButton annulerConsultation;

    @FXML
    private TextField nomConsultation;

    @FXML
    private TextField prenomConsultation;

    @FXML
    private ComboBox<String> operationConsultation;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> list =FXCollections.observableArrayList("Consultation", "Controle");
		System.out.println(list.size());
		operationConsultation.setItems(list);

		hundler = new DbHundler();



	}

	@FXML
	public void enregisterConsultationAction() throws IOException{
		System.out.println(validerInput());

		if(validerInput() == false){
			System.out.println("error");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Consultation");
			alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
			alert.setContentText("les champs qui sont en rouges indique que vous n'avez inserer l'information qu'elle est n�cessaire!");

			alert.showAndWait();
		}else {
			String observation = observationConsultation.getText();
			String prix = prixConsultation.getText();
			String CIN = CINConsultation.getText();
			LocalDate localDate = dateConsultation.getValue();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
			String formattedString = localDate.format(formatter);
			String poinds = poidsConsultation.getText();
			String operation = operationConsultation.getValue();
			String nom = nomConsultation.getText();
			String prenom = prenomConsultation.getText();

			String insert = "INSERT INTO consultation(CIN,nom,prenom,operation,dateOperation,poinds,prix,observation)"+"VALUES (?,?,?,?,?,?,?,?)";
			connection = hundler.getConnection();
			try {
				pst = (PreparedStatement) connection.prepareStatement(insert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				pst.setString(1, CIN);
				pst.setString(2, nom);
				pst.setString(3, prenom);
				pst.setString(4, operation);
				pst.setString(5, formattedString);
				pst.setString(6, poinds);
				pst.setString(7, prix);
				pst.setString(8, observation);



				pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("done");

			enregistrerConsultation.getScene().getWindow().hide();
			Stage signUp = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Consultation.fxml"));

	        Scene scene = new Scene(root);
	        signUp.setScene(scene);
	        signUp.show();
	        signUp.setResizable(false);
		}
	}

	@FXML
	public void annulerConsultationAction() throws IOException{
		System.out.println("salam");

		annulerConsultation.getScene().getWindow().hide();
		Stage consultation = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Consultation.fxml"));

        Scene scene = new Scene(root);
        consultation.setScene(scene);
        consultation.show();
        consultation.setResizable(false);

	}

	private boolean validerInput(){
		boolean valide = true;
		if(this.CINConsultation.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			CINConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			CINConsultation.setText("vous devez inserer un CIN");
			valide = false;
		}
		if(this.nomConsultation.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			nomConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			nomConsultation.setText("vous devez inserer un nom");
			valide = false;
		}
		if(this.prenomConsultation.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			prenomConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			prenomConsultation.setText("vous devez inserer un prenom");
			valide = false;
		}
		if(this.dateConsultation.getValue() == null) {
			 Paint value = Paint.valueOf("660029");
			dateConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			valide = false;
		}
		if(this.prixConsultation.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			prixConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			prixConsultation.setText("vous devez inserer un prix");
			valide = false;
		}

		if(this.poidsConsultation.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			poidsConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			poidsConsultation.setText("vous devez inserer un poids");
			valide = false;
		}
		if(this.observationConsultation.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			observationConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			observationConsultation.setText("vous devez inserer une observation");
			valide = false;
		}
		if(this.operationConsultation.getValue() == null) {
			 Paint value = Paint.valueOf("660029");
			operationConsultation.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			valide = false;
		}

		return valide;
	}





}
