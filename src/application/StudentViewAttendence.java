package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;

import javafx.scene.control.ProgressBar;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

public class StudentViewAttendence implements Initializable{

	Connection con = null;
	String UACID=null;
	Button[] btns = new Button[10];
	Alert error = new Alert(AlertType.ERROR);
	int i=0;
	boolean ss = false;
	String course = null;
	
	//Table View
	@FXML
	private TableView<attendanceview> table;
	
	//Table column
	@FXML
	private TableColumn<attendanceview, String> col_lect;
	@FXML
	private TableColumn<attendanceview, ComboBox<String>> col_date;
	@FXML
	private TableColumn<attendanceview, ComboBox<String>> col_presence;
	//Button

	@FXML
	private Button b1;
	@FXML
	private Button b2;
	@FXML
	private Button b3;
	@FXML
	private Button b4;
	@FXML
	private Button b5;
	@FXML
	private Button b6;
	@FXML
	private Button b7;
	@FXML
	private Button b8;
	@FXML
	private Label courselabel;
	@FXML
	private Label progresslabel;
	@FXML
	private ProgressBar progress;

	
	ObservableList<attendanceview> attend = FXCollections.observableArrayList();
	ArrayList<ComboBox<String>> Attendance = new ArrayList<ComboBox<String>>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
		
			con = DBConnector.getConnection();
			
			disableButton();
			run();
			getCourse();
	
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
			courselabel.setText("");
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void getCourse() {
		disableButton();
		buttoneresest();
		int i = 7;

		String query = "Select CourseID from student_course where StudentID = '" + UACID + "'";

		btns[0] = b1;
		btns[1] = b2;
		btns[2] = b3;
		btns[3] = b4;
		btns[4] = b5;
		btns[5] = b6;
		btns[6] = b7;
		btns[7] = b8;
		
		try {
		
			ResultSet rs = con.createStatement().executeQuery(query);
			
			
			while(rs.next()) {
	
				btns[i].setDisable(false);
				
				btns[i].setText(rs.getString("CourseID") );
				
				i--;
			}
			
			i = 7;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void buttoneresest()
	{
		ss=false;
		
		b1.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b2.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b3.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b4.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b5.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		b6.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		b7.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		b8.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
	}	
	
	public void returnSection1(ActionEvent e) {
		ViewAttendance();
		buttoneresest();
		ss=true;

		b1.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course= b1.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	public void returnSection2(ActionEvent e) {
		ViewAttendance();
		buttoneresest();
		ss=true;

		b2.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b2.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	public void returnSection3(ActionEvent e) {
		ViewAttendance();
		buttoneresest();
		ss=true;

		b3.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b3.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	
	public void returnSection4(ActionEvent e) {
		ViewAttendance();
		buttoneresest();
		ss=true;

		b4.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b4.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	public void returnSection5(ActionEvent e) {
		
		buttoneresest();
		ss=true;

		b5.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b5.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	public void returnSection6(ActionEvent e) {
		
		buttoneresest();
		ss=true;

		b6.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b6.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	public void returnSection7(ActionEvent e) {
		
		buttoneresest();
		ss=true;

		b7.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b7.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	public void returnSection8(ActionEvent e) {
		
		buttoneresest();
		ss=true;

		b8.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b8.getText();
		ViewAttendance();
		courselabel.setText(course);
	}
	
	
	
	
	
	
	
	public void ViewAttendance() {
		

		i = 1;
		double absent=0;
		double total=0;
		double presentpercentage=0;
		if(table.getItems() != null) {
			table.getItems().clear();
		}

		String query1 = "SELECT Date , Presence FROM  attendance WHERE StudentID = '" + UACID + "'and CourseId ='" + course +"' and Presence is not null  ORDER BY date ASC";

		
		try {
			ResultSet rs = con.createStatement().executeQuery(query1);
			if(rs.isBeforeFirst())
			{
				while(rs.next()) {
					String check= rs.getString("Presence");
					if(check.contentEquals("A"))
						{absent++;}
					attend.add(new attendanceview(Integer.toString(i) , rs.getString("Date"),rs.getString("Presence")));
					i++;
					total++;
				
				}
			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error.setContentText(e.getMessage());
			error.showAndWait();
			e.printStackTrace();
		}
		
		table.setItems(attend);
		
		col_lect.setCellValueFactory(new PropertyValueFactory<attendanceview,String>("lec"));
		col_date.setCellValueFactory(new PropertyValueFactory<attendanceview,ComboBox<String>>("date"));
		col_presence.setCellValueFactory(new PropertyValueFactory<attendanceview,ComboBox<String>>("presensce"));
	
		i = 0;

		System.out.println(total);
		System.out.println(absent);
		presentpercentage=(total-absent)/total;
		System.out.println(presentpercentage);
		if(presentpercentage< .75)
			progress.setStyle("-fx-accent: red;");
		if(presentpercentage>= .75)
			progress.setStyle("-fx-accent: green;");
		progress.setProgress(presentpercentage);
		progresslabel.setStyle("-fx-font-weight: BOLD;");
		progresslabel.setText(Double.toString(presentpercentage*100)+"%" );
		
		
	}
	
	public void disableButton() {
		b1.setDisable(true);
		b1.setText("");
		
		b2.setDisable(true);
		b2.setText("");
		
		b3.setDisable(true);
		b3.setText("");
		
		b4.setDisable(true);
		b4.setText("");
		
		b5.setDisable(true);
		b5.setText("");
		
		b6.setDisable(true);
		b6.setText("");
		
		b7.setDisable(true);
		b7.setText("");
		
		b8.setDisable(true);
		b8.setText("");
	}
	
}
