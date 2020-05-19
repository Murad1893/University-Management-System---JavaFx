package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AddAnnounceController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String UACID=null;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	
	@FXML
    private Button submit;
	
	@FXML
    private TextField title;
	
	@FXML
    private TextArea announcement;
	
	@FXML
    private ComboBox<String> courses;
	
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
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Submit(ActionEvent e) {
			
		if(courses.getValue()!=null) {
		
			String query1 = "INSERT INTO `announcement` (`StaffID`, `CourseID`, `Ann_num`, `AnnTitle` ,`Announcement`, `DateAdded`) VALUES (?, ?, ?, ?, ?, ?)";
			String query2 = "select max(Ann_num) from Announcement where StaffID = '" + UACID + "'";
			
			PreparedStatement ps = null;
			try {
				
				ResultSet rs = con.createStatement().executeQuery(query2);
				rs.next();	
				
				ps = con.prepareStatement(query1);
				ps.setString(1, UACID);
				ps.setString(2, courses.getValue());
				ps.setString(3, Integer.toString((rs.getInt("max(Ann_num)") + 1)));
				ps.setString(4, title.getText());
				ps.setString(5, announcement.getText());
				ps.setString(6, dateFormat.format(date));
				ps.execute();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
		else {
			error.setContentText("No course selected yet!");
			error.showAndWait();
		}
			
			title.clear();
			announcement.clear();
			courses.setPromptText("Courses");
		
		
		}
	
}
