package application;


import java.net.URL;
import java.sql.Connection;

import java.sql.SQLException;

import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class StudentController implements Initializable{

	Connection con = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DBConnector.getConnection();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
