package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class FeeCreationController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	
	//Table View
	@FXML
	private TableView<feecreation> table;
	
	//Table column
	@FXML
	private TableColumn<feecreation,String> col_studid;
	@FXML
	private TableColumn<feecreation,String> col_cost;
	@FXML
	private TableColumn<feecreation,String> col_duedate;
	@FXML
	private TableColumn<feecreation,String> col_status;

	
	
	
	@FXML
    private Text studidlabel;

    @FXML
    private TextField studid;

    @FXML
    private Button Refresh;

    @FXML
    private Text statuslabel;

    @FXML
    private RadioButton paid;

    @FXML
    private RadioButton unpaid;
	
	

	
 

    @FXML
    private Button UpdDel;


    @FXML
    private Button View;

    @FXML
    private Button ViewAll;

    @FXML
    private Button Refresh1;

    @FXML
    private DatePicker duedate;

    @FXML
    private Text duedatelabel;
    
    @FXML
    private DatePicker duedate1;

    @FXML
    private Text duedatelabel1;

    @FXML
    private Button generate;
	
	ObservableList<feecreation> courselist = FXCollections.observableArrayList();
	
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
						studid.setText(table.getSelectionModel().getSelectedItem().getStudid());
						if(table.getSelectionModel().getSelectedItem().getStatus().equals("Paid")) {
							paid.setSelected(true);							
						}
						else {
							unpaid.setSelected(true);
						}
						 duedate1.setValue(LOCAL_DATE(table.getSelectionModel().getSelectedItem().getDue()));
						
					
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
	
	public void defaultlabel() 
	{
		
		studidlabel.setText("Student ID");
		studidlabel.setFill(Color.web("#042954"));
		
		statuslabel.setText("Status");
		statuslabel.setFill(Color.web("#042954"));
		
		duedatelabel.setText("Due Date");
		duedatelabel.setFill(Color.web("#042954"));
		
		duedatelabel1.setText("Due Date");
		duedatelabel1.setFill(Color.web("#042954"));
		
		
	}
	
	
	
	
	public void UpdateStudentRecord(ActionEvent e) {

		
		String query = "UPDATE `studentfee` SET  `Status` = ? where `StdID` LIKE ? and `DueDate` LIKE ?";
		
		
		defaultlabel();
		PreparedStatement ps = null;
		if(StringUtils.isNullOrEmpty(studid.getText()) || duedate1.getValue()  == null ) {
			if(StringUtils.isNullOrEmpty(studid.getText())   )
			{
				
				studidlabel.setText("Student ID*");
				studidlabel.setFill(Color.web("#d50000"));
			}
			
			if(duedate1.getValue()  == null   )
			{
				
				duedatelabel1.setText("Due Date*");
				duedatelabel1.setFill(Color.web("#d50000"));
			}
		
		}
		
		else {

		
		try {
			ps = con.prepareStatement(query);

			
		
			ps.setString(2, studid.getText());
			
			if(paid.isSelected()) {
				ps.setString(1, "Paid");
			}
			else
				ps.setString(1, "Unpaid");
			ps.setString(3, duedate1.getValue().toString());

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
		
	
	
	public void RefreshRecord(ActionEvent e) {
		studid.clear();
		paid.setSelected(false);
		unpaid.setSelected(false);
		duedate.setValue(null);
		duedate1.setValue(null);
		defaultlabel();

	}
	
	public void SearchStudentRecord(ActionEvent e) {

		defaultlabel();	
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			String query = "select * from `studentfee` where `StdID` LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, studid.getText());
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			if(!rs.isBeforeFirst()) {
				throw new SQLException();
			}
			
			else {
				
				
				while(rs.next()) {

					
					courselist.add(new feecreation(rs.getString("StdID"), rs.getString("Fees"),rs.getString("DueDate"),rs.getString("Status")));				}
			}
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
		} 
		
		col_studid.setCellValueFactory(new PropertyValueFactory<>("studid"));
		col_cost.setCellValueFactory(new PropertyValueFactory<>("total"));
		col_duedate.setCellValueFactory(new PropertyValueFactory<>("due"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

				
		table.setItems(courselist);
		
		
	
	}
	
	public void ViewStudentRecord(ActionEvent Event) {

		defaultlabel();
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		try {
			
			ResultSet rs = con.createStatement().executeQuery("select * from `studentfee`");
			while(rs.next()) {
				courselist.add(new feecreation(rs.getString("StdID"), rs.getString("Fees"),rs.getString("DueDate"),rs.getString("Status")));
			}
			}
			
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}

		col_studid.setCellValueFactory(new PropertyValueFactory<>("studid"));
		col_cost.setCellValueFactory(new PropertyValueFactory<>("total"));
		col_duedate.setCellValueFactory(new PropertyValueFactory<>("due"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

				
		table.setItems(courselist);
	
	}
	
	public void generatefee (ActionEvent e)
	{
		defaultlabel();
		if(duedate.getValue()  == null) {
			
				
				duedatelabel.setText("Due Date*");
				duedatelabel.setFill(Color.web("#d50000"));
			
		
		}
		
		else {
		double total;
		ResultSet rs, rs1,rs2 = null;
		try {
			String query = "select * from `student`";
			System.out.println("A");
			PreparedStatement ps = con.prepareStatement(query);
			ps.execute();
			System.out.println("B");
			rs = ps.getResultSet();
			if(!rs.isBeforeFirst()) {
				
				throw new SQLException();
			}
			else {
				while(rs.next()) {
					total=0;
					System.out.println(rs.getString("StdID"));
					rs1 = con.createStatement().executeQuery("SELECT SUM(CreditHour) FROM `course` WHERE CourseCode IN (SELECT CourseID FROM `student_course` where StudentID LIKE '" + rs.getString("StdID") + "')");
					
					if(rs1.isBeforeFirst()) {
						System.out.println(rs.getString("StdID"));
						rs1.next();
						System.out.println(rs1.getString("SUM(CreditHour)"));
						rs2 = con.createStatement().executeQuery("select Fees from `fee` where DeptCode LIKE '" + rs.getString("DeptCode") + "'");
						if(rs1.getString("SUM(CreditHour)")!=null) 
						{	
							rs2.next();
							System.out.println(rs.getString("StdID"));
							total = Double.valueOf(rs1.getString("SUM(CreditHour)")) * Double.valueOf(rs2.getString("Fees"));
							String query2 = "INSERT INTO `studentfee`(`StdID`, `Fees`,`DueDate`, `Status`)  VALUES (?, ?, ?, ?)"; 
							System.out.println("D");
							try {
								System.out.println("E");
								PreparedStatement ps2 = con.prepareStatement(query2);
								ps2.setString(1, rs.getString("StdID"));
								ps2.setString(2, String.valueOf(total));
								ps2.setString(3, duedate.getValue().toString());
								ps2.setString(4, "Unpaid");
								ps2.execute();
								ViewStudentRecord(e);
								
							}
							catch (Exception e1) {
								// TODO Auto-generated catch block
								
							}
						}
						
					}
		
						
					}
				}
		}
		
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
		
	}

}


