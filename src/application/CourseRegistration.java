package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CourseRegistration {
	String course, credit, status;
	Button action;
	boolean disable;
	
	public CourseRegistration(String course, String credit, String status, Button action, boolean disable) {
		super();
		this.course = course;
		this.credit = credit;
		this.status = status;
		this.action = action;
		this.disable = disable;
		
		action.setDisable(disable);
		
		action.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub	
					String UACID;
					try {
						BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
						UACID = fd.readLine();
						fd.close();
						String query = "INSERT INTO `student_course` (`StudentID`, `CourseID`) VALUES (?, ?)";
						
						PreparedStatement ps = null;
						
						ps = DBConnector.getConnection().prepareStatement(query);
						
						ps.setString(1, UACID);
						ps.setString(2, course.substring(0,4));
						
						ps.execute();
						
						action.setDisable(true);
						
					} catch (IOException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
		});
		
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Button getAction() {
		return action;
	}
	public void setAction(Button action) {
		this.action = action;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	
	
	
}
