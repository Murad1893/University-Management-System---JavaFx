package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;

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
import javafx.scene.control.DatePicker;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class AdminControllerStudent implements Initializable{

	String stid;
	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	
	//Table View
	@FXML
	private TableView<Student> table;
	
	//Table column
	@FXML
	private TableColumn<Student,String> col_id;
	@FXML
	private TableColumn<Student,String> col_fname;
	@FXML
	private TableColumn<Student,String> col_mname;
	@FXML
	private TableColumn<Student,String> col_lname;
	@FXML
	private TableColumn<Student,String> col_gender;
	@FXML
	private TableColumn<Student,String> col_caddr;
	@FXML
	private TableColumn<Student,String> col_paddr;
	@FXML
	private TableColumn<Student,String> col_mobno;
	@FXML
	private TableColumn<Student,String> col_hno;
	@FXML
	private TableColumn<Student,String> col_altno;
	@FXML
	private TableColumn<Student,String> col_emailh;
	@FXML
	private TableColumn<Student,String> col_emailw;
	@FXML
	private TableColumn<Student,String> col_cnic;
	@FXML
	private TableColumn<Student,String> col_dob;
	@FXML
	private TableColumn<Student,String> col_batch;
	@FXML
	private TableColumn<Student,String> col_dcode;
	@FXML
	private TableColumn<Student,String> col_secid;
	
	//Text Field 
	@FXML
	private TextField id;
	@FXML
	private TextField fname;
	@FXML
	private TextField mname;
	@FXML
	private TextField lname;
	@FXML
	private TextArea caddr;
	@FXML
	private TextArea paddr;
	@FXML
	private TextField mobno;
	@FXML
	private TextField hno;
	@FXML
	private TextField altno;
	@FXML
	private TextField emailh;
	@FXML
	private TextField emailw;
	@FXML
	private TextField cnic;
	@FXML
	private TextField batch;
	@FXML
	private TextField dcode;
	@FXML
	private TextField secid;
	@FXML
	private Text idlabel;
	@FXML
	private Text fnamelabel;
	@FXML
	private Text lnamelabel;
	@FXML
	private Text genderlabel;
	@FXML
	private Text caddrlabel;
	@FXML
	private Text paddrlabel;
	@FXML
	private Text mobnolabel;
	@FXML
	private Text emailhlabel;
	@FXML
	private Text emailwlabel;
	@FXML
	private Text cniclabel;
	@FXML
	private Text doblabel;
	@FXML
	private Text batchlabel;
	@FXML
	private Text dcodelabel;

	//Radio Button
	@FXML
	private RadioButton gender_1;
	@FXML
	private RadioButton gender_2;
	
	//Date Picker
	@FXML
	private DatePicker dob;
	
	//Buttons
	@FXML
	private Button Insert;
	@FXML
	private Button Update;
	@FXML
	private Button Delete;
	@FXML
	private Button View;
	@FXML
	private Button ViewAll;
	@FXML
	private Button Refresh;
	@FXML
	private Button AddImage;
	@FXML
	private Button RemoveImage;
	
	@FXML
	private ImageView studentimage;
	
	ObservableList<Student> stdlist = FXCollections.observableArrayList();
	
	FileChooser filechooser = new FileChooser();
	FileInputStream fs;
	File file;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DBConnector.getConnection();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AddImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				file = filechooser.showOpenDialog(AddImage.getScene().getWindow());
				
				if(file != null) {
					AddImage.setText(file.getName());
					studentimage.setImage(new Image("file:" + file.getAbsolutePath(), 171, 175, false, false));
				}
				
			}
		});
		
		RemoveImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddImage.setText("Add Image");
				studentimage.setImage(new Image("file:unknown.jpg", 171, 175, false, false));
				file = null;	
			}
		});
		
		table.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event){
				// TODO Auto-generated method stub
				try {
					if(event.getClickCount() == 1) {
						id.setText(table.getSelectionModel().getSelectedItem().getId());
						fname.setText(table.getSelectionModel().getSelectedItem().getFname());
						mname.setText(table.getSelectionModel().getSelectedItem().getMname());
						lname.setText(table.getSelectionModel().getSelectedItem().getLname());
						caddr.setText(table.getSelectionModel().getSelectedItem().getC_addr());
						paddr.setText(table.getSelectionModel().getSelectedItem().getP_addr());
						
						if(table.getSelectionModel().getSelectedItem().getGender().equals("Male")) {
							gender_1.setSelected(true);							
						}
						else {
							gender_2.setSelected(true);
						}
			
						dob.setValue(LOCAL_DATE(table.getSelectionModel().getSelectedItem().getDob()));
						
						mobno.setText(table.getSelectionModel().getSelectedItem().getMobileno());
						altno.setText(table.getSelectionModel().getSelectedItem().getAltno());
						hno.setText(table.getSelectionModel().getSelectedItem().getHomeno());
						emailh.setText(table.getSelectionModel().getSelectedItem().getEmailh());
						emailw.setText(table.getSelectionModel().getSelectedItem().getEmailw());
						cnic.setText(table.getSelectionModel().getSelectedItem().getCnic());
						batch.setText(table.getSelectionModel().getSelectedItem().getBatch());
						dcode.setText(table.getSelectionModel().getSelectedItem().getDeptcode());
						secid.setText(table.getSelectionModel().getSelectedItem().getSecid());
						AddImage.setText("Add Image");
						
						try {
							
							String query = "select Image from `student` where `StdID` LIKE '" + table.getSelectionModel().getSelectedItem().getId() + "'";	
							
							ResultSet rs = con.createStatement().executeQuery(query);
						
							if(!rs.isBeforeFirst()) {
								throw new SQLException();			
							}
							rs.next();
							InputStream is = rs.getBinaryStream("Image");
							
							OutputStream os = new FileOutputStream(new File("studentformphoto.jpg"));
							byte[] content = new byte[1024];
							
							int size = 0;

							if(is != null) {
							
							while((size = is.read(content)) != -1) {		
								os.write(content,0,size);
								
							}
							os.close();
							is.close();
							
							Image image = new Image("file:studentformphoto.jpg",171,175,false,false);
							studentimage.setImage(image);
							}
							else {
								studentimage.setImage(new Image("file:unknown.jpg"));
							}
						}
						
						catch (SQLException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				catch(NullPointerException e) {
					error.setContentText("No valid value in table!");
					error.showAndWait();
				}
			}

			private LocalDate LOCAL_DATE(String dateString) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			    LocalDate localDate = LocalDate.parse(dateString, formatter);
			    return localDate;
			}
		});
	}
	
	public void RefreshRecord(ActionEvent e) {
		defaultlabel() ;
		id.clear();
		fname.clear();
		mname.clear();
		lname.clear();
		caddr.clear();
		paddr.clear();
		mobno.clear();
		hno.clear();
		altno.clear();
		emailh.clear();
		emailw.clear();
		cnic.clear();
		batch.clear();
		secid.clear();
		gender_1.setSelected(false);
		gender_2.setSelected(false);
		dob.setValue(null);
		AddImage.setText("Add Image");
		studentimage.setImage(new Image("file:unknown.jpg",171,175,false,false));
	}
	
	public void defaultlabel() 
	{
	
		
		idlabel.setText("Student ID");
		idlabel.setFill(Color.web("#042954"));
		
		fnamelabel.setText("First Name");
		fnamelabel.setFill(Color.web("#042954"));
		
		lnamelabel.setText("Last Name");
		lnamelabel.setFill(Color.web("#042954"));
		
		genderlabel.setText("Gender");
		genderlabel.setFill(Color.web("#042954"));
		
		caddrlabel.setText("Current Address");
		caddrlabel.setFill(Color.web("#042954"));
		
		paddrlabel.setText("Permenant Address");
		paddrlabel.setFill(Color.web("#042954"));
		
		mobnolabel.setText("Mobile No");
		mobnolabel.setFill(Color.web("#042954"));
		
		emailhlabel.setText("Email (Personel)");
		emailhlabel.setFill(Color.web("#042954"));
		
		emailwlabel.setText("Email (Work)");
		emailwlabel.setFill(Color.web("#042954"));
		
		cniclabel.setText("CNIC");
		cniclabel.setFill(Color.web("#042954"));
		
		doblabel.setText("Date Of Birth");
		doblabel.setFill(Color.web("#042954"));
		
		batchlabel.setText("Batch");
		batchlabel.setFill(Color.web("#042954"));
		
		dcodelabel.setText("Department Code");
		dcodelabel.setFill(Color.web("#042954"));
	}
	
	public void InsertASection(int SectionID, int DeptCode) {
		
		String query = "INSERT INTO `section` (`SectionID`, `DeptCode`, `Year`, `SecLetter`) VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, Integer.toString(SectionID));
			ps.setString(2, Integer.toString(DeptCode));
			ps.setString(3, Integer.toString((SectionID/10) % 10000));
			
			char secletter = (char) (64 + (SectionID % 10));
		
			ps.setString(4, String.valueOf(secletter));
			
			ps.execute();
		}
		
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void DeleteASection(String secid) {
		
		String query = "DELETE FROM  `section` WHERE `SectionID` = '"+ secid +"'";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(query);
			
			ps.execute();
		}
		
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void InsertStudentRecord(ActionEvent e) {
		defaultlabel() ;
		
		String query = "INSERT INTO `student` (`StdID`, `CNIC`, `Fname`, `Mname`, `Lname`, `Gender`, `DOB`, `C_addr`, `P_addr`, `Batch`, `Sec_ID`, `DeptCode`, `Image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
		String query1 = "INSERT INTO `student_email` (`StdID`, `Email`,`Type`) VALUES (?, ?, ?)";
		String query2 = "INSERT INTO `student_contactno` (`StdID`, `Contact`, `Type`) VALUES (?, ?, ?)";
		String query3 = "SELECT SEC_ID, COUNT(*) AS SECCOUNT FROM `STUDENT` GROUP BY `SEC_ID` HAVING `SEC_ID` LIKE '%"+ batch.getText() + "%' ORDER BY SEC_ID ASC";
		String query4 = "INSERT INTO `uac` (`Username`, `Password`, `Previlege`) VALUES (?, ?, ?)";
		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		boolean check = gender_1.isSelected() || gender_2.isSelected();
		if(StringUtils.isNullOrEmpty(fname.getText()) || !check||StringUtils.isNullOrEmpty(lname.getText())||StringUtils.isNullOrEmpty(caddr.getText())||StringUtils.isNullOrEmpty(paddr.getText())||StringUtils.isNullOrEmpty(mobno.getText())
				||StringUtils.isNullOrEmpty(emailh.getText())||StringUtils.isNullOrEmpty(emailw.getText())||StringUtils.isNullOrEmpty(cnic.getText())||dob.getValue() == null||StringUtils.isNullOrEmpty(batch.getText())
				||StringUtils.isNullOrEmpty(dcode.getText())) {
			if(StringUtils.isNullOrEmpty(fname.getText()))
			{
				
				fnamelabel.setText("First Name*");
				fnamelabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(lname.getText()))
			{
				
				lnamelabel.setText("Last Name*");
				lnamelabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(caddr.getText()))
			{
				
				caddrlabel.setText("Current Address*");
				caddrlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(paddr.getText()))
			{
				
				paddrlabel.setText("Permenant Address*");
				paddrlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(mobno.getText()))
			{
				
				mobnolabel.setText("Mobile No*");
				mobnolabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(emailh.getText()))
			{
				
				emailhlabel.setText("Email (Personel)*");
				emailhlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(emailw.getText()))
			{
				
				emailwlabel.setText("Email (Work)*");
				emailwlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(cnic.getText()))
			{
				
				cniclabel.setText("CNIC*");
				cniclabel.setFill(Color.web("#d50000"));	
			}
			if(dob.getValue() == null)
			{
				
				doblabel.setText("Date Of Birth*");
				doblabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(batch.getText()))
			{
				
				batchlabel.setText("Batch*");
				batchlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(dcode.getText()))
			{
				
				dcodelabel.setText("Department Code*");
				dcodelabel.setFill(Color.web("#d50000"));	
			}
			if(!check)
			{	
				genderlabel.setText("Gender*");
				genderlabel.setFill(Color.web("#d50000"));
				
			}	
		}
		
		else {
		try {
			ResultSet rs = con.createStatement().executeQuery("select `StdID` from `student` order by `StdID`");
			
			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query2);
			ps3 = con.prepareStatement(query4);
			
			String s = null;
			int i = 1;
			
			s = "S" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "000" + Integer.toString(i); 
			
			while(rs.next()) {
	
				if(rs.getString("StdID").equals(s)) {
					i+=1;
					
					if (i < 10)
						s = "S" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "000" + Integer.toString(i); 
					else if(i < 100)
						s = "S" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "00" + Integer.toString(i); 
					else if (i < 1000)
						s = "S" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "0" + Integer.toString(i); 
					else 
						s = "S" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + Integer.toString(i);
				}
					
			}

			ps.setString(1, s);
			id.setText(s);
			
			ps.setString(2, cnic.getText());
			ps.setString(3, fname.getText());
			
			if(StringUtils.isNullOrEmpty(mname.getText()))
				ps.setString(4, null);
			else
				ps.setString(4, mname.getText());
			
			ps.setString(5, lname.getText());
			
			if(gender_1.isSelected())
				ps.setString(6, gender_1.getText());
			else
				ps.setString(6, gender_2.getText());
			ps.setString(7, dob.getValue().toString());
			ps.setString(8, caddr.getText());
			ps.setString(9, paddr.getText());
			ps.setString(10, batch.getText()); //----------------------DO CHANGE in ID
			
//			if(StringUtils.isNullOrEmpty(secid.getText()))
//				ps.setString(11, null);
//			else
			
			int sec = Integer.parseInt(dcode.getText()) * 100000 + Integer.parseInt(batch.getText()) * 10 + 1 ;
			ResultSet rs1 = con.createStatement().executeQuery(query3);
			boolean var = false;
			
			while(rs1.next()) {
				if(Integer.parseInt(rs1.getString("Sec_ID")) == sec) {
					if(Integer.parseInt(rs1.getString("SECCOUNT")) < 1) {
						ps.setString(11, Integer.toString(sec));
						var = true;
					}
					else sec+=1;
				}
				
			}

			if(!var) {
				InsertASection(sec, Integer.parseInt(dcode.getText()));
				ps.setString(11, Integer.toString(sec));
			}
			
			if(StringUtils.isNullOrEmpty(dcode.getText()))
				ps.setString(12, null);
			else
			ps.setString(12, dcode.getText());
			
			if (file != null) {
				fs = new FileInputStream(file);
				ps.setBinaryStream(13, (InputStream)fs , (int)file.length());
			}
			
			else
			ps.setString(13, null);
			
			try{
				ps.execute();
			}
			catch(SQLException e4) {
				String query5 = "SELECT SEC_ID, COUNT(*) AS SECCOUNT FROM `STUDENT` GROUP BY `SEC_ID` HAVING `SEC_ID` LIKE "+ sec +" ORDER BY SEC_ID ASC";
				
				ResultSet checker = con.createStatement().executeQuery(query5);
				
				if(!checker.isBeforeFirst()) {
					DeleteASection(Integer.toString(sec));
				}
			}
			
			ps1.setString(1, s);
			ps1.setString(2, emailh.getText());
			ps1.setString(3, "Home");
			
			ps1.execute();
			
			ps1.setString(1, s);
			ps1.setString(2, emailw.getText());
			ps1.setString(3, "Work");
			
			ps1.execute();
			
			ps2.setString(1, s);
			ps2.setString(2, mobno.getText());
			ps2.setString(3, "Mobile");
			
			ps2.execute();
			
			ps2.setString(1, s);
			ps2.setString(2, altno.getText());
			ps2.setString(3, "Alternate");
			
			if(!StringUtils.isNullOrEmpty(altno.getText()))
				ps2.execute();
			
			ps2.setString(1, s);
			ps2.setString(2, hno.getText());
			ps2.setString(3, "Home");
			
			if(!StringUtils.isNullOrEmpty(hno.getText()))
				ps2.execute();
			
			
			ps3.setString(1, s);	
			ps3.setString(2, Base64.getEncoder().encodeToString(("1234").getBytes()));
			ps3.setString(3, "1");
			
			ps3.execute();
			
			ViewStudentRecord(e);
			
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Student record already found!");
			error.showAndWait();
		}
		}
	}
	
	public void UpdateStudentRecord(ActionEvent e) {
		defaultlabel() ;
	
		String query = "UPDATE `student` SET `CNIC` = ?, `Fname` = ?, `Mname` = ?, `Lname` = ?, `Gender` = ?, `DOB` = ?, `C_addr` = ?, `P_addr` = ?, `Batch` = ?, `Sec_ID` = ?, `DeptCode` = ?, `Image` = ? where `StdID` LIKE ?";
		String query1 = "UPDATE `student_email` SET `EMAIL` = ? where `StdID` LIKE ? and `type` = ?";
		String query2 = "UPDATE `student_contactno` SET `CONTACT` = ? where `StdID` LIKE ? and `type` = ?";
		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		boolean check = gender_1.isSelected() || gender_2.isSelected();
		if(StringUtils.isNullOrEmpty(fname.getText()) || !check||StringUtils.isNullOrEmpty(lname.getText())||StringUtils.isNullOrEmpty(caddr.getText())||StringUtils.isNullOrEmpty(paddr.getText())||StringUtils.isNullOrEmpty(mobno.getText())
				||StringUtils.isNullOrEmpty(emailh.getText())||StringUtils.isNullOrEmpty(emailw.getText())||StringUtils.isNullOrEmpty(cnic.getText())||dob.getValue() == null||StringUtils.isNullOrEmpty(batch.getText())
				||StringUtils.isNullOrEmpty(dcode.getText())||StringUtils.isNullOrEmpty(id.getText())) {
			if(StringUtils.isNullOrEmpty(fname.getText()))
			{
				
				fnamelabel.setText("First Name*");
				fnamelabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(lname.getText()))
			{
				
				lnamelabel.setText("Last Name*");
				lnamelabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(caddr.getText()))
			{
				
				caddrlabel.setText("Current Address*");
				caddrlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(paddr.getText()))
			{
				
				paddrlabel.setText("Permenant Address*");
				paddrlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(mobno.getText()))
			{
				
				mobnolabel.setText("Mobile No*");
				mobnolabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(emailh.getText()))
			{
				
				emailhlabel.setText("Email (Personel)*");
				emailhlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(emailw.getText()))
			{
				
				emailwlabel.setText("Email (Work)*");
				emailwlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(cnic.getText()))
			{
				
				cniclabel.setText("CNIC*");
				cniclabel.setFill(Color.web("#d50000"));	
			}
			if(dob.getValue() == null)
			{
				
				doblabel.setText("Date Of Birth*");
				doblabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(batch.getText()))
			{
				
				batchlabel.setText("Batch*");
				batchlabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(dcode.getText()))
			{
				
				dcodelabel.setText("Department Code*");
				dcodelabel.setFill(Color.web("#d50000"));	
			}
			if(StringUtils.isNullOrEmpty(id.getText()))
			{
				
				idlabel.setText("Student ID*");
				idlabel.setFill(Color.web("#d50000"));	
			}
			if(!check)
			{	
				genderlabel.setText("Gender*");
				genderlabel.setFill(Color.web("#d50000"));	
			}
		}
		
		else {
		
		try {
			
			String checker = "select * from `student` where `StdID` LIKE ?";
			
			PreparedStatement ps3 = con.prepareStatement(checker);
			ps3.setString(1, id.getText());
			ps3.execute();
			
			ResultSet rs = ps3.getResultSet();
			
			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}
			
			else {
				ps = con.prepareStatement(query);
				ps1 = con.prepareStatement(query1);
				ps2 = con.prepareStatement(query2);
				
				ps.setString(1, cnic.getText());
				ps.setString(2, fname.getText());
				ps.setString(3, mname.getText());
				ps.setString(4, lname.getText());
				
				if(gender_1.isSelected())
					ps.setString(5, gender_1.getText());
				else
					ps.setString(5, gender_2.getText());
				ps.setString(6, dob.getValue().toString());
				ps.setString(7, caddr.getText());
				ps.setString(8, paddr.getText());
				ps.setString(9, batch.getText());
				ps.setString(10, secid.getText());
				ps.setString(11, dcode.getText());
				
				if (file != null) {
					fs = new FileInputStream(file);
					ps.setBinaryStream(12, (InputStream)fs , (int)file.length());
				}	
				else 
					ps.setString(12,null);
				ps.setString(13, id.getText());
				
				ps.execute();
				
				ps1.setString(1, emailh.getText());
				ps1.setString(2, id.getText());
				ps1.setString(3, "Home");
				
				ps1.execute();
				
				ps1.setString(1, emailw.getText());
				ps1.setString(2, id.getText());
				ps1.setString(3, "Work");
				
				ps1.execute();
				
				ps2.setString(1, mobno.getText());
				ps2.setString(2, id.getText());
				ps2.setString(3, "Mobile");
				
				ps2.execute();
				
				ps2.setString(1, altno.getText());
				ps2.setString(2, id.getText());
				ps2.setString(3, "Alternate");
				
				if(!StringUtils.isNullOrEmpty(altno.getText()))
					ps2.execute();
				
				ps2.setString(1, hno.getText());
				ps2.setString(2, id.getText());
				ps2.setString(3, "Home");
				
				if(!StringUtils.isNullOrEmpty(hno.getText()))
					ps2.execute();
				
				
				ViewStudentRecord(e);
			}
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		}
		}

	}
		
	public void DeleteStudentRecord(ActionEvent e) {
		defaultlabel() ;
		
		
		String query = "delete from `student` where `StdID` LIKE ?";
		String query1 = "DELETE FROM `uac` WHERE `Username`= ?";
		String query2 = "SELECT SEC_ID FROM `student` where `StdID` LIKE '"+ id.getText() + "'";
		
		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		
			if(StringUtils.isNullOrEmpty(id.getText()))
			{
				
				idlabel.setText("Student ID*");
				idlabel.setFill(Color.web("#d50000"));	
			}
			else {
			try {
				ps = con.prepareStatement(query);
				ps1 = con.prepareStatement(query1);
				
				ps.setString(1, id.getText());
				ps1.setString(1, id.getText());
				
				
				ResultSet rs1 = con.createStatement().executeQuery(query2);
				
				rs1.next();
				
				ps.execute();
				ps1.execute();
				
				try {
					
					String query3 = "SELECT SEC_ID, COUNT(*) AS SECCOUNT FROM `STUDENT` GROUP BY `SEC_ID` HAVING `SEC_ID` LIKE "+ rs1.getString("SEC_ID") +" ORDER BY SEC_ID ASC";
					
					ResultSet rs2 = con.createStatement().executeQuery(query3);
					
					if(!rs2.isBeforeFirst()) {
						DeleteASection(rs1.getString("SEC_ID"));
					}
					
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					error.setContentText("Record not found!");
					error.showAndWait();
				}
				
				ViewStudentRecord(e);
				
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				error.setContentText("Unable to delete student record!");
				error.showAndWait();
			}
			}
		
	}
	
	public void SearchStudentRecord(ActionEvent e) {
		defaultlabel() ;
		
		
			if(StringUtils.isNullOrEmpty(id.getText()))
			{
				
				idlabel.setText("Student ID*");
				idlabel.setFill(Color.web("#d50000"));	
			}
			else {
			if(table.getItems() != null) {
				table.getItems().clear();
			}
		try {
			
			String query = "select * from `student` where `StdID` LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id.getText());
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
					
					stdlist.add(new Student(rs.getString("stdid"), rs.getString("fname"),rs.getString("mname"), rs.getString("lname"), rs.getString("gender"), rs.getString("c_addr"), rs.getString("p_addr"), contact[0], contact[1], contact[2], email[0], email[1], rs.getString("cnic"), rs.getString("dob"), rs.getString("batch").substring(0,4), rs.getString("sec_id"), rs.getString("deptcode")));
				}
			}
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		} 
		
		
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
		col_mname.setCellValueFactory(new PropertyValueFactory<>("mname"));
		col_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
		col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		col_caddr.setCellValueFactory(new PropertyValueFactory<>("c_addr"));
		col_paddr.setCellValueFactory(new PropertyValueFactory<>("p_addr"));
		col_mobno.setCellValueFactory(new PropertyValueFactory<>("mobileno"));
		col_hno.setCellValueFactory(new PropertyValueFactory<>("homeno"));
		col_altno.setCellValueFactory(new PropertyValueFactory<>("altno"));
		col_emailh.setCellValueFactory(new PropertyValueFactory<>("emailh"));
		col_emailw.setCellValueFactory(new PropertyValueFactory<>("emailw"));
		col_cnic.setCellValueFactory(new PropertyValueFactory<>("cnic"));
		col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
		col_batch.setCellValueFactory(new PropertyValueFactory<>("batch"));
		col_dcode.setCellValueFactory(new PropertyValueFactory<>("deptcode"));
		col_secid.setCellValueFactory(new PropertyValueFactory<>("secid"));
				
		table.setItems(stdlist);
		}
	}
	
	public void ViewStudentRecord(ActionEvent Event) {
		
		try {
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			 stid = fd.readLine();
			fd.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		int i = 0;
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery("select * from `student`");
			
			while(rs.next()) {	
				
				ResultSet rs1 = con.createStatement().executeQuery("select * from `student_email` where `StdID` LIKE '" + rs.getString("StdID") + "'");
				ResultSet rs2 = con.createStatement().executeQuery("select * from `student_contactno` where `StdID` LIKE '" + rs.getString("StdID") + "'");
				
				String[] email = new String[2];
				String[] contact = new String[3];
				
				i = 0;
				
				while(rs1.next()) {
					email[i] = rs1.getString("Email");
					i++;
				}
				
				i = 0; 
				
				while(rs2.next()) {
					contact[i] = rs2.getString("Contact");
					i++;
				}
				
				stdlist.add(new Student(rs.getString("stdid"), rs.getString("fname"),rs.getString("mname"), rs.getString("lname"), rs.getString("gender"), rs.getString("c_addr"), rs.getString("p_addr"), contact[0], contact[1], contact[2], email[0], email[1], rs.getString("cnic"), rs.getString("dob"), rs.getString("batch").substring(0,4), rs.getString("deptcode"), rs.getString("sec_id")));
			}
			
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Cannot fetch records!");
			error.showAndWait();
		} 
				
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
		col_mname.setCellValueFactory(new PropertyValueFactory<>("mname"));
		col_lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
		col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		col_caddr.setCellValueFactory(new PropertyValueFactory<>("c_addr"));
		col_paddr.setCellValueFactory(new PropertyValueFactory<>("p_addr"));
		col_mobno.setCellValueFactory(new PropertyValueFactory<>("mobileno"));
		col_hno.setCellValueFactory(new PropertyValueFactory<>("homeno"));
		col_altno.setCellValueFactory(new PropertyValueFactory<>("altno"));
		col_emailh.setCellValueFactory(new PropertyValueFactory<>("emailh"));
		col_emailw.setCellValueFactory(new PropertyValueFactory<>("emailw"));
		col_cnic.setCellValueFactory(new PropertyValueFactory<>("cnic"));
		col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
		col_batch.setCellValueFactory(new PropertyValueFactory<>("batch"));
		col_dcode.setCellValueFactory(new PropertyValueFactory<>("deptcode"));
		col_secid.setCellValueFactory(new PropertyValueFactory<>("secid"));
				
		table.setItems(stdlist);
	}

}
