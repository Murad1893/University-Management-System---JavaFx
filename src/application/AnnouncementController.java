package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;


public class AnnouncementController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String UACID=null;
	
	@FXML
	private BorderPane ap;
	
	@FXML
	private BorderPane resource;
	
    @FXML
    private Button Alter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DBConnector.getConnection();		
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
			
			LoadPane("AddAnnouncement");
			LoadPane("UploadAssignment");
		} 
		catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void AddAnnouncement(ActionEvent e) {
		LoadPane("AddAnnouncement");
	}
	
	public void AlterAnnouncement() {
		LoadPane("UpdateAnnouncement");
	}
	
	public void UploadAssignment() {
		LoadPane("UploadAssignment");
	}
	
	public void UploadResource() {
		LoadPane("UploadResource");
	}
	
	public void AlterAssignment() {
		LoadPane("UpdateAssignment");
	}
	
	public void AlterResource() {
		LoadPane("UpdateResource");
	}
	
	public void LoadPane(String ui) {
		Parent root = null;
		
		try {
			root = FXMLLoader.load(getClass().getResource(ui + ".fxml"));
			new FadeIn(root).play();
		}
		
		catch(Exception ex){
			error.setContentText(ex.getMessage());
			error.showAndWait();
			ex.printStackTrace();
		}
		
		
		if(ui.equals("AddAnnouncement") || ui.equals("UpdateAnnouncement"))
			ap.setCenter(root);
		else
			resource.setCenter(root);
	}
	
}
