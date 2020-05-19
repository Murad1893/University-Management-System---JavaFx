package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class UpdateAssignment implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String UACID=null;
	String startdate = null;
	
	FileChooser filechooser = new FileChooser();
	FileInputStream fs;
	File file;
	
	@FXML
    private TextArea desc;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<String> titles;

    @FXML
    private ComboBox<String> courses;

    @FXML
    private DatePicker date;

    @FXML
    private Button upload;
	
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
			
			upload.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					file = filechooser.showOpenDialog(upload.getScene().getWindow());
					if (file != null) {
						upload.setText(file.getName());
					}		
				}
			});
			
		} 
		catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ComboBoxButton (ActionEvent e) {
		
		titles.getItems().clear();
		
		String query = "select Resource_num, Resource_title from resourcespace where StaffID = '" + UACID + "' and CourseID = '" + courses.getValue() + "' and Type = 'Assignment' order by Resource_num";
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery(query);
		
			while(rs.next()) {
				titles.getItems().add(rs.getString("Resource_num") + ". " + rs.getString("Resource_title"));	
			}
			titles.setPromptText("Assignment Titles");
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();

		}
		
	}
	
	public void TitleComboBoxButton (ActionEvent e) {
		
		String num = "";
		int i = 0;
		
		if(titles.getValue() != null) {
			while(!(titles.getValue().charAt(i) + "").equals(".") ) {
				
				num = num + titles.getValue().charAt(i);
				System.out.println(num);
				i++;
			}
			
		}
		
		String query = "select StartDate, DueDate, Resource_Description from resourcespace where StaffID = '" + UACID + "' and CourseID = '" + courses.getValue() + "' and Type = 'Assignment' and Resource_num = '"+ num +"'";
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery(query);
			
			if(rs.isBeforeFirst()) {
				rs.next();
				
				date.setValue(LOCAL_DATE(rs.getString("DueDate")));
				startdate = rs.getString("StartDate");
				desc.setText(rs.getString("Resource_Description"));	
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();

		}
		
	}	
	
	public static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}

	public void UpdateAssign(ActionEvent e) {
		
		if(courses.getValue()!=null) {
		
		String num = "";
		int i = 0;
		
		if(date.getValue()!=null) {
		
		if(titles.getValue() != null) {
			while(!(titles.getValue().charAt(i) + "").equals(".") ) {
				
				num = num + titles.getValue().charAt(i);
				System.out.println(num);
				i++;
			}	
		}
		
		String query = "UPDATE resourcespace SET Resource_Description = ?, DueDate = ?, Files = ? WHERE StaffID = ? and CourseID = ? and Type = 'Assignment' and Resource_num = ?";
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, desc.getText());
			
			if(startdate.compareTo(date.getValue().toString()) > 0) {
				System.out.println(startdate + date.getValue().toString());
				error.setContentText("Due date cannot be less than the start date!");
				error.showAndWait();
			}
			
			ps.setString(2, date.getValue().toString());
			
			if (file != null) {
				fs = new FileInputStream(file);
				ps.setBinaryStream(3, (InputStream)fs , (int)file.length());
			}	
			else
			ps.setString(3, null);
			
			ps.setString(4, UACID);
			ps.setString(5, courses.getValue());
			ps.setString(6, num);
			
			ps.execute();
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
		}
		else {
			error.setContentText("Please choose an assignment!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("No value for courses entered yet!");
			error.showAndWait();
		}
	}

	
	public void DeleteAssign(ActionEvent e) {
		
		if(courses.getValue()!=null) {
		
		String num = "";
		int i = 0;
		
		if(date.getValue()!=null) {
		
		if(titles.getValue() != null) {
			while(!(titles.getValue().charAt(i) + "").equals(".") ) {
				
				num = num + titles.getValue().charAt(i);
				System.out.println(num);
				i++;
			}
			
		}
		
		String query = "delete from resourcespace where Resource_num = ? and StaffID = ? and CourseID = ? and Type = 'Assignment'";
		
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, num);
			ps.setString(2, UACID);
			ps.setString(3, courses.getValue());
			
			ps.execute();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ComboBoxButton(e);
		titles.setPromptText("Resource Titles");
		desc.clear();
		
		}
		else {
			error.setContentText("Please choose an assignment!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("No value for courses entered yet!");
			error.showAndWait();
		}
		
	}
	
}