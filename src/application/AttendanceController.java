package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AttendanceController implements Initializable{

	Connection con = null;
	String UACID=null;
	String section = null;
	Button[] btns = new Button[10];
	String conv_section = null;
	Alert error = new Alert(AlertType.ERROR);
	int i=0;
	
	//Table View
	@FXML
	private TableView<Att> table;
	
	//Table column
	@FXML
	private TableColumn<Att, String> col_stdid;
	@FXML
	private TableColumn<Att, ComboBox<String>> col_presence;

	@FXML
	private Label course_label;
	
	//Button

	@FXML
	private Button section1;
	@FXML
	private Button section2;
	@FXML
	private Button section3;
	@FXML
	private Button section4;
	@FXML
	private Button section5;
	@FXML
	private Button section6;
	@FXML
	private Button section7;
	
	@FXML
	private Button Add;
	@FXML
	private Button Update;
	@FXML
	private Button Delete;
	@FXML
	private Button View;
	@FXML
	private Button Refresh;
	
	//MenuButton
	@FXML
	private ComboBox<String> Course;
	
	@FXML
	private DatePicker date;
	
	ObservableList<Att> attend = FXCollections.observableArrayList();
	ArrayList<ComboBox<String>> Attendance = new ArrayList<ComboBox<String>>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
			con = DBConnector.getConnection();
			disableButton();
			run();
	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		try {
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
			
			String query1 = "select DISTINCT courseid from staff_course where StaffID = '" + UACID + "'";
			
			ResultSet rs = con.createStatement().executeQuery(query1);
			
			while(rs.next()) {
				Course.getItems().add(rs.getString("courseid"));	
				Course.setPromptText("Course");
			}
			
			
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getCourse(ActionEvent e) {
		
		disableButton();
		String course = Course.getValue();
		int i = 6;
		String query1 = "Select DISTINCT sectionid from staff_course where StaffID = '" + UACID + "' AND CourseID = '" + course + "'";
		String query2 = "SELECT `CourseName` FROM `course` WHERE `CourseCode` LIKE ?";
		
		btns[0] = section1;
		btns[1] = section2;
		btns[2] = section3;
		btns[3] = section4;
		btns[4] = section5;
		btns[5] = section6;
		btns[6] = section7;
		
		try {
			ResultSet rs = con.createStatement().executeQuery(query1);
			PreparedStatement ps = null;
			
			ps = con.prepareStatement(query2);
			ps.setString(1, Course.getValue());
			
			while(rs.next()) {
	
				btns[i].setDisable(false);
				
				btns[i].setText(rs.getString("sectionid").charAt(0) + "" + (char)(rs.getString("sectionid").charAt(5) - '0' + 64) + " - " + rs.getString("sectionid").substring(1,5));
				
				i--;
			}
			
			i = 6;
			
			ps.execute();
			rs = ps.getResultSet();
			
			if(rs.next()) {
				course_label.setText(rs.getString("coursename"));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void returnSection1(ActionEvent e) {
		buttoneresest();
		section1.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section1.getText();
	}
	
	public void returnSection2(ActionEvent e) {
		buttoneresest();
		section2.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section2.getText();
	}
	
	public void returnSection3(ActionEvent e) {
		buttoneresest();
		section3.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section3.getText();
	}
	
	public void returnSection4(ActionEvent e) {
		buttoneresest();
		section4.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section4.getText();
	}
	
	public void returnSection5(ActionEvent e) {
		buttoneresest();
		section5.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section5.getText();
	}
	public void returnSection6(ActionEvent e) {
		buttoneresest();
		section6.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section6.getText();
	}
	
	public void returnSection7(ActionEvent e) {
		buttoneresest();
		section7.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section7.getText();
	}
	
	public void AddAttendance() {
		
		if(Course.getValue()!=null) {
		
		if(!StringUtils.isNullOrEmpty(section)) {
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		if(date.getValue() != null) {
		//conv_section = section.charAt(0) + section.substring(1,5)
		
		String query1 = "INSERT INTO `attendance` (`StudentID`, `CourseID`, `Date`, `Presence`) VALUES (?,?,?,?)";
		String query2 = "SELECT s.StdID FROM student s, student_course sc where s.StdID = sc.StudentID and s.Sec_ID = '" + conv_section + "' and sc.CourseID = '" + Course.getValue() +"'";
		
		PreparedStatement ps = null;
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery(query2);
			
			while(rs.next()) {
							
				ps = con.prepareStatement(query1);
				
				ps.setString(1, rs.getString("s.StdID"));
				ps.setString(2, Course.getValue());
				ps.setString(3, date.getValue().toString());
				ps.setString(4, null);
				
				ps.execute();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ViewAttendance();
		}
		else {
			error.setContentText("Date not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
	}
	
	public void DeleteAttendance() {
		
		if(Course.getValue()!=null) {
		
		if(!StringUtils.isNullOrEmpty(section)) {
			
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		if(date.getValue() != null) {
		
		String query = "delete from attendance where CourseID = ? and Date = ? and StudentID in (select student.StdID from student where student.Sec_ID = ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1,Course.getValue());
			ps.setString(2,date.getValue().toString());
			ps.setString(3, conv_section);
			
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
			e.printStackTrace();
		}
		
		ViewAttendance();
		}
		else {
			error.setContentText("Date not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
	}
	
	public void UpdateAttendance() {
		
		i=0;
		
		if(Course.getValue()!=null) {
		
		if(!StringUtils.isNullOrEmpty(section)) {	
			
		String query1 = "SELECT s.StdID FROM student s, student_course sc where s.StdID = sc.StudentID and s.Sec_ID = '" + conv_section + "' and sc.CourseID = '" + Course.getValue() + "'";
		String query2 = "UPDATE `attendance` SET `Presence` = ? WHERE `attendance`.`StudentID` = ? AND `attendance`.`CourseID` = ? AND `attendance`.`Date` = ?";
		
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		if(date.getValue() != null) {
		
		PreparedStatement ps = null;
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery(query1);
			while(rs.next()) {
		
				ps = con.prepareStatement(query2);
				
				if(!StringUtils.isNullOrEmpty(Attendance.get(i).getValue()))	
					ps.setString(1, Attendance.get(i).getValue());
				else
					ps.setString(1, null);
				ps.setString(2, rs.getString("s.StdID"));
				ps.setString(3, Course.getValue());
				ps.setString(4, date.getValue().toString());
				
				ps.execute();
			
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error.setContentText(e.getMessage());
			error.showAndWait();
			e.printStackTrace();
		}
		
		ViewAttendance();
		}
		else {
			error.setContentText("Date not selected yet!");
			error.showAndWait();
		}
		}
		
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
	}
	
	public void ViewAttendance() {
		
		i = 0;
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		Attendance.clear();
		
		if(Course.getValue()!=null) {
			
		if(!StringUtils.isNullOrEmpty(section)) {	
		
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		if(date.getValue() != null) {
		
		String query1 = "SELECT s.StdID, a.Presence FROM student s, student_course sc, attendance a where s.StdID = sc.StudentID and s.Sec_ID = '" + conv_section + "' and sc.CourseID = '" + Course.getValue() +"' and a.StudentID = s.StdID and a.date = '" + date.getValue().toString() + "'";
		
		try {
			ResultSet rs = con.createStatement().executeQuery(query1);
			System.out.println("l");
			while(rs.next()) {
	
				//ComboBox<String> Attendance = new ComboBox<String>();
	
				Attendance.add(new ComboBox<String>());
				Attendance.get(i).getItems().add("A");
				Attendance.get(i).getItems().add("P");
				Attendance.get(i).getItems().add("L");
				
				if(!StringUtils.isNullOrEmpty(rs.getString("a.Presence")))	
				Attendance.get(i).setValue(rs.getString("a.Presence"));
				else 
					Attendance.get(i).valueProperty().set(null);
				
				attend.add(new Att(rs.getString("s.StdID"), Attendance.get(i)));
				
				i++;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error.setContentText(e.getMessage());
			error.showAndWait();
			e.printStackTrace();
		}
		
		table.setItems(attend);
		
		col_stdid.setCellValueFactory(new PropertyValueFactory<Att,String>("studentid"));
		col_presence.setCellValueFactory(new PropertyValueFactory<Att,ComboBox<String>>("presence"));
	
		i = 0;
		
		}
		else {
			error.setContentText("Date not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
		
	}
	public void buttoneresest()
	{
		
		section1.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section2.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section3.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section4.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section5.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		section6.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		section7.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
	
	}
	
	public void disableButton() {
		buttoneresest();
		section1.setDisable(true);
		section1.setText("");
		
		section2.setDisable(true);
		section2.setText("");
		
		section3.setDisable(true);
		section3.setText("");
		
		section4.setDisable(true);
		section4.setText("");
		
		section5.setDisable(true);
		section5.setText("");
		
		section6.setDisable(true);
		section6.setText("");
		
		section7.setDisable(true);
		section7.setText("");
	}
	
}
