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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class AdminControllerCourse implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);

	//Table View
	@FXML
	private TableView<Course> table;

	//Table column
	@FXML
	private TableColumn<Course,String> col_id;
	@FXML
	private TableColumn<Course,String> col_cname;
	@FXML
	private TableColumn<Course,String> col_cr;
	@FXML
	private TableColumn<Course,String> col_dcode;
	@FXML
	private TableColumn<Course,String> col_ccode1;
	@FXML
	private TableColumn<Course,String> col_ccode2;
	@FXML
	private TableColumn<Course,String> col_ccode3;
	@FXML
	private TableColumn<Course,String> col_ccode4;

	//Text Field
	@FXML
	private TextField id;
	@FXML
	private TextField cname;
	@FXML
	private TextField dcode;
	@FXML
	private TextField ccode1;
	@FXML
	private TextField ccode2;
	@FXML
	private TextField ccode3;
	@FXML
	private TextField ccode4;
	@FXML
	private TextField ID;


	//Radio Button
	@FXML
	private RadioButton cr1;
	@FXML
	private RadioButton cr2;
	@FXML
	private RadioButton cr3;
	@FXML
	private RadioButton cr4;
	@FXML
	private Text idlabel;
	@FXML
	private Text cnamelabel;
	@FXML
	private Text crlabel;
	@FXML
	private Text dcodelabel;



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
	@FXML
	private Button button1;

	ObservableList<Course> courselist = FXCollections.observableArrayList();

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
						id.setText(table.getSelectionModel().getSelectedItem().getId());
						dcode.setText(table.getSelectionModel().getSelectedItem().getDeptcode());
						cname.setText(table.getSelectionModel().getSelectedItem().getCname());
						ccode1.setText(table.getSelectionModel().getSelectedItem().getPreq1());
						ccode2.setText(table.getSelectionModel().getSelectedItem().getPreq2());
						ccode3.setText(table.getSelectionModel().getSelectedItem().getPreq3());
						ccode4.setText(table.getSelectionModel().getSelectedItem().getPreq4());

						if(table.getSelectionModel().getSelectedItem().getCredit().equals("1")) {
							cr1.setSelected(true);
						}
						else if(table.getSelectionModel().getSelectedItem().getCredit().equals("2")) {
							cr2.setSelected(true);
						}
						else if(table.getSelectionModel().getSelectedItem().getCredit().equals("3")) {
							cr3.setSelected(true);
						}
						else if(table.getSelectionModel().getSelectedItem().getCredit().equals("4")) {
							cr4.setSelected(true);
						}
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
		idlabel.setText("Course ID");
		idlabel.setFill(Color.web("#042954"));

		cnamelabel.setText("Course Name");
		cnamelabel.setFill(Color.web("#042954"));

		dcodelabel.setText("Offering Department");
		dcodelabel.setFill(Color.web("#042954"));
		crlabel.setText("Choose Credit Hours");
		crlabel.setFill(Color.web("#042954"));

	}

	public void InsertCourseRecord(ActionEvent e){
		defaultlabel();
		String query = "INSERT INTO `course`(`CourseCode`, `CourseName`, `CreditHour`, `Offer_dept`) VALUES (?, ?, ?, ?)";
		String query1 = "INSERT INTO `prerequisite`(`CourseCode`, `PreqCourseCode`) VALUES (?, ?)";
		String query2 = "SELECT DISTINCT department.DeptShortName as Dept from department where DeptCode = " + dcode.getText();
		String query3 = "SELECT `course`.`coursecode` FROM `course`";
		String query4 = "DELETE FROM `course` WHERE `course`.`CourseCode` LIKE ?";

		int i = 1;

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		boolean check = cr1.isSelected() || cr2.isSelected() || cr3.isSelected() || cr4.isSelected();
		if( StringUtils.isNullOrEmpty(cname.getText()) || StringUtils.isNullOrEmpty(dcode.getText()) || !check) {

			if(StringUtils.isNullOrEmpty(cname.getText()))
			{


				cnamelabel.setText("Course Name*");
				cnamelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(dcode.getText()))
			{


				dcodelabel.setText("Offering Department*");
				dcodelabel.setFill(Color.web("#d50000"));
			}
			if(!check)
			{

				crlabel.setText("Choose Credit Hours*");
				crlabel.setFill(Color.web("#d50000"));

			}

		}

		else {

		try {
			String code;
			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query4);

			ResultSet rs = con.createStatement().executeQuery(query2);
			ResultSet rs1 = con.createStatement().executeQuery(query3);

			if(i<10) {
				rs.next();
				code = rs.getString("Dept") + "0" + Integer.toString(i);
			}
				else
				code = rs.getString("Dept") + Integer.toString(i);

			while(rs1.next()) {
				if(rs1.getString("coursecode").equals(code)) {
					i+=1;
					if(i<10)
						code = rs.getString("Dept") + "0" + Integer.toString(i);
					else
						code = rs.getString("Dept") + Integer.toString(i);
				}
			}

			ps.setString(1, code);
			ps2.setString(1, code);

			ps.setString(2, cname.getText());
			if(cr1.isSelected())
				ps.setString(3, cr1.getText());
			else if(cr2.isSelected())
				ps.setString(3, cr2.getText());
			else if(cr3.isSelected())
				ps.setString(3, cr3.getText());
			else if(cr4.isSelected())
				ps.setString(3, cr4.getText());
			ps.setString(4, dcode.getText());

			ps.execute();

			if(id.getText().equalsIgnoreCase(ccode1.getText()) || id.getText().equalsIgnoreCase(ccode2.getText()) || id.getText().equalsIgnoreCase(ccode3.getText()) ||
					id.getText().equalsIgnoreCase(ccode4.getText())) {
				error.setContentText("Course cannot be its own prerequisite!");
				error.showAndWait();
				return;
			}
			
			if(!StringUtils.isNullOrEmpty(ccode1.getText())) {

				ps1.setString(1, code);
				ps1.setString(2, ccode1.getText().toUpperCase());
				ps1.execute();
			}

			if(!StringUtils.isNullOrEmpty(ccode2.getText())) {

					ps1.setString(1, code);
					ps1.setString(2, ccode2.getText().toUpperCase());
					ps1.execute();
			}

			if(!StringUtils.isNullOrEmpty(ccode3.getText())) {

					ps1.setString(1, code);
					ps1.setString(2, ccode3.getText().toUpperCase());
					ps1.execute();
			}

			if(!StringUtils.isNullOrEmpty(ccode4.getText())) {
					ps1.setString(1, code);
					ps1.setString(2, ccode4.getText().toUpperCase());
					ps1.execute();
			}

			ViewCourseRecord(e);

		}
		catch (Exception e1) {
			String s = new String();
			
			try {
				ps2.execute();
			} catch (SQLException e2) {
				error.setContentText("Department does not exist!");
				error.showAndWait();
				return;
			}
		
			s = e1.getMessage();
		
			if(s.contains("Duplicate")) {
				error.setContentText("Course with these entries already exists!");
				error.showAndWait();
			}
			else if(s.contains("prerequisite")) {
				error.setContentText("Prerequisite course does not exist!");
				error.showAndWait();
			}
		}}
	}

	public void UpdateCourseRecord(ActionEvent e) {

		defaultlabel();
		String query = "UPDATE `course` SET  `CourseName` = ?, `CreditHour` = ?, `Offer_dept` = ? where `CourseCode` LIKE ?";
		String query1 = "INSERT INTO `prerequisite`(`CourseCode`, `PreqCourseCode`) VALUES (?, ?)";
		String query2 = "DELETE  FROM `prerequisite` where CourseCode = ?";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		boolean check = cr1.isSelected() || cr2.isSelected() || cr3.isSelected() || cr4.isSelected();
		if( StringUtils.isNullOrEmpty(cname.getText())|| StringUtils.isNullOrEmpty(id.getText()) || StringUtils.isNullOrEmpty(dcode.getText()) || !check) {

			if(StringUtils.isNullOrEmpty(id.getText()))
			{


				idlabel.setText("Course ID*");
				idlabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(cname.getText()))
			{


				cnamelabel.setText("Course Name*");
				cnamelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(dcode.getText()))
			{


				dcodelabel.setText("Offering Department*");
				dcodelabel.setFill(Color.web("#d50000"));
			}
			if(!check)
			{

				crlabel.setText("Choose Credit Hours*");
				crlabel.setFill(Color.web("#d50000"));

			}

		}
		else {

		try {
			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query2);

			ps.setString(4, id.getText());
			ps.setString(1, cname.getText());

			if(!searchcourse()) {
				error.setContentText("Course ID not found!");
				error.showAndWait();
				return;
			}
			

			if(cr1.isSelected())
				ps.setString(2, cr1.getText());
			else if(cr2.isSelected())
				ps.setString(2, cr2.getText());
			else if(cr3.isSelected())
				ps.setString(2, cr3.getText());
			else if(cr4.isSelected())
				ps.setString(2, cr4.getText());
			ps.setString(3, dcode.getText());
			ps.execute();

			ps2.setString(1, id.getText());

			if(id.getText().equalsIgnoreCase(ccode1.getText()) || id.getText().equalsIgnoreCase(ccode2.getText()) || id.getText().equalsIgnoreCase(ccode3.getText()) ||
					id.getText().equalsIgnoreCase(ccode4.getText())) {
				error.setContentText("Course cannot be its own prerequisite!");
				error.showAndWait();
				return;
			}

			ps2.execute();

			if(!StringUtils.isNullOrEmpty(ccode1.getText())) {

				ps1.setString(1, id.getText());
				ps1.setString(2, ccode1.getText().toUpperCase());
				ps1.execute();
			}

			if(!StringUtils.isNullOrEmpty(ccode2.getText())) {

					ps1.setString(1, id.getText());
					ps1.setString(2, ccode2.getText().toUpperCase());
					ps1.execute();
			}

			if(!StringUtils.isNullOrEmpty(ccode3.getText())) {

					ps1.setString(1, id.getText());
					ps1.setString(2, ccode3.getText().toUpperCase());
					ps1.execute();
			}

			if(!StringUtils.isNullOrEmpty(ccode4.getText())) {
					ps1.setString(1, id.getText());
					ps1.setString(2, ccode4.getText().toUpperCase());
					ps1.execute();
			}

			ViewCourseRecord(e);

		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			ViewCourseRecord(e);
			String s = e1.getMessage();
		
			if(s.contains("prerequisite")) {
				error.setContentText("Prerequisite course does not exist!");
				error.showAndWait();
			}
			else if(s.contains("department")) {
				error.setContentText("Deparment does not exist!");
				error.showAndWait();
			}
			else if(s.contains("Duplicate")) {
				error.setContentText("Course with these entries already exists!");
				error.showAndWait();
			}
			else{
				error.setContentText("Unable to update course record!");
				error.showAndWait();
			}
		}
		}

	}

	public void DeleteCourseRecord(ActionEvent e) {
		defaultlabel();
		if(StringUtils.isNullOrEmpty(id.getText()))
		{

			idlabel.setText("Course ID*");
			idlabel.setFill(Color.web("#d50000"));
		}
		else {
		String query = "delete from `course` where `CourseCode` LIKE ?";

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(query);

			ps.setString(1, id.getText());

			if(!searchcourse()) {
				error.setContentText("Course ID not found!");
				error.showAndWait();
				return;
			}
			
			ps.execute();

			ViewCourseRecord(e);

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Unable to delete course record!");
			error.showAndWait();
		}
		}
	}

	boolean searchcourse(){
		try {
			String query = "select * from `course` where `CourseCode` LIKE ?";

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
	
	public void SearchCourseRecord(ActionEvent e) {
		defaultlabel();
		if(StringUtils.isNullOrEmpty(id.getText()))
		{

			idlabel.setText("Course ID*");
			idlabel.setFill(Color.web("#d50000"));
		}
		else {

		if(table.getItems() != null) {
			table.getItems().clear();
		}

		try {

			String query = "select * from `course` where `CourseCode` LIKE ?";

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

					ResultSet rs1 = con.createStatement().executeQuery("select * from `prerequisite` where `CourseCode` LIKE '" + rs.getString("CourseCode") + "'");

					String[] pre = new String[4];

					while(rs1.next()) {
						pre[i] = rs1.getString("PreqCourseCode");
						i++;
					}



					courselist.add(new Course(rs.getString("CourseCode"), rs.getString("CourseName"),rs.getString("CreditHour"), rs.getString("Offer_dept"), pre[0], pre[1], pre[2], pre[3]));				}
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		}

		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
		col_cr.setCellValueFactory(new PropertyValueFactory<>("credit"));
		col_dcode.setCellValueFactory(new PropertyValueFactory<>("deptcode"));
		col_ccode1.setCellValueFactory(new PropertyValueFactory<>("preq1"));
		col_ccode2.setCellValueFactory(new PropertyValueFactory<>("preq2"));
		col_ccode3.setCellValueFactory(new PropertyValueFactory<>("preq3"));
		col_ccode4.setCellValueFactory(new PropertyValueFactory<>("preq4"));

		table.setItems(courselist);

		}
	}

	public void ViewCourseRecord(ActionEvent Event) {

		int i = 0;

		if(table.getItems() != null) {
			table.getItems().clear();
		}

		try {

			ResultSet rs = con.createStatement().executeQuery("select * from `course`");

			while(rs.next()) {

				ResultSet rs1 = con.createStatement().executeQuery("select * from `prerequisite` where `CourseCode` LIKE '" + rs.getString("CourseCode") + "'");


				String[] pre = new String[4];


				i = 0;

				while(rs1.next()) {
					pre[i] = rs1.getString("PreqCourseCode");
					i++;
				}

				i = 0;



				courselist.add(new Course(rs.getString("CourseCode"), rs.getString("CourseName"),rs.getString("CreditHour"), rs.getString("Offer_dept"), pre[0], pre[1], pre[2], pre[3]));
			}

		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}

		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
		col_cr.setCellValueFactory(new PropertyValueFactory<>("credit"));
		col_dcode.setCellValueFactory(new PropertyValueFactory<>("deptcode"));
		col_ccode1.setCellValueFactory(new PropertyValueFactory<>("preq1"));
		col_ccode2.setCellValueFactory(new PropertyValueFactory<>("preq2"));
		col_ccode3.setCellValueFactory(new PropertyValueFactory<>("preq3"));
		col_ccode4.setCellValueFactory(new PropertyValueFactory<>("preq4"));


		table.setItems(courselist);
	}

	public void RefreshRecord(ActionEvent e) {
		defaultlabel();
		id.clear();
		cname.clear();
		dcode.clear();
		ccode1.clear();
		ccode2.clear();
		ccode3.clear();
		ccode4.clear();
		cr1.setSelected(false);
		cr2.setSelected(false);
		cr3.setSelected(false);
		cr4.setSelected(false);
	}

}
