package FXMLController;

import java.io.IOException;
import java.net.URL;
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

public class AjouterOrdonnance implements Initializable {

    @FXML
    private JFXButton enregistrerOrdonnance;

    @FXML
    private JFXButton annulerOrdonnance;

    @FXML
    private TextArea medicammentAjouterOrdonnance;

    @FXML
    private TextArea observationAjouterOrdonnance;

    @FXML
    private TextField CINAjouterOrdonnance;

    @FXML
    private TextField nomAjouterOrdannance;

    @FXML
    private TextField prenomAjouterOrdonnance;

    @FXML
    private TextArea usageAjouterOrdonnance;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		hundler = new DbHundler();

	}

	@FXML
	public void enregisterOrdonnanceAction() throws IOException{
		if(this.validerInput()==false){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Ordonnance");
			alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
			alert.setContentText("les champs qui sont en rouges indique que vous n'avez inserer l'information qu'elle est nécessaire!");

			alert.showAndWait();
		}else{
			String observation ="'"+ observationAjouterOrdonnance.getText()+"'";
			String CIN = "'"+CINAjouterOrdonnance.getText()+"'";
			String medicament = "'"+medicammentAjouterOrdonnance.getText()+"'";
			String usage = "'"+usageAjouterOrdonnance.getText()+"'";
			String nom = "'"+nomAjouterOrdannance.getText()+"'";
			String prenom ="'"+ prenomAjouterOrdonnance.getText()+"'";

			String insert = "INSERT INTO `cabinetdemedecine`.`ordonnance` (`CIN`, `nom`, `prenom`, `medicament`, `usage`, `observation`) VALUES ("+CIN+","+ nom+","+ prenom+","+ medicament+","+usage+","+ observation+")";
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

			this.enregistrerOrdonnance.getScene().getWindow().hide();
			Stage signUp = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Ordannance.fxml"));

	        Scene scene = new Scene(root);
	        signUp.setScene(scene);
	        signUp.show();
	        signUp.setResizable(false);
		}





		/*List<String> lineLengths = (List) Stream.of(observation.split("\n")).collect(Collectors.toList());
		for(int i=0;i<lineLengths.size();i++){
			System.out.print(lineLengths.get(i)+"\n");
		}*/
	}

	@FXML
	public void annulerOrdonnanceAction() throws IOException{
		System.out.println("salam ordonnance");

		this.annulerOrdonnance.getScene().getWindow().hide();
		Stage consultation = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Ordannance.fxml"));

        Scene scene = new Scene(root);
        consultation.setScene(scene);
        consultation.show();
        consultation.setResizable(false);

		/*Pane receiptPane = new Pane();
		Label cin = new Label();
		cin.setText(CINConsultation.getText());
	    receiptPane.getChildren().addAll(cin
	    );
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null && job.showPrintDialog(annulerConsultation.getScene().getWindow())){
		    boolean success = job.printPage(receiptPane);
		    if (success) {
		        job.endJob();
		        annulerConsultation.getScene().getWindow().hide();
		    }
		}*/
	}

	private boolean validerInput(){
		boolean valide = true;
		if(this.CINAjouterOrdonnance.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			this.CINAjouterOrdonnance.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.CINAjouterOrdonnance.setText("vous devez inserer un CIN");
			valide = false;
		}
		if(this.nomAjouterOrdannance.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			this.nomAjouterOrdannance.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.nomAjouterOrdannance.setText("vous devez inserer un nom");
			valide = false;
		}
		if(this.prenomAjouterOrdonnance.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			this.prenomAjouterOrdonnance.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.prenomAjouterOrdonnance.setText("vous devez inserer un prenom");
			valide = false;
		}
		if(this.medicammentAjouterOrdonnance.getText().equals("")) {
			 Paint value = Paint.valueOf("660029");
			this.medicammentAjouterOrdonnance.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.prenomAjouterOrdonnance.setText("vous devez inserer un médicament");
			valide = false;
		}
		if(this.usageAjouterOrdonnance.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			this.usageAjouterOrdonnance.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.usageAjouterOrdonnance.setText("vous devez inserer un usage");
			valide = false;
		}

		if(this.observationAjouterOrdonnance.getText().equals("")){
			Paint value = Paint.valueOf("660029");
			this.observationAjouterOrdonnance.setStyle("-fx-control-inner-background: #"+value.toString().substring(2));
			this.observationAjouterOrdonnance.setText("vous devez inserer une observation");
			valide = false;
		}

		return valide;
	}


}
