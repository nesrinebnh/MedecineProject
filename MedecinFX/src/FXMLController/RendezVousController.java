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
import Model.Ordonnance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RendezVousController implements Initializable {

    @FXML
    private DatePicker dateRendezVous;

    @FXML
    private ComboBox<String> timeRendezVous;

    @FXML
    private JFXButton reserverRendezVous;

    @FXML
    private TextField nomRendezVous;

    @FXML
    private TextField prenomRendezVous;

    @FXML
    private JFXButton backButton;

    private Connection connection;
    private DbHundler hundler;
    private PreparedStatement pst;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> list =FXCollections.observableArrayList("8PM", "9PM","10PM","11PM","12PM","13PM","14PM");
		System.out.println(list.size());
		timeRendezVous.setItems(list);

		hundler = new DbHundler();



	}

	@FXML
	public void reserverAction() throws IOException{
		LocalDate localDate = this.dateRendezVous.getValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/LLLL/dd");
		String formattedString = localDate.format(formatter);
		String time = this.timeRendezVous.getValue();
		System.out.println(formattedString+" "+time);

		int count =0;

		connection = hundler.getConnection();
		String get = "SELECT * from rendezvous where date=? and time = ?";
		try {
			pst = (PreparedStatement) connection.prepareStatement(get);
			pst.setString(1, formattedString);
			pst.setString(2, time);

			ResultSet rs = pst.executeQuery();

			while(rs.next())
				count++;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(count == 0){
			formattedString ="'"+ formattedString+"'";
			time ="'"+ time+"'";
			String nom = "'"+nomRendezVous.getText()+"'";
			String prenom ="'"+ prenomRendezVous.getText()+"'";
			String insert = "INSERT INTO `cabinetdemedecine`.`rendezvous` (`date`, `time`, `nom`, `prenom`) VALUES ("+formattedString+","+ time+","+ nom+","+ prenom+")";
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

		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Ordonnance");
			alert.setHeaderText("je devrai dire bonjour ou bonsoir ^^");
			alert.setContentText("Vous ne pouvez pas réservé aujourd'hui");

			alert.showAndWait();
		}

		this.reserverRendezVous.getScene().getWindow().hide();
		Stage signUp = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/RendezVous.fxml"));

        Scene scene = new Scene(root);
        signUp.setScene(scene);
        signUp.show();
        signUp.setResizable(false);
	}

	@FXML
	public void backRendezVousAction() throws IOException{
		this.backButton.getScene().getWindow().hide();
		Stage ajouterOrdonnance = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLController/Accueil.fxml"));

        Scene scene = new Scene(root);
        ajouterOrdonnance.setScene(scene);
        ajouterOrdonnance.show();
        ajouterOrdonnance.setResizable(false);
	}

}
