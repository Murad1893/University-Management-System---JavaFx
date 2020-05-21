package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

public class AdminControllerStaff implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);

	//Table View
	@FXML
	private TableView<Staff> table;

	//Table column
	@FXML
	private TableColumn<Staff,String> col_id;
	@FXML
	private TableColumn<Staff,String> col_fname;
	@FXML
	private TableColumn<Staff,String> col_mname;
	@FXML
	private TableColumn<Staff,String> col_lname;
	@FXML
	private TableColumn<Staff,String> col_gender;
	@FXML
	private TableColumn<Staff,String> col_caddr;
	@FXML
	private TableColumn<Staff,String> col_paddr;
	@FXML
	private TableColumn<Staff,String> col_mobno;
	@FXML
	private TableColumn<Staff,String> col_hno;
	@FXML
	private TableColumn<Staff,String> col_altno;
	@FXML
	private TableColumn<Staff,String> col_emailh;
	@FXML
	private TableColumn<Staff,String> col_emailw;
	@FXML
	private TableColumn<Staff,String> col_cnic;
	@FXML
	private TableColumn<Staff,String> col_dob;
	@FXML
	private TableColumn<Staff,String> col_job;
	@FXML
	private TableColumn<Staff,String> col_dcode;
	@FXML
	private TableColumn<Staff,String> col_qualify;
	@FXML
	private TableColumn<Staff,String> col_salary;


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
	private TextField job;
	@FXML
	private TextField deptno;
	@FXML
	private TextField qualify;
	@FXML
	private TextField salary;
	@FXML
	private Text idlabel;
	@FXML
	private Text fnamelabel;
	@FXML
	private Text lnamelabel;
	@FXML
	private Text caddrlabel;
	@FXML
	private Text paddrlabel;
	@FXML
	private Text mobnolabel;
	@FXML
	private Text hnolabel;
	@FXML
	private Text altnolabel;
	@FXML
	private Text emailhlabel;
	@FXML
	private Text emailwlabel;
	@FXML
	private Text cniclabel;
	@FXML
	private Text joblabel;
	@FXML
	private Text qualifylabel;
	@FXML
	private Text salarylabel;
	@FXML
	private Text genderlabel;
	@FXML
	private Text doblabel;
	@FXML
	private Text deptnolabel;

	//Radio Button
	@FXML
	private RadioButton gender_1;
	@FXML
	private RadioButton gender_2;

	//Date Picker
	@FXML
	private DatePicker dob;

	//Button
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
	private Button AddImage;
	@FXML
	private Button RemoveImage;
	
	@FXML
	private ImageView staffimage;
	
	ObservableList<Staff> stdlist = FXCollections.observableArrayList();

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
				if(file!=null) {
					AddImage.setText(file.getName());
					staffimage.setImage(new Image("file:" + file.getAbsolutePath(), 171, 175, false, false));
				}

			}
		});
		
		RemoveImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddImage.setText("Add Image");
				staffimage.setImage(new Image("file:unknown.jpg", 171, 175, false, false));
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
						qualify.setText(table.getSelectionModel().getSelectedItem().getQualification());
						job.setText(table.getSelectionModel().getSelectedItem().getJobtitle());
						salary.setText(table.getSelectionModel().getSelectedItem().getSalary());
						deptno.setText(table.getSelectionModel().getSelectedItem().getDeptcode());
						AddImage.setText("Add Image");
						
