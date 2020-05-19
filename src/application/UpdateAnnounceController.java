package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


public class UpdateAnnounceController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String UACID=null;
	
	@FXML
    private TextArea announcement;

    @FXML
    private Button update;

    @FXML
    private Button view;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<String> courses;

    @FXML
    private ComboBox<String> titles;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DBConnector.getConnection();
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
			
			String query = "select DISTINCT sc.CourseID from staff_course sc where sc.StaffID = '"+ UACID +"' order by sc.CourseID ASC";
			ResultSet rs = con.createStatement().executeQuery(query);
			
			while(rs.next()) {
				courses.getItems().add(rs.getString("sc.CourseID"));	
			}
			courses.setPromptText("Courses");
			
		} 
		catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
		
		}
	}
	
	public void ComboBoxButton (ActionEvent e) {
		
		titles.getItems().clear();
		
		String query = "select ann_num, anntitle from Announcement where StaffID = '" + UACID + "' and CourseID = '" + courses.getValue() + "' order by Ann_num";
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery(query);
			
			while(rs.next()) {
				titles.getItems().add(rs.getString("ann_num") + ". " + rs.getString("anntitle"));	
			}
			titles.setPromptText("Announcement Titles");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();

		}
		
	}
	
	public void UpdateAnnouncement(ActionEvent e) {
		
		String num = "";
	
		int i = 0;
		
		if(titles.getValue() != null) {
			while(titles.getValue().charAt(i) != '.') {
				System.out.println("l");
				num = num + titles.getValue().charAt(i);
				i++;
			}
		}
		
		String query = "update Announcement set Announcement = ? where Ann_num = ? and StaffID = ? and CourseID = ?";
	
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, announcement.getText());
			ps.setString(2, num);
			ps.setString(3, UACID);
			ps.setString(4, courses.getValue());
			
			ps.execute();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		}
		
		ComboBoxButton(e);
		titles.setPromptText("Announcement Titles");
		announcement.clear();
		
	}
	
	public void DeleteAnnouncement(ActionEvent e) {

		String num = "";
		int i = 0;
		
		if(titles.getValue() != null) {
			while(!(titles.getValue().charAt(i) + "").equals(".") ) {			
				num = num + titles.getValue().charAt(i);
				i++;
			}
			
		}
		System.out.println(num);
		String query = "delete from announcement where Ann_num = ? and StaffID = ? and CourseID = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, num);
			ps.setString(2, UACID);
			ps.setString(3, courses.getValue());
			
			ps.execute();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		}
		
		ComboBoxButton(e);
		titles.setPromptText("Announcement Titles");
		announcement.clear();
		
	}	
	
	public void ViewAnnouncement() {
		String num = null;
		
		if(titles.getValue() != null)
		num = titles.getValue().charAt(0) + "";		
		
		String query = "select Announcement from Announcement where Ann_num = '" + num + "' and StaffID = '" + UACID + "' and CourseID = '" + courses.getValue() + "'";

		try {
		
			ResultSet rs = con.createStatement().executeQuery(query);
		
			while(rs.next()) {
				announcement.setText(rs.getString("Announcement"));
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
