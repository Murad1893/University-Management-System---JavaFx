package application;

import java.net.URL; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Teachercourseallotmentcontroller implements Initializable{
	
	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	
	//Table View
	@FXML
	private TableView<Teachercourseallotment> table;
	
	//Table column
	@FXML
	private TableColumn<Teachercourseallotment,String> col_alteachid;
	@FXML
	private TableColumn<Teachercourseallotment,String> col_alsecid;
	@FXML
	private TableColumn<Teachercourseallotment,String> col_alcourseid;


	
	
	
	//Text Field 
	@FXML
	private TextField alteachid;
	@FXML
	private TextField secdeptcode;
	@FXML
	private TextField alcourseid;
	@FXML
	private Text alteachidlabel;
	@FXML
	private Text secdeptcodelabel;
	@FXML
	private Text alcourseidlabel;

	

	
	@FXML
	private Button Insert;

	@FXML
	private Button Delete;
	@FXML
	private Button View;
	@FXML
	private Button ViewAll;
	
	ObservableList<Teachercourseallotment> courselist = FXCollections.observableArrayList();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DBConnector.getConnection();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event){
				// TODO Auto-generated method stub
				try {
					if(event.getClickCount() == 1) {
						alteachid.setText(table.getSelectionModel().getSelectedItem().getStaffID());
						secdeptcode.setText(table.getSelectionModel().getSelectedItem().getSectionID());
						alcourseid.setText(table.getSelectionModel().getSelectedItem().getCourseID());
					
						
					
					}
				}
				catch(NullPointerException e) {
					error.setContentText("No valid value in table!");
					error.showAndWait();
				}
			}

		});
		
		
	}
	public void defaultlabel() 
	{
	
		alteachidlabel.setText("Teacher ID");
		alteachidlabel.setFill(Color.web("#042954"));
		
		secdeptcodelabel.setText("Section ID");
		secdeptcodelabel.setFill(Color.web("#042954"));
	
		alcourseidlabel.setText("Course ID");
		alcourseidlabel.setFill(Color.web("#042954"));
		
	}
	
	
	public void InsertStudentRecord(ActionEvent e) {

		
		defaultlabel();
		String query = "INSERT INTO `staff_course`(`StaffID`, `SectionID`, `CourseID`)  VALUES (?, ?, ?)"; 
		String query1 = "select * from staff_course where SectionID = '" + secdeptcode.getText() + "' and CourseID = '" + alcourseid.getText() + "'";
		
		try {
		
		ResultSet rs = con.createStatement().executeQuery(query1);
		
		PreparedStatement ps = null;
		if(StringUtils.isNullOrEmpty(alteachid.getText()) || StringUtils.isNullOrEmpty(secdeptcode.getText()) || StringUtils.isNullOrEmpty(alcourseid.getText()) ) {
			if(StringUtils.isNullOrEmpty(alteachid.getText()))
			{
				
				alteachidlabel.setText("Teacher ID*");
				alteachidlabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(secdeptcode.getText()))
			{
				
				secdeptcodelabel.setText("Section ID*");
				secdeptcodelabel.setFill(Color.web("#d50000"));
			}	
			if(StringUtils.isNullOrEmpty(alcourseid.getText()))
			{
			
				alcourseidlabel.setText("Course ID*");
				alcourseidlabel.setFill(Color.web("#d50000"));
			}
			
		}
		
		else if(rs.next() == true) {
			error.setContentText("A teacher is already teaching this course to this class!");
			error.showAndWait();
		}
		
		else
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, alteachid.getText());
			ps.setString(2, secdeptcode.getText());
			ps.setString(3, alcourseid.getText());
	
			ps.execute();
			ViewStudentRecord(e);
		
			
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		}
		
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
	}
	
	public void DeleteStudentRecord(ActionEvent e) {


		defaultlabel();
		String query = "delete from `staff_course` where `StaffID` LIKE ? AND `SectionID` LIKE ?  AND `CourseID` LIKE ?";
		
		PreparedStatement ps = null;
		if(StringUtils.isNullOrEmpty(alteachid.getText()) || StringUtils.isNullOrEmpty(secdeptcode.getText()) || StringUtils.isNullOrEmpty(alcourseid.getText()) ) {
			if(StringUtils.isNullOrEmpty(alteachid.getText()))
			{
				
				alteachidlabel.setText("Teacher ID*");
				alteachidlabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(secdeptcode.getText()))
			{
				
				secdeptcodelabel.setText("Section ID*");
				secdeptcodelabel.setFill(Color.web("#d50000"));
			}	
			if(StringUtils.isNullOrEmpty(alcourseid.getText()))
			{
				
				alcourseidlabel.setText("Course ID*");
				alcourseidlabel.setFill(Color.web("#d50000"));
			}
			
		}
		
		else {
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, alteachid.getText());
			ps.setString(2, secdeptcode.getText());
			ps.setString(3, alcourseid.getText());
			
			if(searchrecord()) {
				ps.execute();
				
				ViewStudentRecord(e);
			}	
			
			else {
				error.setContentText("Record not found!");
				error.showAndWait();
			}
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		}
	
	
	}
	
	public void RefreshRecord(ActionEvent e) {
		
		alteachid.clear();
		secdeptcode.clear();
		alcourseid.clear();
		defaultlabel();
	}
	
	boolean searchrecord() {
		try {
			
			String query = "select * from `staff_course` where `StaffID` LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, alteachid.getText());
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}
			
			else {
				return true;			
			}
			
		} 
		catch (SQLException e1) {
			return false;
		} 
	}
	
	public void SearchStudentRecord(ActionEvent e) {
		defaultlabel();
		
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			String query = "select * from `staff_course` where `StaffID` LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, alteachid.getText());
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}
			
			else {
				while(rs.next()) {	
					courselist.add(new Teachercourseallotment(rs.getString("StaffID"), rs.getString("SectionID"),rs.getString("CourseID")));				}
			}
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		} 
		
		col_alteachid.setCellValueFactory(new PropertyValueFactory<>("staffID"));
		col_alsecid.setCellValueFactory(new PropertyValueFactory<>("sectionID"));
		col_alcourseid.setCellValueFactory(new PropertyValueFactory<>("courseID"));

				
		table.setItems(courselist);
		
		
	
	}
	
	public void ViewStudentRecord(ActionEvent Event) {
		defaultlabel();
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `staff_course`");
			while(rs.next()) {
				courselist.add(new Teachercourseallotment(rs.getString("StaffID"), rs.getString("SectionID"),rs.getString("CourseID")));
			}
			}
			
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		
		col_alteachid.setCellValueFactory(new PropertyValueFactory<>("staffID"));
		col_alsecid.setCellValueFactory(new PropertyValueFactory<>("sectionID"));
		col_alcourseid.setCellValueFactory(new PropertyValueFactory<>("courseID"));


			
		table.setItems(courselist);
	}

}
