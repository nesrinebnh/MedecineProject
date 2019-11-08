package FXMLController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import DBConnection.DbHundler;
import Model.Consultation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConsultationController implements Initializable {

    @FXML
    private TextField rechercherCIN;

    @FXML
    private TextField rechercheTypeOperation;

    @FXML
    private DatePicker rechercherDate;

    @FXML
    private JFXButton rechercherButton;

    @FXML
    private TableView<Consultation> consultationTable;

    @FXML
    private TableColumn<Consultation, String> CINColumn;

    @FXML
    private TableColumn<Consultation, String> nomColumn;

    @FXML
    private TableColumn<Consultation, String> prenomColumn;

    @FXML
    private TableColumn<Consultation, String> operationcolumn;

    @FXML
    private TableColumn<Consultation, String> dateOperationColumn;

    @FXML
    private TableColumn<Consultation, String> poindsColumn;

    @FXML
    private TableColumn<Consultation, String> prixColumn;

    @FXML
    private TableColumn<Consultation, String> observationColumn;

    @FXML
    private JFXButton ajouterConsultationButton;

    @FXML
    private JFXButton deleteConsultation;

    @FXML
    private JFXButton editConsultation;

    @FXML
    private JFXButton backConsultation;

    private ObservableList<Consultation> consultationData;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

    private String formattedString;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		consultationData = FXCollections.observableArrayList();
		hundler = new DbHundler();

		connection = hundler.getConnection();
		String get = "SELECT * from consultation";
		try {
			pst = (PreparedStatement) connection.prepareStatement(get);


			ResultSet rs = pst.executeQuery();

			int count =0;

			while(rs.next()){
				count++;
				String id = rs.getString("idconsultation");
				String CIN = rs.getString("CIN");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String operation = rs.getString("operation");
				String dateOperation = rs.getString("dateOperation");
				String poinds = rs.getString("poinds");
				String prix = rs.getString(("prix"));
				String observation = rs.getString("observation");


				consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		CINColumn.setCellValueFactory(cellData -> cellData.getValue().CINProperty());
		nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		dateOperationColumn.setCellValueFactory(cellData -> cellData.getValue().dateOperationProperty());
		poindsColumn.setCellValueFactory(cellData -> cellData.getValue().poindsProperty());
		prixColumn.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
		observationColumn.setCellValueFactory(cellData -> cellData.getValue().observationProperty());
		operationcolumn.setCellValueFactory(cellData -> cellData.getValue().operationProperty());
		consultationTable.setItems(consultationData);

	}

	@FXML
	public void ajouterConsultationAction() throws IOException{
		System.out.println("ok, ajouter consultation");
		ajouterConsultationButton.getScene().getWindow().hide();
		Stage ajouterConsultation = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/ajouterConsultation.fxml"));

        Scene scene = new Scene(root);
        ajouterConsultation.setScene(scene);
        ajouterConsultation.show();
        ajouterConsultation.setResizable(false);
	}

	@FXML
	public void deleteConsultationAction(){
		System.out.println("delete consultation");
		int selectedIndex = consultationTable.getSelectionModel().getSelectedIndex();
		Consultation temp = consultationTable.getSelectionModel().getSelectedItem();
		String delete = "DELETE FROM consultation WHERE idconsultation = ?";
		try {
			pst = (PreparedStatement) connection.prepareStatement(delete);
			pst.setString(1, temp.getId());
			pst.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		consultationTable.getItems().remove(selectedIndex);
	}

	@FXML
	public void backConsultationAction() throws IOException{
		backConsultation.getScene().getWindow().hide();
		Stage ajouterConsultation = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Accueil.fxml"));

        Scene scene = new Scene(root);
        ajouterConsultation.setScene(scene);
        ajouterConsultation.show();
        ajouterConsultation.setResizable(false);
	}

	@FXML
	public void rechercheConsultationAction(){

		consultationTable.getItems().clear();
		consultationTable.refresh();
		consultationData =  FXCollections.observableArrayList();
		if((!this.rechercherCIN.getText().equals("")) && (!this.rechercheTypeOperation.getText().equals("")) && (this.rechercherDate.getValue()!= null)){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where CIN=? and operation = ? and dateOperation = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, rechercherCIN.getText());
				pst.setString(2, this.rechercheTypeOperation.getText());
				LocalDate localDate = this.rechercherDate.getValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
				formattedString = localDate.format(formatter);
				pst.setString(3, formattedString);



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.rechercherCIN.getText()+" et type opération = "+this.rechercheTypeOperation.getText()+" date = "+formattedString);

				alert.showAndWait();
			}
		}else if((!this.rechercherCIN.getText().equals("")) && (!this.rechercheTypeOperation.getText().equals(""))){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where CIN=? and operation = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, rechercherCIN.getText());
				pst.setString(2, this.rechercheTypeOperation.getText());



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.rechercherCIN.getText()+" et type opération = "+this.rechercheTypeOperation.getText()+" date = "+formattedString);

				alert.showAndWait();
			}
		}else if((!this.rechercheTypeOperation.getText().equals("")) && ((this.rechercherDate.getValue() != null))){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where operation = ? and dateOperation = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.rechercheTypeOperation.getText());
				LocalDate localDate = this.rechercherDate.getValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
				formattedString = localDate.format(formatter);
				pst.setString(2, formattedString);



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+"type opération = "+this.rechercheTypeOperation.getText()+" date = "+formattedString);

				alert.showAndWait();
			}
		}else if((!this.rechercherCIN.getText().equals("")) && (!this.rechercherDate.getValue().equals(""))){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where CIN=? and dateOperation = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, rechercherCIN.getText());
				LocalDate localDate = this.rechercherDate.getValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
				formattedString = localDate.format(formatter);
				pst.setString(2, formattedString);



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.rechercherCIN.getText()+" date = "+formattedString);

				alert.showAndWait();
			}
		}
		else if(!this.rechercherCIN.getText().equals("")){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where CIN=?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, rechercherCIN.getText());

				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.rechercherCIN.getText());

				alert.showAndWait();
			}
		}else if(this.rechercherDate.getValue() != null){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where dateOperation = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				LocalDate localDate = this.rechercherDate.getValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
				formattedString = localDate.format(formatter);
				pst.setString(1, formattedString);



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+" date = "+formattedString);

				alert.showAndWait();
			}
		}else if(!this.rechercheTypeOperation.getText().equals("")){
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from consultation where operation = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.rechercheTypeOperation.getText());



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idconsultation");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String operation = rs.getString("operation");
					String dateOperation = rs.getString("dateOperation");
					String poinds = rs.getString("poinds");
					String prix = rs.getString(("prix"));
					String observation = rs.getString("observation");


					consultationData.add(new Consultation(id,CIN,nom,prenom,operation,dateOperation,poinds,prix,observation));
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
				consultationTable.setItems(consultationData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+"type opération = "+this.rechercheTypeOperation.getText());

				alert.showAndWait();
			}
		}
	}



}
