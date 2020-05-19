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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentCourseRegistrationController implements Initializable {
	
	Connection con = null;
	String UACID=null;
	Alert error = new Alert(AlertType.ERROR);
	
	//Table View
	@FXML
	private TableView<CourseRegistration> table;
	
    @FXML
    private Label rollno;

    @FXML
    private Label name;

    @FXML
    private Label batch;

    @FXML
    private Label section;

    @FXML
    private Label cattmp;

    @FXML
    private Label status;
    
    @FXML
    private Label depart;

    @FXML
    private Label warning;


    @FXML
    private TableColumn<CourseRegistration, String> col_course;

    @FXML
    private TableColumn<CourseRegistration, String> col_chr;
    
    @FXML
    private TableColumn<CourseRegistration, String> col_status;
    
    @FXML
    private TableColumn<CourseRegistration, Button> action;
    
    ObservableList<CourseRegistration> courses = FXCollections.observableArrayList();
    
    public void initialize(URL location, ResourceBundle resources) {
		try {

			con = DBConnector.getConnection();
			run();
	
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
    public void run()
	{
		try {
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();

			fd.close();
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
				ps.setString(1, UACID);
				throw new SQLException();
			}
			
			else {
			
				while(rs.next()) {
					ResultSet rs4 = con.createStatement().executeQuery("select * from `warning` where `Studentid` LIKE '" + UACID + "'");
					ResultSet rs3 = con.createStatement().executeQuery("select * from `department` where `DeptCode` LIKE '" + rs.getString("DeptCode") + "'");
					ResultSet rs2 = con.createStatement().executeQuery("SELECT SUM(CreditHour) FROM `course` WHERE CourseCode IN (SELECT CourseID FROM `student_course` where StudentID LIKE '" + rs.getString("StdID") + "')");
					String deptname = null;
					String ca=null;
					
					while(rs3.next()) {
						
						deptname = rs3.getString("DeptName");

					}
					while(rs2.next()) {
						
						ca = rs2.getString("SUM(CreditHour)");
					}
					while(rs4.next()) {
						
						warning.setText(rs4.getString("warningcount"));
					}
		
					char sec = (char)(rs.getString("sEC_iD").charAt(5) - '0' + 64);
					
					rollno.setText(rs.getString("StdID"));
					name.setText(rs.getString("Fname")+" "+rs.getString("Lname"));
					cattmp.setText(ca);
					batch.setText(rs.getString("batch").substring(0,4));
					section.setText(String.valueOf(sec));	
					depart.setText(deptname);

				}
			}
			
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			error.setContentText("Record not found!");
			error.showAndWait();
			e1.printStackTrace();
		} 
		
		String query = "Select DISTINCT CourseCode, coursename, credithour from course c, student s where c.Offer_dept = s.DeptCode and StdID = '" + UACID + "'";
		ResultSet rs, rs1, rs2, rs3, rs4 = null;
		
		try {
			rs = con.createStatement().executeQuery(query);
			
			while(rs.next()) {	
			
				String query3 = "select CourseID from student_course where StudentID = '"+ UACID +"' and CourseID = '"+ rs.getString("CourseCode") +"'";
				rs3 = con.createStatement().executeQuery(query3);
				
			if(rs3.isBeforeFirst()) {
				rs3.next();
				String query4 = "select courseid, grade from grade where courseid = '" + rs3.getString("CourseID") + "'and StudentID = '" + UACID + "'";
				
				rs4 = con.createStatement().executeQuery(query4);
				
					if(rs4.isBeforeFirst()) {
						rs4.next();
						
						if(rs4.getString("grade").equals("F")) {
							System.out.println(rs3.getString("CourseID") + " Failed");
								courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Course failed" ,new Button("Register"), false));		
						}
						
						else {
							System.out.println(rs3.getString("CourseID") + " Passed");
								courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Course Passed" ,new Button("Register"), true));
						}
					}
					else {
						System.out.println(rs.getString("CourseCode") + " taken");
							courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Course Taken" ,new Button("Register"), true));
						}
					}
		
			else {
				String query1 = "select PreqCourseCode from prerequisite where CourseCode = '" + rs.getString("CourseCode") + "'";
				
				rs1 = con.createStatement().executeQuery(query1);
						
					if(!rs1.isBeforeFirst()) {
						//add to table
						courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Not registered" ,new Button("Register"), false));
					}
					
					else {
						
							String repeat = "";
							boolean fail = false;
							while(rs1.next()) {	
															
								String query2 = "select courseid, grade from grade where courseid = '" + rs1.getString("PreqCourseCode") + "'and StudentID = '" + UACID + "'";
								
								rs2 = con.createStatement().executeQuery(query2);
								
									if(rs2.isBeforeFirst()) {
										rs2.next();
										
										if(rs2.getString("grade").equals("F")) {
											System.out.println(rs.getString("CourseCode") + " " + rs1.getString("PreqCourseCode") + " Failed");
											if(!repeat.equals(rs.getString("CourseCode"))) {
												courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Preqrequiste failed" ,new Button("Register"), true));
												repeat = rs.getString("CourseCode");
											}
											fail = true;
										}
										
										else {
											System.out.println(rs.getString("CourseCode") + " " + rs1.getString("PreqCourseCode") + " Passed");
										}
									}
									else {
										System.out.println(rs.getString("CourseCode") + " Not taken");
										if(!repeat.equals(rs.getString("CourseCode"))) {
											courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Preqrequiste not taken" ,new Button("Register"), true));
											repeat = rs.getString("CourseCode");
										}
										fail = true;
									}
							}
							
							if(fail == false) {
								courses.add(new CourseRegistration(rs.getString("CourseCode") + "-" + rs.getString("coursename"), rs.getString("credithour"), "Not registered" ,new Button("Register"), false));
							}	
					}	
				}	
			}
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			error.setContentText(e1.getMessage());
			error.showAndWait();
			e1.printStackTrace();
		} 
		
		col_course.setCellValueFactory(new PropertyValueFactory<CourseRegistration,String>("course"));
		col_chr.setCellValueFactory(new PropertyValueFactory<CourseRegistration,String>("credit"));
		col_status.setCellValueFactory(new PropertyValueFactory<CourseRegistration,String>("status"));
		action.setCellValueFactory(new PropertyValueFactory<CourseRegistration,Button>("action"));
		
		table.setItems(courses);
	}
    
}
