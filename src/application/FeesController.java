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


public class FeesController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	
	//Table View
	@FXML
	private TableView<Fees> table;
	
	//Table column
	@FXML
	private TableColumn<Fees,String> col_deptcode;
	@FXML
	private TableColumn<Fees,String> col_fee;

	
	
	
	//Text Field 
	@FXML
	private TextField deptid;
	@FXML
	private TextField cost;
	@FXML
	private Text deptcodelabel;
	@FXML
	private Text costlabel;

	
	

	
	@FXML
	private Button Insert;
	@FXML
	private Button UpdDel;
	@FXML
	private Button Delete;
	@FXML
	private Button View;
	@FXML
	private Button ViewAll;
	
	ObservableList<Fees> courselist = FXCollections.observableArrayList();
	
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
						deptid.setText(table.getSelectionModel().getSelectedItem().getDeptid());
						cost.setText(table.getSelectionModel().getSelectedItem().getFee());
						
						
					
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
		
		deptcodelabel.setText("Department Code");
		deptcodelabel.setFill(Color.web("#042954"));
		
		costlabel.setText("Fees Per Credit Hour");
		costlabel.setFill(Color.web("#042954"));
		
		
	}
	
	
	public void InsertStudentRecord(ActionEvent e) {

		defaultlabel();
		
		String query = "INSERT INTO `fee`(`DeptCode`, `Fees`)  VALUES (?, ?)"; 
		
		
		PreparedStatement ps = null;
		
		if(StringUtils.isNullOrEmpty(deptid.getText()) || StringUtils.isNullOrEmpty(cost.getText()) ) 
		{
			System.out.println("A");
			if(StringUtils.isNullOrEmpty(deptid.getText()))
			{
				
				deptcodelabel.setText("Department Code*");
				deptcodelabel.setFill(Color.web("#d50000"));
			}
			System.out.println("A");
			if(StringUtils.isNullOrEmpty(cost.getText()))
			{
			
				costlabel.setText("Fees Per Credit Hour*");
				costlabel.setFill(Color.web("#d50000"));
			}	
			System.out.println("A");
			
		
		}
		
		else {
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, deptid.getText());
			ps.setString(2, cost.getText());

			ps.execute();
			ViewStudentRecord(e);
		
			
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		}
	}
	
	public void UpdateStudentRecord(ActionEvent e) {
	
		String query = "UPDATE `fee` SET  `Fees` = ? where `DeptCode` LIKE ?";
		
		
		defaultlabel();
		PreparedStatement ps = null;
		if(StringUtils.isNullOrEmpty(deptid.getText()) || StringUtils.isNullOrEmpty(cost.getText()) ) {
			if(StringUtils.isNullOrEmpty(deptid.getText()))
			{
				
				deptcodelabel.setText("Department Code*");
				deptcodelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(cost.getText()))
			{
			
				costlabel.setText("Fees Per Credit Hour*");
				costlabel.setFill(Color.web("#d50000"));
			}	
			
		}
		
		else {

		
		try {
			ps = con.prepareStatement(query);

			
			ps.setString(2, deptid.getText());
			ps.setString(1, cost.getText());		

			ps.execute();
			ViewStudentRecord(e);
			
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		}
	}
		
	public void DeleteStudentRecord(ActionEvent e) {

		
		String query = "delete from `fee` where `DeptCode` LIKE ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, deptid.getText());
			
			ps.execute();
			defaultlabel();
			ViewStudentRecord(e);
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		
	
	}
	
	public void RefreshRecord(ActionEvent e) {
		deptid.clear();
		cost.clear();
		
		defaultlabel();

	}
	
	public void SearchStudentRecord(ActionEvent e) {
		defaultlabel();
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			String query = "select * from `fee` where `DeptCode` LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, deptid.getText());
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}
			
			else {
				
				
				while(rs.next()) {

					
					courselist.add(new Fees(rs.getString("DeptCode"), rs.getString("Fees")));				}
			}
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		} 
		
		col_deptcode.setCellValueFactory(new PropertyValueFactory<>("deptid"));
		col_fee.setCellValueFactory(new PropertyValueFactory<>("fee"));
		
				
		table.setItems(courselist);
		
		
	}
	
	public void ViewStudentRecord(ActionEvent Event) {
		
		defaultlabel();
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery("select * from `fee`");
			while(rs.next()) {
				courselist.add(new Fees(rs.getString("DeptCode"), rs.getString("Fees")));
			}
			}
			
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}

		col_deptcode.setCellValueFactory(new PropertyValueFactory<>("deptid"));
		col_fee.setCellValueFactory(new PropertyValueFactory<>("fee"));

				
		table.setItems(courselist);
	}

}
