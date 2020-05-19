package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class UploadResourceController implements Initializable{

	@FXML
    private Button upload;

    @FXML
    private TextField title;

    @FXML
    private TextArea desc;

    @FXML
    private Button submit;

    @FXML
    private ComboBox<String> courses;
	
	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String UACID=null;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	
 	FileChooser filechooser = new FileChooser();
	FileInputStream fs;
	File file;
	
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
	
	public void submit(ActionEvent e) {
		
		if(courses.getValue()!=null) {
		
		String query = "INSERT INTO `resourcespace` (`StaffID`, `CourseID`, `Resource_num`, `Resource_title`, `Resource_Description`, `Type`, `StartDate`, `DueDate`, `Files`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String query1 = "select max(Resource_num) from `resourcespace` where StaffID = '" + UACID + "' and Type = 'Resource'";
		PreparedStatement ps = null;
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery(query1);
			rs.next();
			
			ps = con.prepareStatement(query);
			
			ps.setString(1, UACID);
			ps.setString(2, courses.getValue());
			ps.setString(3, Integer.toString((rs.getInt("max(Resource_num)") + 1)));
			ps.setString(4, title.getText());
			ps.setString(5, desc.getText());
			ps.setString(6, "Resource");
			ps.setString(7, dateFormat.format(date));
			ps.setString(8, null);
			
			if(file!=null) {
			fs = new FileInputStream(file);
			ps.setBinaryStream(9, (InputStream)fs , (int)file.length());
			}
			else ps.setString(9, null);
			
			ps.execute();
			
		}
		catch(SQLException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
		}
		else {
			error.setContentText("No course entered yet!");
			error.showAndWait();
		}
	}
	
	
}