try {
							
							String query = "select Image from `academic_staff` where `StaffID` LIKE '" + table.getSelectionModel().getSelectedItem().getId() + "'";	
							
							ResultSet rs = con.createStatement().executeQuery(query);
						
							if(!rs.isBeforeFirst()) {
								throw new SQLException();			
							}
							rs.next();
							InputStream is = rs.getBinaryStream("Image");
							
							OutputStream os = new FileOutputStream(new File("staffformphoto.jpg"));
							byte[] content = new byte[1024];
							
							int size = 0;

							if(is != null) {
							
							while((size = is.read(content)) != -1) {		
								os.write(content,0,size);
								
							}
							os.close();
							is.close();
							
							Image image = new Image("file:staffformphoto.jpg",171,175,false,false);
							staffimage.setImage(image);
							}
							else {
								staffimage.setImage(new Image("file:unknown.jpg"));
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
			job.clear();
			deptno.clear();
			qualify.clear();
			gender_1.setSelected(false);
			gender_2.setSelected(false);
			dob.setValue(null);
			AddImage.setText("Add Image");
			staffimage.setImage(new Image("file:unknown.jpg",171,175,false,false));
		}

	public void defaultlabel()
	{

		idlabel.setText("Staff ID");
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

		salarylabel.setText("Salary");
		salarylabel.setFill(Color.web("#042954"));

		qualifylabel.setText("Qualification");
		qualifylabel.setFill(Color.web("#042954"));

		joblabel.setText("Job Title");
		joblabel.setFill(Color.web("#042954"));

		deptnolabel.setText("Department Code");
		deptnolabel.setFill(Color.web("#042954"));
	}

	public void InsertStaffRecord(ActionEvent e) {

		defaultlabel() ;

		String query = "INSERT INTO `academic_staff` (`StaffID`, `CNIC`, `Fname`, `Mname`, `Lname`, `DOB`, `Gender`, `Qualification`, `Salary`, `C_addr`, `P_addr`, `JobTitle`, `DeptCode`, `Image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String query1 = "INSERT INTO `staff_email` (`ID`, `Email`,`Type`) VALUES (?, ?, ?)";
		String query2 = "INSERT INTO `staff_contactno` (`ID`, `Contact`, `Type`) VALUES (?, ?, ?)";
		String query4 = "INSERT INTO `uac` (`Username`, `Password`, `Previlege`) VALUES (?, ?, ?)";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;

		boolean check = gender_1.isSelected() || gender_2.isSelected();

		if(StringUtils.isNullOrEmpty(fname.getText()) || !check||StringUtils.isNullOrEmpty(lname.getText())||StringUtils.isNullOrEmpty(caddr.getText())||StringUtils.isNullOrEmpty(paddr.getText())||StringUtils.isNullOrEmpty(mobno.getText())
				||StringUtils.isNullOrEmpty(emailh.getText())||StringUtils.isNullOrEmpty(emailw.getText())||StringUtils.isNullOrEmpty(cnic.getText())||dob.getValue() == null||StringUtils.isNullOrEmpty(qualify.getText())
				||StringUtils.isNullOrEmpty(deptno.getText())||StringUtils.isNullOrEmpty(job.getText())||StringUtils.isNullOrEmpty(salary.getText())) {

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
			if(StringUtils.isNullOrEmpty(qualify.getText()))
			{

				qualifylabel.setText("Qualification*");
				qualifylabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(salary.getText()))
			{

				salarylabel.setText("Salary*");
				salarylabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(job.getText()))
			{

				joblabel.setText("Job Title*");
				joblabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(deptno.getText()))
			{

				deptnolabel.setText("Department Code*");
				deptnolabel.setFill(Color.web("#d50000"));
			}
			if(!check)
			{
				genderlabel.setText("Gender*");
				genderlabel.setFill(Color.web("#d50000"));
			}

		}
		else {
		try {

			ResultSet rs = con.createStatement().executeQuery("select `StaffID` from `academic_staff`");

			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query2);
			ps3 = con.prepareStatement(query4);

			String s = new String();
			int i = 1;

			s = "T" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "000" + Integer.toString(i);

			while(rs.next()) {

				if(rs.getString("StaffID").equals(s)) {
					i+=1;

					if (i < 10)
						s = "T" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "000" + Integer.toString(i);
					else if(i < 100)
						s = "T" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "00" + Integer.toString(i);
					else if (i < 1000)
						s = "T" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + "0" + Integer.toString(i);
					else
						s = "T" + Integer.toString(Calendar.getInstance().get(Calendar.YEAR)%100) + Integer.toString(i);
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

			ps.setString(6, dob.getValue().toString());

			if(gender_1.isSelected())
				ps.setString(7, gender_1.getText());
			else
				ps.setString(7, gender_2.getText());

			ps.setString(8, qualify.getText());

			if(StringUtils.isNullOrEmpty(salary.getText())) {
				ps.setString(9, "0");
			}
			else
				ps.setString(9, salary.getText());

			ps.setString(10, caddr.getText());
			ps.setString(11, paddr.getText());
			ps.setString(12, job.getText());

			if(StringUtils.isNullOrEmpty(deptno.getText())) {
				ps.setString(13, null);
			}
			else
				ps.setString(13, deptno.getText());

			if (file != null) {
				fs = new FileInputStream(file);
				ps.setBinaryStream(14, (InputStream)fs , (int)file.length());
			}

			else ps.setString(14,null);

			ps.execute();

			ps1.setString(1, id.getText());
			ps1.setString(2, emailh.getText());
			ps1.setString(3, "Home");

			ps1.execute();

			ps1.setString(1, id.getText());
			ps1.setString(2, emailw.getText());
			ps1.setString(3, "Work");

			ps1.execute();

			ps2.setString(1, id.getText());
			ps2.setString(2, mobno.getText());
			ps2.setString(3, "Mobile");

			ps2.execute();

			ps2.setString(1, id.getText());
			ps2.setString(2, altno.getText());
			ps2.setString(3, "Alternate");

			if(!StringUtils.isNullOrEmpty(altno.getText()))
				ps2.execute();

			ps2.setString(1, id.getText());
			ps2.setString(2, hno.getText());
			ps2.setString(3, "Home");

			if(!StringUtils.isNullOrEmpty(hno.getText()))
				ps2.execute();

			ps3.setString(1, s);
			ps3.setString(2, Base64.getEncoder().encodeToString(("1234").getBytes()));
			ps3.setString(3, "2");

			ps3.execute();

			ViewStaffRecord(e);

		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Staff record already exists!");
			error.showAndWait();
			e1.printStackTrace();
		}
		}

	}

	public void UpdateStaffRecord(ActionEvent e) {
		defaultlabel() ;
		boolean check = gender_1.isSelected() || gender_2.isSelected();
		if(StringUtils.isNullOrEmpty(fname.getText()) || !check||StringUtils.isNullOrEmpty(lname.getText())||StringUtils.isNullOrEmpty(caddr.getText())||StringUtils.isNullOrEmpty(paddr.getText())||StringUtils.isNullOrEmpty(mobno.getText())
				||StringUtils.isNullOrEmpty(emailh.getText())||StringUtils.isNullOrEmpty(emailw.getText())||StringUtils.isNullOrEmpty(cnic.getText())||dob.getValue() == null||StringUtils.isNullOrEmpty(qualify.getText())
				||StringUtils.isNullOrEmpty(deptno.getText())||StringUtils.isNullOrEmpty(job.getText())||StringUtils.isNullOrEmpty(salary.getText())||StringUtils.isNullOrEmpty(id.getText())) {
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
			if(StringUtils.isNullOrEmpty(qualify.getText()))
			{

				qualifylabel.setText("Qualification*");
				qualifylabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(salary.getText()))
			{

				salarylabel.setText("Salary*");
				salarylabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(job.getText()))
			{

				joblabel.setText("Job Title*");
				joblabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(deptno.getText()))
			{

				deptnolabel.setText("Department Code*");
				deptnolabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(id.getText()))
			{

				idlabel.setText("Staff ID*");
				idlabel.setFill(Color.web("#d50000"));
			}
			if(!check)
			{

				genderlabel.setText("Gender*");
				genderlabel.setFill(Color.web("#d50000"));

			}

		}
		else {
		String query = "UPDATE `academic_staff` SET `CNIC` = ?, `Fname` = ?, `Mname` = ?, `Lname` = ?, `DOB` = ?, `Gender` = ?, `Qualification` = ?, `Salary` = ?, `C_addr` = ?, `P_addr` = ?, `JobTitle` = ?, `DeptCode` = ?, `Image` = ? WHERE `StaffID` = ?";
		String query1 = "UPDATE `staff_email` SET `EMAIL` = ? where `ID` LIKE ? and `type` = ?";
		String query2 = "UPDATE `staff_contactno` SET `CONTACT` = ? where `ID` LIKE ? and `type` = ?";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		try {
			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query2);

			ps.setString(1, cnic.getText());
			ps.setString(2, fname.getText());
			ps.setString(3, mname.getText());
			ps.setString(4, lname.getText());
			ps.setString(5, dob.getValue().toString());
			if(gender_1.isSelected())
				ps.setString(6, gender_1.getText());
			else
				ps.setString(6, gender_2.getText());
			ps.setString(7, qualify.getText());

			if(StringUtils.isNullOrEmpty(salary.getText())) {
				ps.setString(8, "0");
			}
			else
				ps.setString(8, salary.getText());

			ps.setString(9, caddr.getText());
			ps.setString(10, paddr.getText());
			ps.setString(11, job.getText());

			if(StringUtils.isNullOrEmpty(deptno.getText())) {
				ps.setString(12, null);
			}
			else
				ps.setString(12, deptno.getText());

			if (file != null) {
				fs = new FileInputStream(file);
				ps.setBinaryStream(13, (InputStream)fs , (int)file.length());
			}

			else ps.setString(13,null);

			ps.setString(14, id.getText());

			if(searchdept()) {
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


				ViewStaffRecord(e);
			}
			
			else {
				error.setContentText("Staff record not found!");
				error.showAndWait();
			}
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}
		}

	}

	public void DeleteStaffRecord(ActionEvent e) {
		defaultlabel() ;
		if(StringUtils.isNullOrEmpty(id.getText()))
		{
			idlabel.setText("Staff ID*");
			idlabel.setFill(Color.web("#d50000"));
		}
		else {
		String query = "delete from `academic_staff` where `StaffID` LIKE ?";
		String query1 = "DELETE FROM `uac` WHERE `Username`= ?";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

		try {
			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);

			ps.setString(1, id.getText());
			ps1.setString(1, id.getText());

			if(searchdept()){
				ps.execute();
				ps1.execute();

				ps.close();
				ViewStaffRecord(e);
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

	boolean searchdept(){
		try {
			String query = "select * from `academic_staff` where `StaffID` LIKE ?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id.getText());
			ps.execute();

			ResultSet rs = ps.getResultSet();

			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}
			else{
				return true;
			}
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public void SearchStaffRecord(ActionEvent e) {

		defaultlabel() ;

		if(StringUtils.isNullOrEmpty(id.getText()))
		{

			idlabel.setText("Staff ID*");
			idlabel.setFill(Color.web("#d50000"));
		}
		else {
		if(table.getItems() != null) {
			table.getItems().clear();
		}

		try {

			String query = "select * from `academic_staff` where `StaffID` LIKE ?";

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

					stdlist.add(new Staff(rs.getString("staffid"), rs.getString("fname"),rs.getString("mname"), rs.getString("lname"), rs.getString("gender"), rs.getString("c_addr"), rs.getString("p_addr"), contact[0], contact[1], contact[2], email[0], email[1], rs.getString("cnic"), rs.getString("dob"), rs.getString("qualification"), rs.getString("deptcode"), rs.getString("jobtitle") , rs.getString("salary") ));
				}
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
			e1.printStackTrace();
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
		col_qualify.setCellValueFactory(new PropertyValueFactory<>("qualification"));
		col_dcode.setCellValueFactory(new PropertyValueFactory<>("deptcode"));
		col_job.setCellValueFactory(new PropertyValueFactory<>("jobtitle"));
		col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

		table.setItems(stdlist);
		}

	}

	public void ViewStaffRecord(ActionEvent Event) {

		defaultlabel();

		int i = 0;

		if(table.getItems() != null) {
			table.getItems().clear();
		}

		try {

			ResultSet rs = con.createStatement().executeQuery("select * from `academic_staff`");

			while(rs.next()) {

				ResultSet rs1 = con.createStatement().executeQuery("select * from `staff_email` where `ID` LIKE '" + rs.getString("StaffID") + "'");
				ResultSet rs2 = con.createStatement().executeQuery("select * from `staff_contactno` where `ID` LIKE '" + rs.getString("StaffID") + "'");

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

				stdlist.add(new Staff(rs.getString("staffid"), rs.getString("fname"),rs.getString("mname"), rs.getString("lname"), rs.getString("gender"), rs.getString("c_addr"), rs.getString("p_addr"), contact[0], contact[1], contact[2], email[0], email[1], rs.getString("cnic"), rs.getString("dob"), rs.getString("qualification"), rs.getString("deptcode"), rs.getString("jobtitle"), rs.getString("salary")));
			}

		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
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
		col_qualify.setCellValueFactory(new PropertyValueFactory<>("qualification"));
		col_dcode.setCellValueFactory(new PropertyValueFactory<>("deptcode"));
		col_job.setCellValueFactory(new PropertyValueFactory<>("jobtitle"));
		col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

		table.setItems(stdlist);

	}

}
