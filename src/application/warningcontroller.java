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
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class warningcontroller implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);

	//Table View
	@FXML
	private TableView<warningclass> table;
    @FXML
    private TextField id;

    @FXML
    private Button btis;

    @FXML
    private Button btts;

    @FXML
    private Button btrw;
	@FXML
	private Label idlabel;

    @FXML
    private TableColumn<warningclass, String> col_id;

    @FXML
    private TableColumn<warningclass, String> col_sn;

    @FXML
    private TableColumn<warningclass, String> col_wc;

    ObservableList<warningclass> war = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DBConnector.getConnection();
			run();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			error.setContentText("Cannot establish connection with the server!");
			error.showAndWait();
			e.printStackTrace();
		}
	}

	public void run() {
		defaultlabel() ;
		if(table.getItems() != null) {
			table.getItems().clear();
		}

		String query1 = "SELECT Studentid, warningcount FROM  warning";

		try {

			ResultSet rs = con.createStatement().executeQuery(query1);
			if(rs.isBeforeFirst())
			{
				while(rs.next()) {
					String query2 = "SELECT Fname, Lname from student where stdid = '" + rs.getString("StudentID") + "'";
					ResultSet rs1 = con.createStatement().executeQuery(query2);
							if(rs1.isBeforeFirst())
							{
								while(rs1.next()) {
									System.out.println("A");
									war.add(new warningclass(rs.getString("StudentID"),rs1.getString("Fname")+" "+rs1.getString("Lname"),rs.getString("warningcount")));
								}}
							else if(!rs1.isBeforeFirst())
							{
								System.out.println("B");
								war.add(new warningclass(rs.getString("StudentID")," ",rs.getString("warningcount")));

								}
							}
				}

			}

		catch (Exception e) {

			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		}

		col_id.setCellValueFactory(new PropertyValueFactory<warningclass,String>("id"));
		col_sn.setCellValueFactory(new PropertyValueFactory<warningclass,String>("name"));

		col_wc.setCellValueFactory(new PropertyValueFactory<warningclass,String>("warning"));

		table.setItems(war);

		}

	public void issuewarning()
	{

		if(StringUtils.isNullOrEmpty(id.getText()))
		{
			id.setStyle("-fx-border-color: #d50000; -fx-border-radius: 10px; -fx-background-color: transparent;");
			idlabel.setText("Student ID*");
			idlabel.setTextFill(Color.web("#d50000"));
		}

		else {
		String query1 = "SELECT Studentid, warningcount FROM  warning where Studentid = '" + id.getText() + "'";
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

		try {

			ResultSet rs = con.createStatement().executeQuery(query1);
			if(rs.isBeforeFirst())
			{

				while(rs.next()) {
					String query2 = "UPDATE `warning` SET  `warningcount` = ? where `Studentid` LIKE ?";
					ps = con.prepareStatement(query2);

					ps.setString(2, id.getText());
					int temp = Integer.valueOf(rs.getString("warningcount"))+1;
					ps.setString(1, Integer.toString(temp));

					ps.execute();
				}
			}
			else {
				String query2 = "INSERT INTO `warning`(`studentid`, `warningcount`) VALUES (?, ?)";
				ps1 = con.prepareStatement(query2);

				ps1.setString(1, id.getText());
				ps1.setString(2, "1");

				ps1.execute();
			}
		}

		catch (Exception e) {

			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		}
		run();
		}

	}

	public void removewarning() {

		if(StringUtils.isNullOrEmpty(id.getText()))
		{
			id.setStyle("-fx-border-color: #d50000; -fx-border-radius: 10px; -fx-background-color: transparent;");
			idlabel.setText("Student ID*");
			idlabel.setTextFill(Color.web("#d50000"));
		}

		else {
		try{
			String query1 = "SELECT Studentid, warningcount FROM  warning where Studentid = '" + id.getText() + "'";
			ResultSet rs = con.createStatement().executeQuery(query1);
			if(!rs.isBeforeFirst()){
				throw new SQLException();
			}

			else{
				String query = "delete from `warning` where `studentid` LIKE ?";

				PreparedStatement ps = null;

				try {
					ps = con.prepareStatement(query);

					ps.setString(1, id.getText());

					ps.execute();

					run();

				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					error.setContentText("Unable to remove warning!");
					error.showAndWait();
				}
			}

		}
		catch(SQLException e){
			error.setContentText("Record not found!");
			error.showAndWait();
		}

		}
	}

	public void DeleteRecord() {
		defaultlabel() ;
		String query = "delete from `student` where `StdID` LIKE ?";
		String query1 = "DELETE FROM `uac` WHERE `Username`= ?";
		String query2 = "SELECT Studentid, warningcount FROM  warning where Studentid = '" + id.getText() + "'";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

			if(StringUtils.isNullOrEmpty(id.getText()))
			{
				id.setStyle("-fx-border-color: #d50000; -fx-border-radius: 10px; -fx-background-color: transparent;");
				idlabel.setText("Student ID*");
				idlabel.setTextFill(Color.web("#d50000"));
			}
		else {

		try{
			ResultSet rs = con.createStatement().executeQuery(query2);

			if(rs.isBeforeFirst()){
				ps = con.prepareStatement(query);
				ps1 = con.prepareStatement(query1);

				ps.setString(1, id.getText());
				ps1.setString(1, id.getText());

				ps.execute();
				ps1.execute();

				run();
			}

			else{
				throw new SQLException();
			}

		}
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		}
		}
	}

	public void defaultlabel()
	{

		id.setStyle("-fx-border-color: #3f51b5; -fx-border-radius: 10px; -fx-background-color: transparent;");
		idlabel.setText("Student ID");
		idlabel.setTextFill(Color.web("#000000"));

	}

}
