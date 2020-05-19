package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.util.StringUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class StudentMarkViewController implements Initializable {
	
	Connection con = null;
	String UACID=null, course = null;
	Button[] btns = new Button[8];
	Alert error = new Alert(AlertType.ERROR);
	int i=7;
	
	@FXML
    private Button course8;

    @FXML
    private Button course7;

    @FXML
    private Button course6;

    @FXML
    private Button course5;

    @FXML
    private Button course4;

    @FXML
    private Button course3;

    @FXML
    private Button course2;

    @FXML
    private Button course1;

    @FXML
    private Label midtotal;

    @FXML
    private Label midobt;

    @FXML
    private Label midweight;

    @FXML
    private Label assigntotal;

    @FXML
    private Label assignobt;

    @FXML
    private Label assignweight;

    @FXML
    private Label projecttotal;

    @FXML
    private Label projectobt;

    @FXML
    private Label projectweight;

    @FXML
    private Label finaltotal;

    @FXML
    private Label finalobt;

    @FXML
    private Label finalweight;

    @FXML
    private Label quiztotal;

    @FXML
    private Label quizobt;

    @FXML
    private Label quizweight;
    
    @FXML
    private Label grandtotal;

    @FXML
    private Label grandobt;

    @FXML
    private Label grandweight;

    @FXML
    private Label grade;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			try {
				
				disableButton();
				
				con = DBConnector.getConnection();
				
				BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
				UACID = fd.readLine();
				fd.close();
				
				btns[0] = course1;
				btns[1] = course2;
				btns[2] = course3;
				btns[3] = course4;
				btns[4] = course5;
				btns[5] = course6;
				btns[6] = course7;
				btns[7] = course8;
				
				
				String query = "select courseid from student_course where StudentID = '" + UACID + "'";
				
				try {
					ResultSet rs = con.createStatement().executeQuery(query);
					
					
					while(rs.next()) {
			
						btns[i].setDisable(false);
						
						btns[i].setText(rs.getString("courseid"));
						
						i--;
					}
					
					i = 7;
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} 
			catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void ResetAllLabels() {
			midtotal.setText("-");
			midobt.setText("-");
			midweight.setText("-");
			
			assigntotal.setText("-");
			assignobt.setText("-");
			assignweight.setText("-");
			
			finaltotal.setText("-");
			finalobt.setText("-");
			finalweight.setText("-");
			
			quiztotal.setText("-");
			quizobt.setText("-");
			quizweight.setText("-");
			
			projecttotal.setText("-");
			projectobt.setText("-");
			projectweight.setText("-");
			
			grandtotal.setText("-");
			grandobt.setText("-");
			grandweight.setText("-");
			
			grade.setText("-");
		
		}
	
		public void returncourse1(ActionEvent e) throws SQLException {
			buttoneresest();
			ResetAllLabels();
			course = course1.getText();
			course1.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse2(ActionEvent e) throws SQLException {
			ResetAllLabels();
			buttoneresest();
			course = course2.getText();
			course2.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse3(ActionEvent e) throws SQLException {
			ResetAllLabels();
			buttoneresest();
			course = course3.getText();
			course3.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse4(ActionEvent e) throws SQLException {
			ResetAllLabels();
			buttoneresest();
			course = course4.getText();
			course4.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse5(ActionEvent e) throws SQLException {
			ResetAllLabels();
			buttoneresest();
			course = course5.getText();
			course5.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse6(ActionEvent e) throws SQLException {
			ResetAllLabels();
			buttoneresest();
			course = course6.getText();
			course6.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse7(ActionEvent e) throws SQLException {
			ResetAllLabels();
			buttoneresest();
			course = course7.getText();
			course7.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			viewMarks(course);
		}
		
		public void returncourse8(ActionEvent e){
			ResetAllLabels();
			buttoneresest();
			course8.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = course8.getText();
			viewMarks(course);
			
		}
		
		public void viewMarks(String course) {
			
			float gtotal = 0, gobt = 0;
			
			String query = "SELECT * FROM marks LEFT JOIN grade ON marks.StudentID = grade.StudentID where marks.StudentID = '"+ UACID +"' and marks.CourseID = '"+ course +"'ORDER BY marks.Type";
			
			ResultSet rs;
			try {
				rs = con.createStatement().executeQuery(query);
				
				while(rs.next()) {
					
					if(rs.getString("type").equals("Mid Term")) {
						midtotal.setText(rs.getString("Total_Marks"));
						gtotal += Float.parseFloat(rs.getString("Total_Marks"));
						midobt.setText(rs.getString("Marks_Gained"));
						gobt += Float.parseFloat(rs.getString("Marks_Gained"));
						midweight.setText(rs.getString("Weight_assigned") + "%");
					}
									
					else if(rs.getString("type").equals("Assignment")) {
						assigntotal.setText(rs.getString("Total_Marks"));
						gtotal += Float.parseFloat(rs.getString("Total_Marks"));
						assignobt.setText(rs.getString("Marks_Gained"));
						gobt += Float.parseFloat(rs.getString("Marks_Gained"));
						assignweight.setText(rs.getString("Weight_assigned") + "%");				
					}
					
					else if(rs.getString("type").equals("Final")) {
						finaltotal.setText(rs.getString("Total_Marks"));
						gtotal += Float.parseFloat(rs.getString("Total_Marks"));
						finalobt.setText(rs.getString("Marks_Gained"));
						gobt += Float.parseFloat(rs.getString("Marks_Gained"));
						finalweight.setText(rs.getString("Weight_assigned") + "%");
					}
					
					else if(rs.getString("type").equals("Project")) {
						projecttotal.setText(rs.getString("Total_Marks"));
						gtotal += Float.parseFloat(rs.getString("Total_Marks"));
						projectobt.setText(rs.getString("Marks_Gained"));
						gobt += Float.parseFloat(rs.getString("Marks_Gained"));
						projectweight.setText(rs.getString("Weight_assigned") + "%");
					}
					
					else if(rs.getString("type").equals("Quiz")) {
						quiztotal.setText(rs.getString("Total_Marks"));
						gtotal += Float.parseFloat(rs.getString("Total_Marks"));
						quizobt.setText(rs.getString("Marks_Gained"));
						gobt += Float.parseFloat(rs.getString("Marks_Gained"));
						quizweight.setText(rs.getString("Weight_assigned") + "%");
					}	
					
					grandtotal.setText(Float.toString(gtotal));
					grandobt.setText(Float.toString(gobt));
					grandweight.setText("100%");
					if(!StringUtils.isNullOrEmpty(rs.getString("Grade"))) {
						grade.setText(rs.getString("Grade"));
					}
				}
			}
				catch (SQLException e1) {
				// TODO Auto-generated catch block
				error.setContentText(e1.getMessage());
				error.showAndWait();
				e1.printStackTrace();
			}
			
		}
		
		public void disableButton() {
			course1.setDisable(true);
			course1.setText("");
			
			course2.setDisable(true);
			course2.setText("");
	
			course3.setDisable(true);
			course3.setText("");
	
			course4.setDisable(true);
			course4.setText("");
			
			course5.setDisable(true);
			course5.setText("");
			
			course6.setDisable(true);
			course6.setText("");
			
			course7.setDisable(true);
			course7.setText("");
			
			course8.setDisable(true);
			course8.setText("");
		}
		public void buttoneresest()
		{
			
			course1.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
			course2.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
			course3.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
			course4.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
			course5.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
			course6.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
			course7.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
			course8.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		}
}


