package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class viewannouncementcontroller implements Initializable{

	Connection con = null;
	String UACID=null;
	Button[] btns = new Button[10];
	Label[] headings = new Label[10];
	Label[] date = new Label[10];
	TextArea[] texts = new TextArea[10];
	Alert error = new Alert(AlertType.ERROR);
	int i=7;
	int j = 0;
	String course = null;

	//Button

	@FXML
	private Button b1;
	@FXML
	private Button b2;
	@FXML
	private Button b3;
	@FXML
	private Button b4;
	@FXML
	private Button b5;
	@FXML
	private Button b6;
	@FXML
	private Button b7;
	@FXML
	private Button b8;
	@FXML
	private Label announceheading1;
	@FXML
	private Label announcedate1;
	@FXML
	private TextArea announcetext1;
	@FXML
	private Label announceheading2;
	@FXML
	private Label announcedate2;
	@FXML
	private TextArea announcetext2;
	@FXML
	private Label announceheading3;
	@FXML
	private Label announcedate3;
	@FXML
	private TextArea announcetext3;
	@FXML
	private Label announceheading4;
	@FXML
	private Label announcedate4;
	@FXML
	private TextArea announcetext4;
	@FXML
	private Label announceheading5;
	@FXML
	private Label announcedate5;
	@FXML
	private TextArea announcetext5;
	@FXML
	private Label announceheading6;
	@FXML
	private Label announcedate6;
	@FXML
	private TextArea announcetext6;
	@FXML
	private Label announceheading7;
	@FXML
	private Label announcedate7;
	@FXML
	private TextArea announcetext7;
	@FXML
	private Label announceheading8;
	@FXML
	private Label announcedate8;
	@FXML
	private TextArea announcetext8;
	@FXML
	private Label announceheading9;
	@FXML
	private Label announcedate9;
	@FXML
	private TextArea announcetext9;
	@FXML
	private Label announceheading10;
	@FXML
	private Label announcedate10;
	@FXML
	private TextArea announcetext10;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
			con = DBConnector.getConnection();
			
			btns[0] = b1;
			btns[1] = b2;
			btns[2] = b3;
			btns[3] = b4;
			btns[4] = b5;
			btns[5] = b6;
			btns[6] = b7;
			btns[7] = b8;
			
			headings[0] = announceheading1; date[0] = announcedate1; texts[0] = announcetext1;
			headings[1] = announceheading2; date[1] = announcedate2; texts[1] = announcetext2;
			headings[2] = announceheading3; date[2] = announcedate3; texts[2] = announcetext3;
			headings[3] = announceheading4; date[3] = announcedate4; texts[3] = announcetext4;
			headings[4] = announceheading5; date[4] = announcedate5; texts[4] = announcetext5;
			headings[5] = announceheading6; date[5] = announcedate6; texts[5] = announcetext6;
			headings[6] = announceheading7; date[6] = announcedate7; texts[6] = announcetext7;
			headings[7] = announceheading8; date[7] = announcedate8; texts[7] = announcetext8;
			headings[8] = announceheading9; date[8] = announcedate9; texts[8] = announcetext9;
			headings[9] = announceheading10; date[9] = announcedate10; texts[9] = announcetext10;
		
			
			disableButton();
			run();
			getCourse();
	
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
			
			
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void getCourse() {
		disableButton();
		buttoneresest();
		i = 7;

		String query = "Select CourseID from student_course where StudentID = '" + UACID + "'";
		
		try {
		
			ResultSet rs = con.createStatement().executeQuery(query);
			
			
			while(rs.next()) {
	
				btns[i].setDisable(false);
				
				btns[i].setText(rs.getString("CourseID") );
				
				i--;
			}
			
			i = 7;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void buttoneresest()
	{
		
		b1.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b2.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b3.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b4.setStyle("-fx-background-color: #3f51b5;-fx-text-fill: white;");
		b5.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		b6.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		b7.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		b8.setStyle("-fx-background-color:  #3f51b5;-fx-text-fill: white;");
		announceheading1.setText("");
		announcedate1.setText("");
		announcetext1.setText("");
		announceheading2.setText("");
		announcedate2.setText("");
		announcetext2.setText("");
		announceheading3.setText("");
		announcedate3.setText("");
		announcetext3.setText("");
		announceheading4.setText("");
		announcedate4.setText("");
		announcetext4.setText("");
		announceheading5.setText("");
		announcedate5.setText("");
		announcetext5.setText("");
		announceheading6.setText("");
		announcedate6.setText("");
		announcetext6.setText("");
		announceheading7.setText("");
		announcedate7.setText("");
		announcetext7.setText("");
		announceheading8.setText("");
		announcedate8.setText("");
		announcetext8.setText("");
		announceheading9.setText("");
		announcedate9.setText("");
		announcetext9.setText("");
		announceheading10.setText("");
		announcedate10.setText("");
		announcetext10.setText("");
	}
	
	public void returnSection1(ActionEvent e) {
		
		buttoneresest();
		
	
		b1.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course= b1.getText();
		ViewAnnouncement();
	
	}
	
	public void returnSection2(ActionEvent e) {
		
		buttoneresest();
		
		b2.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b2.getText();
		ViewAnnouncement();

	}
	
	public void returnSection3(ActionEvent e) {
		
		buttoneresest();
	
		b3.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b3.getText();
		ViewAnnouncement();
	
	}
	
	
	public void returnSection4(ActionEvent e) {
	
		buttoneresest();
		
		b4.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b4.getText();
		ViewAnnouncement();
	
	}
	
	public void returnSection5(ActionEvent e) {
		
		buttoneresest();
	
		b5.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b5.getText();
		ViewAnnouncement();
	
	}
	
	public void returnSection6(ActionEvent e) {
		
		buttoneresest();
		
		b6.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b6.getText();
		ViewAnnouncement();
	
	}
	
	public void returnSection7(ActionEvent e) {
		
		buttoneresest();
	
		b7.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b7.getText();
		ViewAnnouncement();
	
	}
	
	public void returnSection8(ActionEvent e) {
		
		buttoneresest();
	
		b8.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
		course = b8.getText();
		ViewAnnouncement();
	
	}
	
	
	public void ViewAnnouncement() {
		String query = "select * from announcement where (staffID, CourseID) IN (select stc.StaffID,sc.CourseID from student s, student_course sc, staff_course stc where s.StdID = sc.StudentID and s.Sec_ID = stc.SectionID and sc.CourseID = stc.CourseID and s.StdID = '"+ UACID +"' and sc.CourseID = '"+ course +"') order by DateAdded DESC";
		j = 0;
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery(query);
			while(rs.next() && j <= 9) {
				headings[j].setText(rs.getString("AnnTitle"));
				date[j].setText(rs.getString("DateAdded"));
				texts[j].setText(rs.getString("Announcement"));
				
				j++;
			}
			j = 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void disableButton() {
		b1.setDisable(true);
		b1.setText("");
		
		b2.setDisable(true);
		b2.setText("");
		
		b3.setDisable(true);
		b3.setText("");
		
		b4.setDisable(true);
		b4.setText("");
		
		b5.setDisable(true);
		b5.setText("");
		
		b6.setDisable(true);
		b6.setText("");
		
		b7.setDisable(true);
		b7.setText("");
		
		b8.setDisable(true);
		b8.setText("");
	}
	
}
