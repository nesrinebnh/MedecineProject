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
import Model.Paiment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PaimentController implements Initializable {

    @FXML
    private TextField CINRecherchePaiment;

    @FXML
    private TextField rechercheTitrePaiment;

    @FXML
    private JFXButton rechercherPaiment;

    @FXML
    private TableView<Paiment> paimentTable;

    @FXML
    private TableColumn<Paiment, String> CINPaiment;

    @FXML
    private TableColumn<Paiment, String> nomPaiment;

    @FXML
    private TableColumn<Paiment, String> prenomPaiment;

    @FXML
    private TableColumn<Paiment, String> TitrePaiment;

    @FXML
    private TableColumn<Paiment, String> datePaiment;

    @FXML
    private TableColumn<Paiment, String> totalePaiment;

    @FXML
    private TableColumn<Paiment, String> observationPaiment;

    @FXML
    private JFXButton deletePaiment;

    @FXML
    private JFXButton editPaiment;

    @FXML
    private JFXButton ajouterPaimentBouton;

    @FXML
    private Pane imprimerPane;

    @FXML
    private Text montantPaiment;

    @FXML
    private Label titreMontant;

    @FXML
    private Label nameCabinet;

    @FXML
    private Label EmailCabinet;

    @FXML
    private Label phoneCabinet;

    @FXML
    private Label dateTxtImprimer;

    @FXML
    private Label dateImprimer;

    @FXML
    private Button imprimerPaimentBtn;

    @FXML
    private Label emailImprimer;

    @FXML
    private Label phoneImprimer;

    @FXML
    private JFXButton backPaiment;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;
    private String formattedString;

    private ObservableList<Paiment> paimentData;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		paimentData = FXCollections.observableArrayList();
		hundler = new DbHundler();

		connection = hundler.getConnection();
		String get = "SELECT * from paiment";
		try {
			pst = (PreparedStatement) connection.prepareStatement(get);


			ResultSet rs = pst.executeQuery();

			int count =0;

			while(rs.next()){
				count++;
				String id = rs.getString("idpaiment");
				String CIN = rs.getString("CIN");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String titre = rs.getString("titre");
				String date = rs.getString("date");
				String totale = rs.getString("totale");
				String observation = rs.getString("observation");


				paimentData.add(new Paiment(id,CIN,nom,prenom,titre,date,totale,observation));
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		this.CINPaiment.setCellValueFactory(cellData -> cellData.getValue().CINProperty());
		this.nomPaiment.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		this.prenomPaiment.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		this.TitrePaiment.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
		this.totalePaiment.setCellValueFactory(cellData -> cellData.getValue().montant());
		this.datePaiment.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		this.observationPaiment.setCellValueFactory(cellData -> cellData.getValue().observationProperty());
		this.paimentTable.setItems(paimentData);

		showPaiment(null);

		this.paimentTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showPaiment(newValue));

	}

	private void showPaiment(Paiment temp){
		if(temp != null){
			this.titreMontant.setText(temp.getTitre());
			this.montantPaiment.setText(temp.getMontant()+"Da");
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
			formattedString = now.format(formatter);
			this.dateImprimer.setText(formattedString);
		}else{
			this.titreMontant.setText(" ");
			this.montantPaiment.setText(" ");
			this.dateImprimer.setText(" ");
		}

	}

	@FXML
	public void imprimerAction() throws IOException{
		System.out.println("imprimer");
		imprimerPane = new Pane();
		imprimerPane.getChildren().addAll(this.nameCabinet,this.phoneCabinet,this.EmailCabinet,this.phoneImprimer,this.emailImprimer,this.titreMontant,this.montantPaiment,this.dateImprimer,this.dateTxtImprimer);
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null && job.showPrintDialog(this.imprimerPaimentBtn.getScene().getWindow())){
		    boolean success = job.printPage(imprimerPane);
		    if (success) {
		        job.endJob();
		        this.imprimerPaimentBtn.getScene().getWindow().hide();
		    }
		}

		this.imprimerPaimentBtn.getScene().getWindow().hide();
		Stage ajouterOrdonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Paiment.fxml"));

        Scene scene = new Scene(root);
        ajouterOrdonnance.setScene(scene);
        ajouterOrdonnance.show();
        ajouterOrdonnance.setResizable(false);


	}

	@FXML
	public void ajouterPaimentBoutonAction() throws IOException{
		this.ajouterPaimentBouton.getScene().getWindow().hide();
		Stage consultation = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/AjouterPaiment.fxml"));

        Scene scene = new Scene(root);
        consultation.setScene(scene);
        consultation.show();
        consultation.setResizable(false);
	}

	@FXML
	public void deletePaimentAction(){
		System.out.println("delete consultation");
		int selectedIndex = this.paimentTable.getSelectionModel().getSelectedIndex();
		Paiment temp = this.paimentTable.getSelectionModel().getSelectedItem();
		String delete = "DELETE FROM paiment WHERE idpaiment = ?";
		try {
			pst = (PreparedStatement) connection.prepareStatement(delete);
			System.out.println(temp.getId());
			pst.setString(1, temp.getId());
			pst.executeUpdate();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.paimentTable.getItems().remove(selectedIndex);
	}

	@FXML
	public void backPaimentAction() throws IOException{
		this.backPaiment.getScene().getWindow().hide();
		Stage ajouterPaiment = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Accueil.fxml"));

        Scene scene = new Scene(root);
        ajouterPaiment.setScene(scene);
        ajouterPaiment.show();
        ajouterPaiment.setResizable(false);
	}

	@FXML
	public void rechercherPaimentAction(){
		this.paimentTable.getItems().clear();
		this.paimentTable.refresh();
		this.paimentData =  FXCollections.observableArrayList();
		if((!this.rechercheTitrePaiment.getText().equals("")) && (!this.CINRecherchePaiment.getText().equals(""))){
			System.out.println("les deux vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from ordonnance where CIN=? and titre = ?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.CINRecherchePaiment.getText());
				pst.setString(2, this.rechercheTitrePaiment.getText());



				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idpaiment");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String titre = rs.getString("titre");
					String date = rs.getString("date");
					String totale = rs.getString("totale");
					String observation = rs.getString("observation");


					paimentData.add(new Paiment(id,CIN,nom,prenom,titre,date,totale,observation));
					}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(count != 0){
				this.paimentTable.setItems(this.paimentData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Paiment");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.CINRecherchePaiment.getText()+" et médicamment = "+this.rechercheTitrePaiment.getText());

				alert.showAndWait();
			}
		}else if(!this.CINRecherchePaiment.getText().equals("")){
			System.out.println("cin vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from paiment where CIN=?";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);
				pst.setString(1, this.CINRecherchePaiment.getText());

				ResultSet rs = pst.executeQuery();



				while(rs.next()){
					count++;
					String id = rs.getString("idpaiment");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String titre = rs.getString("titre");
					String date = rs.getString("date");
					String totale = rs.getString("totale");
					String observation = rs.getString("observation");


					paimentData.add(new Paiment(id,CIN,nom,prenom,titre,date,totale,observation));
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
				this.paimentTable.setItems(this.paimentData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Paiment");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour CIN = "+this.CINRecherchePaiment.getText());

				alert.showAndWait();
			}
		}else if(!this.rechercheTitrePaiment.getText().equals("")){
			System.out.println("medicament vide");
			int count =0;

			connection = hundler.getConnection();
			String get = "SELECT * from paiment";
			try {
				pst = (PreparedStatement) connection.prepareStatement(get);



				ResultSet rs = pst.executeQuery();



				while(rs.next()){

					String id = rs.getString("idpaiment");
					String CIN = rs.getString("CIN");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String titre = rs.getString("titre");
					String date = rs.getString("date");
					String totale = rs.getString("totale");
					String observation = rs.getString("observation");


					paimentData.add(new Paiment(id,CIN,nom,prenom,titre,date,totale,observation));



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
				this.paimentTable.setItems(this.paimentData);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Paiment");
				alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
				alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+"type opération = "+this.rechercheTitrePaiment.getText());

				alert.showAndWait();
			}
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Paiment");
			alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
			alert.setContentText("je veux vous informer que aucun resultat n'a été trouvé pour "+"type opération = "+this.rechercheTitrePaiment.getText());

			alert.showAndWait();
		}
	}



}
