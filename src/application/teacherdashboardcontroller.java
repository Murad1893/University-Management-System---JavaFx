package application;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.net.URL; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;

import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;



public class teacherdashboardcontroller implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String nullString = null;
	String UACID=null;
	
	//Table View
	@FXML
	private TableView<Teachercourseallotment> table;
	
	//Table column
	@FXML
	private TableColumn<Teachercourseallotment,String> teachsec;
	@FXML
	private TableColumn<Teachercourseallotment,String> teachcourse;
	
	
	//Text Field 
	
	@FXML
	private Label teachfname;
	@FXML
	private Label teachmname;
	@FXML
	private Label teachlname;
	@FXML
	private Label teachcnic;
	@FXML
	private Label teachdob;
	@FXML
	private Label teachid;
	@FXML
	private Label teachdepart;
	@FXML
	private Label teachjob;
	@FXML
	private Label teachqaulify;
	@FXML
	private Label teachmob;
	@FXML
	private Label teachalt;
	@FXML
	private Label teachmailp;
	@FXML
	private Label teachmailw;
	@FXML
	private Label teachaddp;
	@FXML
	private Label teachaddc;
	@FXML
	private Button update;
	@FXML
	private PasswordField pass;
	@FXML
	private PasswordField newpass1;
	@FXML
	private PasswordField newpass2;

	@FXML
	private Label wplabel;
	@FXML
	private Label oplabel;
	@FXML
	private Label nplabel;
	@FXML
	private Label nplabel2;
	
	ObservableList<Teachercourseallotment> courselist = FXCollections.observableArrayList();
	
	public void run()
	{
		wplabel.setText("");
		
		try {
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	    
		try {
					String query = "select * from `academic_staff` where `StaffID` LIKE ?";
					
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, UACID);
				
					ps.execute();
					ResultSet rs = ps.getResultSet();
					if(!rs.isBeforeFirst()) {
						throw new SQLException();			
					}
					
					else {
						int i = 0;
						
						while(rs.next()) {
						
							ResultSet rs1 = con.createStatement().executeQuery("select * from `staff_email` where `ID` LIKE '" + rs.getString("StaffID") + "'");
							ResultSet rs2 = con.createStatement().executeQuery("select * from `staff_contactno` where `ID` LIKE '" + rs.getString("StaffID") + "'");
							
							String[] email = new String[2];
							String[] contact = new String[3];
							
							while(rs1.next()) {
								email[i] = rs1.getString("Email");
								i++;
							}
							
							i = 0; 
							
							while(rs2.next()) {
								contact[i] = rs2.getString("Contact");
								i++;
							}
						
							teachfname.setText(rs.getString("fname"));
							teachmname.setText(rs.getString("Mname"));
							teachlname.setText(rs.getString("lname"));
							teachcnic.setText(rs.getString("CNIC"));
							teachdob.setText(rs.getString("DOB"));
							teachid.setText(rs.getString("StaffID"));
							teachdepart.setText(rs.getString("DeptCode"));
							teachjob.setText(rs.getString("JobTitle"));
							teachqaulify.setText(rs.getString("Qualification"));
							teachmob.setText(contact[0]);
							if(contact[1]==null && contact[2]==null) {teachalt.setText("");}
							else if(contact[1]==null) {teachalt.setText(contact[2]);}
							else if(contact[2]==null) {teachalt.setText(contact[1]);}
							else {teachalt.setText(contact[1]+", "+contact[2]);}
							
							teachmailp.setText(email[0]);
							teachmailw.setText(email[1]);
							teachaddp.setText(rs.getString("C_addr"));
							teachaddc.setText(rs.getString("P_addr"));
					
						}
					}
					
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					error.setContentText("Record not found!");
					error.showAndWait();
					e1.printStackTrace();
				}
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			String query = "select * from `staff_course` where `StaffID` LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, UACID);
			ps.execute();
			ResultSet rs = ps.getResultSet();
		
				while(rs.next()) {
					courselist.add(new Teachercourseallotment(rs.getString("StaffID"), rs.getString("SectionID"),rs.getString("CourseID")));				}
	
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record!");
			error.showAndWait();
		} 
		

		teachsec.setCellValueFactory(new PropertyValueFactory<>("sectionID"));
		teachcourse.setCellValueFactory(new PropertyValueFactory<>("courseID"));

				
		table.setItems(courselist);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			con = DBConnector.getConnection();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		run();
		
	   
	}
	
	public void Update()
	{
		oplabel.setTextFill(Color.web("#002e62"));
		nplabel2.setTextFill(Color.web("#002e62"));
		nplabel.setTextFill(Color.web("#002e62"));
		wplabel.setText("");
		if(StringUtils.isNullOrEmpty(pass.getText()))
		{
			System.out.println("A");
			oplabel.setTextFill(Color.web("#d50000"));	
		}
		if(StringUtils.isNullOrEmpty(newpass2.getText()))
		{
		
			nplabel2.setTextFill(Color.web("#d50000"));	
		}
		if(StringUtils.isNullOrEmpty(newpass1.getText()))
		{
			
			nplabel.setTextFill(Color.web("#d50000"));
		}
		if(!StringUtils.isNullOrEmpty(newpass1.getText()) && !StringUtils.isNullOrEmpty(newpass2.getText()) && !StringUtils.isNullOrEmpty(pass.getText()) )
		{
		String query = "SELECT * FROM `uac` WHERE `Username` = '" + UACID + "'";
		try {
			ResultSet rs = con.createStatement().executeQuery(query);
			rs.next();
			
				byte[] actualByte = Base64.getDecoder().decode(rs.getString("Password")); 
				
				String passcheck = new String(actualByte);
			
				if(passcheck.equals(pass.getText()) ) {
					wplabel.setText("");
					String query2 = "UPDATE `uac` SET  `Password` = ? where Username = '"+ UACID+ "' ";
					PreparedStatement ps = con.prepareStatement(query2);
					if( newpass1.getText().equals(newpass2.getText()))
					{
				
						ps.setString(1, Base64.getEncoder().encodeToString(newpass2.getText().getBytes()));
						
						ps.execute();
						}
					else
						wplabel.setText("*Wrong New Password");
					}
				else
					wplabel.setText("*Wrong Password");
				}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
	}
	}
	
	
}
	


	
	
	
	
	
	
	
	


