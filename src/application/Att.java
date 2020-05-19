package application;

import javafx.scene.control.ComboBox;

public class Att {

	String studentid;
	ComboBox<String> presence;
	
	public Att(String studentid, ComboBox<String> presence) {
		super();
		this.studentid = studentid;
		this.presence = presence;
	}
	public String getStudentid() {
		return studentid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public ComboBox<String> getPresence() {
		return presence;
	}
	public void setPresence(ComboBox<String> presence) {
		this.presence = presence;
	}
	
	
}
