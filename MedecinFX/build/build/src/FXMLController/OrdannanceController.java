package FXMLController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import DBConnection.DbHundler;
import Model.Consultation;
import Model.Ordonnance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OrdannanceController implements Initializable{

    @FXML
    private TextField CINRechercheOrdonnance;

    @FXML
    private TextField rechercheMedicamentOrdonnance;

    @FXML
    private JFXButton rechercherOrdonnance;

    @FXML
    private TableView<Ordonnance> ordonnanceTable;

    @FXML
    private TableColumn<Ordonnance, String> CINOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> nomOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> prenomOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> traitementOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> UsageOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> observationOrdonnance;

    @FXML
    private TableColumn<Ordonnance, String> dateOrdonnance;

    @FXML
    private JFXButton deleteOrdonnance;

    @FXML
    private JFXButton editOrdonnance;

    @FXML
    private JFXButton ajouterOrdonnanceButton;

    private ObservableList<Ordonnance> ordonnanceData;

    @FXML
    private Label medicamentImprimer;

    @FXML
    private Text dosageImprimer;

    @FXML
    private Label dateImprimer;

    @FXML
    private Button imprimerBtn;

    @FXML
    private Label emailImprimer;

    @FXML
    private Label phoneImprimer;

    @FXML
    private Label nameCabinet;

    @FXML
    private Label EmailCabinet;

    @FXML
    private Label phoneCabinet;

    @FXML
    private Label dateTxtImprimer;

    @FXML
    private Pane imprimerPane;

    @FXML
    private JFXButton backOrdonnance;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;
    private String formattedString;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		ordonnanceData = FXCollections.observableArrayList();
		hundler = new DbHundler();

		connection = hundler.getConnection();
		String get = "SELECT * from ordonnance";
		try {
			pst = (PreparedStatement) connection.prepareStatement(get);


			ResultSet rs = pst.executeQuery();

			int count =0;

			while(rs.next()){
				count++;
				String id = rs.getString("idordonnance");
				String observation = rs.getString("Observation");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String medicament = rs.getString("Medicament");
				String usage = rs.getString("Usage");
				String CIN = rs.getString("CIN");
				ordonnanceData.add(new Ordonnance(id,CIN,nom,prenom,observation,medicament,usage));
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		CINOrdonnance.setCellValueFactory(cellData -> cellData.getValue().CINPropertyOrdonnance());
		this.nomOrdonnance.setCellValueFactory(cellData -> cellData.getValue().nomPropertyOrdonnance());
		this.prenomOrdonnance.setCellValueFactory(cellData -> cellData.getValue().prenomPropertyOrdonnance());
		this.traitementOrdonnance.setCellValueFactory(cellData -> cellData.getValue().medicamentPropertyOrdonnance());
		this.UsageOrdonnance.setCellValueFactory(cellData -> cellData.getValue().usagePropertyOrdonnance());
		this.observationOrdonnance.setCellValueFactory(cellData -> cellData.getValue().observationPropertyOrdonnance());
		System.out.println(this.ordonnanceData.size());
		ordonnanceTable.setItems(ordonnanceData);

		showOrdonnance(null);

		ordonnanceTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showOrdonnance(newValue));

	}

	private void showOrdonnance(Ordonnance temp){
		if(temp != null){
			this.dosageImprimer.setText(temp.getUsageOrdonnance());
			this.medicamentImprimer.setText(temp.getMedicamentOrdonnance());
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
			formattedString = now.format(formatter);
			this.dateImprimer.setText(formattedString);
		}else{
			this.dosageImprimer.setText(" ");
			this.medicamentImprimer.setText(" ");
			this.dateImprimer.setText(" ");
		}

	}

	@FXML
	public void imprimerAction() throws IOException{
		System.out.println("imprimer");
		imprimerPane = new Pane();
		imprimerPane.getChildren().addAll(this.nameCabinet,this.phoneCabinet,this.EmailCabinet,this.phoneImprimer,this.emailImprimer,this.medicamentImprimer,this.dosageImprimer,this.dateImprimer,this.dateTxtImprimer);
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null && job.showPrintDialog(imprimerBtn.getScene().getWindow())){
		    boolean success = job.printPage(imprimerPane);
		    if (success) {
		        job.endJob();
		        imprimerBtn.getScene().getWindow().hide();
		    }
		}

		this.imprimerBtn.getScene().getWindow().hide();
		Stage ajouterOrdonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Ordannance.fxml"));

        Scene scene = new Scene(root);
        ajouterOrdonnance.setScene(scene);
        ajouterOrdonnance.show();
        ajouterOrdonnance.setResizable(false);


	}

	@FXML
	public void ajouterOrdonnanceAction() throws IOException{
		this.ajouterOrdonnanceButton.getScene().getWindow().hide();
		Stage ajouterOrdonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/ajouterOrdonnance.fxml"));

        Scene scene = new Scene(root);
        ajouterOrdonnance.setScene(scene);
        ajouterOrdonnance.show();
        ajouterOrdonnance.setResizable(false);
	}

	@FXML
	public void deleteOrdonnanceAction(){
		System.out.println("delete ordonnance");
		int selectedIndex = ordonnanceTable.getSelectionModel().getSelectedIndex();
		Ordonnance temp = ordonnanceTable.getSelectionModel().getSelectedItem();
		String delete = "DELETE FROM ordonnance WHERE idordonnance = ?";
		try {
			pst = (PreparedStatement) connection.prepareStatement(delete);
			pst.setString(1, temp.getId());
			pst.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ordonnanceTable.getItems().remove(selectedIndex);
	}

	@FXML
	public void rechercherOrdonnanceAction(){
		ordonnanceTable.getItems().clear();
		ordonnanceTable.refresh();
		ordonnanceData =  FXCollections.observableArrayList();
		if((!this.CINRechercheOrdonnance.getText().equals("")) && (!this.rechercheMedicamentOrdonnance.getText().equals(""))){
			System.out.println("les deux vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from ordonnance where CIN=? and medicament = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, CINRechercheOrdonnance.getText());
				pst.setString(2, this.rechercheMedicamentOrdonnance.getText());



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idordonnance");
					String observation = rs.getString("Observation");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String medicament = rs.getString("medicament");
					String usage = rs.getString("usage");
					String CIN = rs.getString("CIN");
					ordonnanceData.add(new Ordonnance(id,CIN,nom,prenom,observation,medicament,usage));			}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(count != 0){
				ordonnanceTable.setItems(ordonnanceData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Ordonnance");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.CINRechercheOrdonnance.getText()+" et médicamment = "+this.rechercheMedicamentOrdonnance.getText());

				alert.showAndWait();
			}
		}else if(!this.CINRechercheOrdonnance.getText().equals("")){
			System.out.println("cin vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from ordonnance where CIN=?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, CINRechercheOrdonnance.getText());

				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idordonnance");
					String observation = rs.getString("Observation");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String medicament = rs.getString("medicament");
					String usage = rs.getString("usage");
					String CIN = rs.getString("CIN");
					ordonnanceData.add(new Ordonnance(id,CIN,nom,prenom,observation,medicament,usage));
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
				ordonnanceTable.setItems(ordonnanceData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.CINRechercheOrdonnance.getText());

				alert.showAndWait();
			}
		}else if(!this.rechercheMedicamentOrdonnance.getText().equals("")){
			System.out.println("medicament vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from ordonnance";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);



				ResultSet rs = pst.executeQuery();



				while(rs.next()){

					String id = rs.getString("idordonnance");
					String observation = rs.getString("Observation");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String usage = rs.getString("usage");
					String CIN = rs.getString("CIN");
					String medicament = rs.getString("medicament");
					List<String> lineLengths = (List) Stream.of(medicament.split("\n")).collect(Collectors.toList());
					for(int i=0;i<lineLengths.size();i++){
						System.out.println(lineLengths.get(i));
						if(lineLengths.get(i).equals(this.rechercheMedicamentOrdonnance.getText())){
							count++;
							ordonnanceData.add(new Ordonnance(id,CIN,nom,prenom,observation,medicament,usage));
						}
					}



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
				ordonnanceTable.setItems(ordonnanceData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Consultation");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+"type opération = "+this.rechercheMedicamentOrdonnance.getText());

				alert.showAndWait();
			}
		}
	}

	@FXML
	public void backOrdonnanceAction() throws IOException{
		this.backOrdonnance.getScene().getWindow().hide();
		Stage ajouterOrdonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Accueil.fxml"));

        Scene scene = new Scene(root);
        ajouterOrdonnance.setScene(scene);
        ajouterOrdonnance.show();
        ajouterOrdonnance.setResizable(false);
	}

}
