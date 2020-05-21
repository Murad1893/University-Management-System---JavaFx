package application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

public class marksController implements  Initializable {

	private static final Exception SQLException = null;
	Connection con = null;
	String UACID=null;
	Button[] btns = new Button[6];
	String section = null;
	String type = null;
	String conv_section = null;
	String weight = null;
	int i = 0;
	Alert error = new Alert(AlertType.ERROR);
	
	//Table View
	@FXML
	private TableView<StudentMarks> table;
	
	public static TableView<StudentMarks> table1;
	
    @FXML
    private AnchorPane tmarks;

    @FXML
    private ComboBox<String> Course;

    @FXML
    private TableColumn<StudentMarks, String> stdid;

    @FXML
    private TableColumn<StudentMarks, String> obtmarks;

    @FXML
    private Button mid;

    @FXML
    private Button assign;

    @FXML
    private Button proj;

    @FXML
    private Button quiz;

    @FXML
    private Button fin;
    
    @FXML
    private Button finalize;
    
    @FXML
	private Button section1;
	@FXML
	private Button section2;
	@FXML
	private Button section3;
	@FXML
	private Button section4;
	@FXML
	private Button section5;
	@FXML
	private Button section6;
    
	@FXML
	private TextField total_marks;
	
	ObservableList<StudentMarks> marks = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		table1 = table;
		
