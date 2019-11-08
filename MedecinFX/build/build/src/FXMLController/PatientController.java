package FXMLController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import DBConnection.DbHundler;
import Model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PatientController implements Initializable {

    @FXML
    private TextField rechercherCINPatient;

    @FXML
    private TextField rechercherParNomPatient;

    @FXML
    private JFXButton rechercherButtonPatient;

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, String> CINColumnPatient;

    @FXML
    private TableColumn<Patient, String> nomPatient;

    @FXML
    private TableColumn<Patient, String> prenomPatient;

    @FXML
    private TableColumn<Patient, String> datePatient;

    @FXML
    private TableColumn<Patient, String> telephonePatient;

    @FXML
    private JFXButton ajouterPatientButton;

    @FXML
    private JFXButton deletePatient;

    @FXML
    private JFXButton editPatient;

    @FXML
    private JFXButton backPatient;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

    private ObservableList<Patient> patientData;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		this.patientData = FXCollections.observableArrayList();
		hundler = new DbHundler();

		connection = hundler.getConnection();
		String get = "SELECT * from patient";
		try {
			pst = (PreparedStatement) connection.prepareStatement(get);


			ResultSet rs = pst.executeQuery();

			int count =0;

			while(rs.next()){
				count++;
				String id = rs.getString("idpatient");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String telephone = rs.getString("telephone");
				String CIN = rs.getString("CIN");
				String date = rs.getString("date");
				patientData.add(new Patient(id,CIN,nom,prenom,date,telephone));
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		this.CINColumnPatient.setCellValueFactory(cellData -> cellData.getValue().CINProperty());
		this.nomPatient.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		this.prenomPatient.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		this.datePatient.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		this.telephonePatient.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
		this.patientTable.setItems(this.patientData);

	}

	@FXML
	public void ajouterPatientAction() throws IOException{
		this.ajouterPatientButton.getScene().getWindow().hide();
		Stage ajouterPatient = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/ajouterPatient.fxml"));

        Scene scene = new Scene(root);
        ajouterPatient.setScene(scene);
        ajouterPatient.show();
        ajouterPatient.setResizable(false);
	}

	@FXML
	public void deletePatientAction(){
		System.out.println("delete ordonnance");
		int selectedIndex = this.patientTable.getSelectionModel().getSelectedIndex();
		Patient temp = this.patientTable.getSelectionModel().getSelectedItem();
		String delete = "DELETE FROM ordonnance WHERE idpatient = ?";
		try {
			pst = (PreparedStatement) connection.prepareStatement(delete);
			pst.setString(1, temp.getId());
			pst.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		patientTable.getItems().remove(selectedIndex);
	}

	@FXML
	public void rechercherPatientAction(){
		patientTable.getItems().clear();
		patientTable.refresh();
		patientData =  FXCollections.observableArrayList();
		if((!this.rechercherCINPatient.getText().equals("")) && (!this.rechercherParNomPatient.getText().equals(""))){
			System.out.println("les deux vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from patient where cin=? and nom = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.rechercherCINPatient.getText());
				pst.setString(2, this.rechercherParNomPatient.getText());



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idpatient");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String date = rs.getString("date");
					String telephone = rs.getString("telephone");
					String CIN = rs.getString("cin");
					patientData.add(new Patient(id,CIN,nom,prenom,date,telephone));			}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(count != 0){
				patientTable.setItems(patientData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Ordonnance");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.rechercherCINPatient.getText()+" et nom = "+this.rechercherParNomPatient.getText());

				alert.showAndWait();
			}
		}else if(!this.rechercherCINPatient.getText().equals("")){
			System.out.println("cin vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from patient where cin=?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.rechercherCINPatient.getText());

				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idpatient");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String date = rs.getString("date");
					String telephone = rs.getString("telephone");
					String CIN = rs.getString("cin");
					patientData.add(new Patient(id,CIN,nom,prenom,date,telephone));
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try{
					connection.close();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
			}


			if(count != 0){
				patientTable.setItems(patientData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.rechercherCINPatient.getText());

				alert.showAndWait();
			}
		}else if(!this.rechercherParNomPatient.getText().equals("")){
			System.out.println("medicament vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from patient where nom=?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.rechercherParNomPatient.getText());

				ResultSet rs = pst.executeQuery();



				while(rs.next()){

					count++;
					String id = rs.getString("idpatient");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String date = rs.getString("date");
					String telephone = rs.getString("telephone");
					String CIN = rs.getString("cin");
					patientData.add(new Patient(id,CIN,nom,prenom,date,telephone));



				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try{
					connection.close();
				}catch (SQLException e1){
					e1.printStackTrace();
				}
			}


			if(count != 0){
				patientTable.setItems(patientData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+"nom = "+this.rechercherParNomPatient.getText());

				alert.showAndWait();
			}
		}
	}

	@FXML
	public void backPatientAction() throws IOException{
		this.backPatient.getScene().getWindow().hide();
		Stage ajouterPatient = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Accueil.fxml"));

        Scene scene = new Scene(root);
        ajouterPatient.setScene(scene);
        ajouterPatient.show();
        ajouterPatient.setResizable(false);
	}


}
