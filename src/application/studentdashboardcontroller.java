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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class studentdashboardcontroller implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	String nullString = null;
	String UACID=null;

	//Text Field 
	
	@FXML
	private Label pifname;
	@FXML
	private Label pimname;
	@FXML
	private Label pilname;
	@FXML
	private Label pidob;
	@FXML
	private Label picnic;
	@FXML
	private Label pigender;
	@FXML
	private Label uiroll;
	@FXML
	private Label uideptname;
	@FXML
	private Label uideptcode;
	@FXML
	private Label uibatch;
	@FXML
	private Label cimobno;
	@FXML
	private Label cialtno;
	@FXML
	private Label cimailp;
	@FXML
	private Label cimailw;
	@FXML
	private Label ciaddp;
	@FXML
	private Label ciaddc;
	
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
	

	public void run()
	{
		
		try {
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
			wplabel.setText("");
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	    
		try {	
					String query = "select * from `student` where `StdID` LIKE ?";		
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
							
							ResultSet rs1 = con.createStatement().executeQuery("select * from `student_email` where `StdID` LIKE '" + rs.getString("StdID") + "'");
							ResultSet rs2 = con.createStatement().executeQuery("select * from `student_contactno` where `StdID` LIKE '" + rs.getString("StdID") + "'");
							ResultSet rs3 = con.createStatement().executeQuery("select * from `department` where `DeptCode` LIKE '" + rs.getString("DeptCode") + "'");
							String[] email = new String[2];
							String[] contact = new String[3];
							String deptname = null;
							while(rs1.next()) {
								email[i] = rs1.getString("Email");
								i++;
							}
							
							i = 0; 
							
							while(rs2.next()) {
								contact[i] = rs2.getString("Contact");
								i++;
							}
							while(rs3.next()) {
								deptname = rs3.getString("DeptName");
								i++;
							}
							
							pifname.setText(rs.getString("fname"));
							pimname.setText(rs.getString("Mname"));
							pilname.setText(rs.getString("lname"));
							picnic.setText(rs.getString("CNIC"));
							pidob.setText(rs.getString("DOB"));
							uiroll.setText(rs.getString("StdID"));
							uideptcode.setText(rs.getString("DeptCode"));
							uideptname.setText(deptname);
							pigender.setText(rs.getString("Gender"));
							uibatch.setText(rs.getString("batch").substring(0,4));
							cimobno.setText(contact[0]);
							if(contact[1]==null && contact[2]==null) {cialtno.setText("");}
							else if(contact[1]==null) {cialtno.setText(contact[2]);}
							else if(contact[2]==null) {cialtno.setText(contact[1]);}
							else {cialtno.setText(contact[1]+", "+contact[2]);}
							
							cimailp.setText(email[0]);
							cimailw.setText(email[1]);
							ciaddp.setText(rs.getString("C_addr"));
							
							ciaddc.setText(rs.getString("P_addr"));
							
						}
					}
					
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					error.setContentText("Record not found!");
					error.showAndWait();
					e1.printStackTrace();
				} 
		
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
					String query2 = "UPDATE `uac` SET  `Password` = ? where Username = '"+ UACID+ "' ";
					PreparedStatement ps = con.prepareStatement(query2);
					if( newpass1.getText().equals(newpass2.getText()))
					{
				
						ps.setString(1, Base64.getEncoder().encodeToString(newpass2.getText().getBytes()));
						
						ps.execute();
						
						pass.clear();
						newpass1.clear();
						newpass2.clear();
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
	


	
	
	
	
	
	
	
	


