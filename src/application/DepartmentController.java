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


public class DepartmentController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);

	//Table View
	@FXML
	private TableView<Department> table;

	//Table column
	@FXML
	private TableColumn<Department,String> col_deptcode;
	@FXML
	private TableColumn<Department,String> col_deptname;
	@FXML
	private TableColumn<Department,String> col_deptshortname;
	@FXML
	private TableColumn<Department,String> col_hod;




	//Text Field
	@FXML
	private TextField deptid;
	@FXML
	private TextField deptname;
	@FXML
	private TextField deptshortname;
	@FXML
	private TextField hod;
	@FXML
	private Text deptcodelabel;
	@FXML
	private Text deptnamelabel;
	@FXML
	private Text deptshortnamelabel;
	@FXML
	private Text hodlabel;




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

	ObservableList<Department> courselist = FXCollections.observableArrayList();

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
						deptname.setText(table.getSelectionModel().getSelectedItem().getDeptname());
						deptshortname.setText(table.getSelectionModel().getSelectedItem().getDeptShortName());
						hod.setText(table.getSelectionModel().getSelectedItem().getHod());


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

		deptnamelabel.setText("Department Name");
		deptnamelabel.setFill(Color.web("#042954"));

		deptshortnamelabel.setText("Department Short Name");
		deptshortnamelabel.setFill(Color.web("#042954"));

		hodlabel.setText("Head Of Department");
		hodlabel.setFill(Color.web("#042954"));
	}


	public void InsertStudentRecord(ActionEvent e) {

		defaultlabel();
		String query = "INSERT INTO `department`(`DeptCode`, `DeptName`,`DeptShortName`, `HOD`)  VALUES (?, ?, ?, ?)";


		PreparedStatement ps = null;
		if(StringUtils.isNullOrEmpty(deptid.getText()) || StringUtils.isNullOrEmpty(deptname.getText()) || StringUtils.isNullOrEmpty(deptshortname.getText())) {
			if(StringUtils.isNullOrEmpty(deptid.getText()))
			{

				deptcodelabel.setText("Department Code*");
				deptcodelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(deptname.getText()))
			{

				deptnamelabel.setText("Department Name*");
				deptnamelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(deptshortname.getText()))
			{

				deptshortnamelabel.setText("Department Short Name*");
				deptshortnamelabel.setFill(Color.web("#d50000"));
			}

		}

		else {
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, deptid.getText());
			ps.setString(2, deptname.getText());
			ps.setString(3, deptshortname.getText());

			if(StringUtils.isNullOrEmpty(hod.getText())) {
				ps.setString(4, null);
			}
			else
				ps.setString(4, hod.getText());
			ps.execute();
			ViewStudentRecord(e);


		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Department record already exists!");
			error.showAndWait();
		}
		}
	}

	public void UpdateStudentRecord(ActionEvent e) {

		String query = "UPDATE `department` SET  `DeptName` = ?,`DeptShortName` = ?, `HOD` = ? where `DeptCode` LIKE ?";

		defaultlabel();
		PreparedStatement ps = null;
		if(StringUtils.isNullOrEmpty(deptid.getText()) || StringUtils.isNullOrEmpty(deptname.getText()) || StringUtils.isNullOrEmpty(deptshortname.getText())) {
			if(StringUtils.isNullOrEmpty(deptid.getText()))
			{

				deptcodelabel.setText("Department Code*");
				deptcodelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(deptname.getText()))
			{

				deptnamelabel.setText("Department Name*");
				deptnamelabel.setFill(Color.web("#d50000"));
			}
			if(StringUtils.isNullOrEmpty(deptshortname.getText()))
			{

				deptshortnamelabel.setText("Department Short Name*");
				deptshortnamelabel.setFill(Color.web("#d50000"));
			}
		}

		else {


		try {
			ps = con.prepareStatement(query);

			ps.setString(4, deptid.getText());
			ps.setString(1, deptname.getText());
			ps.setString(2, deptshortname.getText());

			if(searchdept()){
				if(StringUtils.isNullOrEmpty(hod.getText())) {
					ps.setString(3, null);
				}
				else
					ps.setString(3, hod.getText());

				ps.execute();
				ViewStudentRecord(e);
			}
			else{
				error.setContentText("Record not found!");
				error.showAndWait();
			}
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Unable to update record!");
			error.showAndWait();
		}
		}
	}

	public void DeleteStudentRecord(ActionEvent e) {

		String query = "delete from `department` where `DeptCode` LIKE ?";

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(query);

			ps.setString(1, deptid.getText());

			if(searchdept()){
				ps.execute();
				defaultlabel();
				ViewStudentRecord(e);
			}
			else{
				error.setContentText("Record not found!");
				error.showAndWait();
			}
		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Unable to delete record!");
			error.showAndWait();
		}
	}

	public void RefreshRecord(ActionEvent e) {
		deptid.clear();
		deptname.clear();
		deptshortname.clear();
		hod.clear();
		defaultlabel();

	}

	boolean searchdept(){
		try {
			String query = "select * from `department` where `DeptCode` LIKE ?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, deptid.getText());
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

	public void SearchStudentRecord(ActionEvent e) {
		defaultlabel();

		if(table.getItems() != null) {
			table.getItems().clear();
		}

		try {

			String query = "select * from `department` where `DeptCode` LIKE ?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, deptid.getText());
			ps.execute();

			ResultSet rs = ps.getResultSet();

			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}

			else {
				while(rs.next()) {
					courselist.add(new Department(rs.getString("DeptCode"), rs.getString("DeptName"),rs.getString("DeptShortName"),rs.getString("HOD")));				}
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		}

		col_deptcode.setCellValueFactory(new PropertyValueFactory<>("deptid"));
		col_deptname.setCellValueFactory(new PropertyValueFactory<>("deptname"));
		col_deptshortname.setCellValueFactory(new PropertyValueFactory<>("DeptShortName"));
		col_hod.setCellValueFactory(new PropertyValueFactory<>("hod"));

		table.setItems(courselist);

	}

	public void ViewStudentRecord(ActionEvent Event) {

		defaultlabel();
		if(table.getItems() != null) {
			table.getItems().clear();
		}

		try {

			ResultSet rs = con.createStatement().executeQuery("select * from `department`");
			while(rs.next()) {
				courselist.add(new Department(rs.getString("DeptCode"), rs.getString("DeptName"),rs.getString("DeptShortName"),rs.getString("HOD")));
			}
			}

		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText("Cannot fetch records!");
			error.showAndWait();
		}

		col_deptcode.setCellValueFactory(new PropertyValueFactory<>("deptid"));
		col_deptname.setCellValueFactory(new PropertyValueFactory<>("deptname"));
		col_deptshortname.setCellValueFactory(new PropertyValueFactory<>("DeptShortName"));
		col_hod.setCellValueFactory(new PropertyValueFactory<>("hod"));


		table.setItems(courselist);
	}

}