		try {
	
			con = DBConnector.getConnection();
			disableButton();
			run();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void run() {
		
		try {
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
			
			String query1 = "select DISTINCT courseid from staff_course where StaffID = '" + UACID + "'";
			
			ResultSet rs = con.createStatement().executeQuery(query1);
			
			while(rs.next()) {
				Course.getItems().add(rs.getString("courseid"));	
				Course.setPromptText("Course");
			}
		
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void returnSection1(ActionEvent e) {
		buttoneresest();
		section1.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section1.getText();
	}
	
	public void returnSection2(ActionEvent e) {
		buttoneresest();
		section2.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section2.getText();
	}
	
	public void returnSection3(ActionEvent e) {
		buttoneresest();
		section3.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section3.getText();
	}
	
	public void returnSection4(ActionEvent e) {
		buttoneresest();
		section4.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section4.getText();
	}
	
	public void returnSection5(ActionEvent e) {
		buttoneresest();
		section5.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		section = section5.getText();
	}
	public void returnSection6(ActionEvent e) {
		buttoneresest();
		section6.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
	
		section = section6.getText();
	}
	
	public void returntype1(ActionEvent e) {
		buttoneresest2();
		mid.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		weight = "30";
		type = mid.getText();
	}
	
	public void returntype2(ActionEvent e) {
		buttoneresest2();
		assign.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		weight = "5";
		type = assign.getText();
	}
	
	public void returntype3(ActionEvent e) {
		buttoneresest2();
		quiz.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		weight = "5";
		type = quiz.getText();
	}
	
	public void returntype4(ActionEvent e) {
		buttoneresest2();
		proj.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		weight = "10";
		type = proj.getText();
	}
	
	public void returntype5(ActionEvent e) {
		buttoneresest2();
		fin.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		weight = "50";
		type = fin.getText();
	}
	
	
	public void getCourse(ActionEvent e) {
			
			disableButton();
			String course = Course.getValue();
			int i = 5;
			String query1 = "Select DISTINCT sectionid from staff_course where StaffID = '" + UACID + "' AND CourseID = '" + course + "'";
	
			btns[0] = section1;
			btns[1] = section2;
			btns[2] = section3;
			btns[3] = section4;
			btns[4] = section5;
			btns[5] = section6;
			
			try {
				ResultSet rs = con.createStatement().executeQuery(query1);
				
				
				while(rs.next()) {
					btns[i].setDisable(false);
					
					btns[i].setText(rs.getString("sectionid").charAt(0) + "" + (char)(rs.getString("sectionid").charAt(5) - '0' + 64) + " - " + rs.getString("sectionid").substring(1,5));
					
					i--;
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

	public void disableButton() {
		buttoneresest();
	
		section1.setDisable(true);
		section1.setText("");
		
		section2.setDisable(true);
		section2.setText("");
		
		section3.setDisable(true);
		section3.setText("");
		
		section4.setDisable(true);
		section4.setText("");
		
		section5.setDisable(true);
		section5.setText("");
		
		section6.setDisable(true);
		section6.setText("");
	}
	
	public void AddRecords() {	
		
		if(Course.getValue()!=null) {
		
		if(!StringUtils.isNullOrEmpty(section)) {	
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		//conv_section = section.charAt(0) + section.substring(1,5)
		if(!total_marks.getText().equals("")) {
		String query1 = "INSERT INTO `marks` (`StudentID`, `CourseID`, `Type`, `Marks_Gained`, `Total_Marks`, `Weight_assigned`) VALUES (?,?,?,?,?,?)";
		String query2 = "SELECT s.StdID FROM student s, student_course sc where s.StdID = sc.StudentID and s.Sec_ID = '" + conv_section + "' and sc.CourseID = '" + Course.getValue() +"'";
		
		PreparedStatement ps = null;
		ResultSet rs;
		
		try {
			
			rs = con.createStatement().executeQuery(query2);
			
			
			while(rs.next()) {
							
				ps = con.prepareStatement(query1);
				
				ps.setString(1, rs.getString("s.StdID"));
				ps.setString(2, Course.getValue());
				ps.setString(3, type);
				ps.setString(4, null);
				ps.setString(5, total_marks.getText());
				ps.setString(6, weight);
				
				ps.execute();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error.setContentText(e.getMessage());
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Total marks not set yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
		
	}

	@SuppressWarnings("rawtypes")
	public void UpdateObtainedMarks(CellEditEvent e) {
		StudentMarks sm = table.getSelectionModel().getSelectedItem();
		
		
		String query1 = "UPDATE `marks` SET `Marks_Gained` = ? WHERE `marks`.`StudentID` = ? AND `marks`.`CourseID` = ? AND `marks`.`Type` = ?";
		String query2 = "SELECT DISTINCT Total_Marks FROM student s, marks m where s.StdID = m.StudentID and s.Sec_ID = '" + conv_section + "' and m.CourseID = '" + Course.getValue() + "' and m.Type = '" + type + "'";
	
		PreparedStatement ps = null;
		
		try{
			
			ps = con.prepareStatement(query1);
			ResultSet rs = con.createStatement().executeQuery(query2);
			rs.next();
			System.out.println(rs.getString("Total_Marks"));
			
			System.out.println(e.getNewValue().toString() + " " + rs.getString("Total_Marks"));
			
			if(Float.parseFloat(e.getNewValue().toString()) <= Float.parseFloat(rs.getString("Total_Marks"))) {
				sm.setMarks(e.getNewValue().toString());
				ps.setString(1, e.getNewValue().toString());
				ps.setString(2, sm.getId());
				ps.setString(3, Course.getValue());
				ps.setString(4, type);
				
				ps.execute();
			}
			else throw SQLException;
				
		}
		
		catch(SQLException e1) {
			error.setContentText("Marks entered > Total marks!");
			error.showAndWait();
		}
		
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
		}	
	}
	
	public void GradeCalculate() {
		
		if(Course.getValue()!=null) {
			
		if(!StringUtils.isNullOrEmpty(section)) {
		
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		String query = "INSERT INTO `grade` (`StudentID`, `CourseID`, `Points`, `Grade`) VALUES (?, ?, ?, ?)";
		String query1 = "SELECT s.StdID FROM student s, marks m where s.StdID = m.StudentID and s.Sec_ID = '" + conv_section + "' and m.CourseID = '" + Course.getValue() +"' and m.Type = '" + type + "'";
		String query3 = "Select count(DISTINCT type) as typecount from student s, marks m, student_course sc where s.StdID = m.StudentID and s.Sec_ID = '" + conv_section +"' and sc.CourseID = '" + Course.getValue() + "'";
		
		//ResultSet 
		ResultSet rs,rs1,rs2;
		PreparedStatement ps = null;
		
		try {
			rs = con.createStatement().executeQuery(query3);
			rs1 = con.createStatement().executeQuery(query1);
			ps = con.prepareStatement(query);
			
			rs.next();
			
			if(rs.getString("typecount").equals("5")) {
				
				while(rs1.next()) {
					
					String query2 = "select sum(Marks_Gained / Total_Marks * (Weight_assigned)) as mark from marks where StudentID = '" + rs1.getString("s.StdID") + "' and CourseID = '" + Course.getValue() + "'";
					
					rs2 = con.createStatement().executeQuery(query2);
					rs2.next();
					
					ps.setString(1, rs1.getString("s.StdID"));
					ps.setString(2, Course.getValue());
					
					if(Float.parseFloat(rs2.getString("mark")) >= 86) {
						ps.setString(3, "4.00");
						ps.setString(4, "A");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 82) {
						ps.setString(3, "3.67");
						ps.setString(4, "A-");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 78) {
						ps.setString(3, "3.33");
						ps.setString(4, "B+");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 74) {
						ps.setString(3, "3.00");
						ps.setString(4, "B");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 70) {
						ps.setString(3, "2.67");
						ps.setString(4, "B-");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 66) {
						ps.setString(3, "2.33");
						ps.setString(4, "C+");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 62) {
						ps.setString(3, "2.00");
						ps.setString(4, "C");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 58) {
						ps.setString(3, "1.67");
						ps.setString(4, "C-");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 54) {
						ps.setString(3, "1.33");
						ps.setString(4, "D");
					}
					else if(Float.parseFloat(rs2.getString("mark")) >= 50) {
						ps.setString(3, "1.00");
						ps.setString(4, "E");
					}
					else{
						ps.setString(3, "0.00");
						ps.setString(4, "F");
					}
					
					ps.execute();
				}
				
			}
			else throw new SQLException();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error.setContentText(e.getMessage());
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
		
	}
	
	public void ViewRecords() {
		
		String total = new String();
		
		if(table.getItems() != null) {
			table.getItems().clear();
		}
		
		if(Course.getValue()!=null) {
		
		if(!StringUtils.isNullOrEmpty(section)) {
		conv_section = section.charAt(0) + section.substring(5,9) + (section.charAt(1) - 64);
		
		String query1 = "SELECT s.StdID, m.Marks_Gained, m.Total_Marks FROM student s, marks m where s.StdID = m.StudentID and s.Sec_ID = '" + conv_section + "' and m.CourseID = '" + Course.getValue() +"' and m.Type = '" + type + "'";
		
		try {
			ResultSet rs = con.createStatement().executeQuery(query1);
			while(rs.next()) {
				marks.add(new StudentMarks(rs.getString("s.StdID"), rs.getString("m.Marks_Gained")));	
				total = rs.getString("m.Total_Marks");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error.setContentText(e.getMessage());
			error.showAndWait();
			e.printStackTrace();
		}
		
		stdid.setCellValueFactory(new PropertyValueFactory<StudentMarks,String>("id"));
		obtmarks.setCellValueFactory(new PropertyValueFactory<StudentMarks,String>("marks"));
		
		table.setItems(marks);
		
		table.setEditable(true);
		obtmarks.setCellFactory(TextFieldTableCell.forTableColumn());
		total_marks.setText(total);
		}
		else {
			error.setContentText("Section not selected yet!");
			error.showAndWait();
		}
		}
		else {
			error.setContentText("Course not selected yet!");
			error.showAndWait();
		}
	}
	public void buttoneresest()
	{
		buttoneresest2();
		section1.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section2.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section3.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section4.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		section5.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		section6.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
	
	
	}
	public void buttoneresest2()
	{
		
		mid.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		assign.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		quiz.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		proj.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		fin.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
	
	
	}
}
	
