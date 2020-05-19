package application;

import java.io.FileWriter; 
import java.net.URL;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;

import com.mysql.cj.util.StringUtils;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	Connection con = null;
	Alert error = new Alert(AlertType.ERROR);
	
	//Button
	@FXML
	private Button SigIn;
	
	//TextField
	@FXML
	private TextField Username;
	@FXML
	private TextField Password;
	@FXML
	private Label Usernamelabel;
	@FXML
	private Label Passwordlabel;
	@FXML
	private Label errorlabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			con = DBConnector.getConnection();
			SigIn.setOnKeyPressed(event -> {
				if (event.getCode().equals(KeyCode.ENTER)) {
		            SignInButton(event);
		        }
		    });
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void defaultlabel() 
	{
		
		Username.setStyle("-fx-border-color:  #ffffff ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
		Usernamelabel.setText("UserName");
		Usernamelabel.setTextFill(Color.web("#ffffff"));
		Password.setStyle("-fx-border-color:  #ffffff ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
		Passwordlabel.setText("Password");
		Passwordlabel.setTextFill(Color.web("#ffffff"));	
		errorlabel.setText("");
		errorlabel.setTextFill(Color.web("#ffffff"));
	
	}
		
	public void SignInButton(ActionEvent e) {
		defaultlabel();
		if(StringUtils.isNullOrEmpty(Username.getText()) && StringUtils.isNullOrEmpty(Password.getText()) )
		{

			Username.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Usernamelabel.setText("UserName*");
			Usernamelabel.setTextFill(Color.web("#d50000"));
			Password.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Passwordlabel.setText("Password*");
			Passwordlabel.setTextFill(Color.web("#d50000"));	
			errorlabel.setText("Enter Username And Password!");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		else if(StringUtils.isNullOrEmpty(Username.getText()))
		{
			Username.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Usernamelabel.setText("UserName*");
			Usernamelabel.setTextFill(Color.web("#d50000"));
			errorlabel.setText("Enter Username!");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		else if(StringUtils.isNullOrEmpty(Password.getText()))
		{
			Password.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Passwordlabel.setText("Password*");
			Passwordlabel.setTextFill(Color.web("#d50000"));
			errorlabel.setText("Enter Password!");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		else {
		
		String query = "SELECT * FROM `uac` WHERE `Username` = '" + Username.getText() + "'";
		String passcheck = null;
		
		try {
			ResultSet rs = con.createStatement().executeQuery(query);
			rs.next();
			
			if(!StringUtils.isNullOrEmpty(rs.getString("Username"))) {
				
				byte[] actualByte = Base64.getDecoder().decode(rs.getString("Password")); 
				
				passcheck = new String(actualByte);
				
				if(passcheck.equals(Password.getText())) {
					//System.out.println("Success!");				
					FileWriter fw = new FileWriter("UAC.txt",false);
					fw.write(rs.getString("Username"));
					fw.close();		
					if(Integer.parseInt(rs.getString("Previlege")) == 2)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("TeacherSidebar.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 1)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("Studentsidebar.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			           
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 3)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("Sidebaradmin.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 4)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("SuperAdminSidebar.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 5)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("SidebarFee.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
	                  
				}
				Password.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
				Passwordlabel.setText("Password*");
				Passwordlabel.setTextFill(Color.web("#d50000"));
				errorlabel.setText("Incorrect Password!");
				errorlabel.setTextFill(Color.web("#d50000"));
			}
 
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			Username.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Usernamelabel.setText("UserName*");
			Usernamelabel.setTextFill(Color.web("#d50000"));
			errorlabel.setText("User Not Found");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		}
		
	}
	
	public void SignInButton(KeyEvent e) {
		defaultlabel();
		if(StringUtils.isNullOrEmpty(Username.getText()) && StringUtils.isNullOrEmpty(Password.getText()) )
		{

			Username.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Usernamelabel.setText("UserName*");
			Usernamelabel.setTextFill(Color.web("#d50000"));
			Password.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Passwordlabel.setText("Password*");
			Passwordlabel.setTextFill(Color.web("#d50000"));	
			errorlabel.setText("Enter Username And Password!");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		else if(StringUtils.isNullOrEmpty(Username.getText()))
		{
			Username.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Usernamelabel.setText("UserName*");
			Usernamelabel.setTextFill(Color.web("#d50000"));
			errorlabel.setText("Enter Username!");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		else if(StringUtils.isNullOrEmpty(Password.getText()))
		{
			Password.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Passwordlabel.setText("Password*");
			Passwordlabel.setTextFill(Color.web("#d50000"));
			errorlabel.setText("Enter Password!");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		else {
		
		String query = "SELECT * FROM `uac` WHERE `Username` = '" + Username.getText() + "'";
		String passcheck = null;
		
		try {
			ResultSet rs = con.createStatement().executeQuery(query);
			rs.next();
			
			if(!StringUtils.isNullOrEmpty(rs.getString("Username"))) {
				
				byte[] actualByte = Base64.getDecoder().decode(rs.getString("Password")); 
				
				passcheck = new String(actualByte);
				
				if(passcheck.equals(Password.getText())) {
					//System.out.println("Success!");				
					FileWriter fw = new FileWriter("UAC.txt",false);
					fw.write(rs.getString("Username"));
					fw.close();		
					if(Integer.parseInt(rs.getString("Previlege")) == 2)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("TeacherSidebar.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 1)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("Studentsidebar.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			           
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 3)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("Sidebaradmin.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 4)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("SuperAdminSidebar.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
					else if(Integer.parseInt(rs.getString("Previlege")) == 5)
					{
						Pane view2 = FXMLLoader.load(getClass().getResource("SidebarFee.fxml"));
			            Scene scene2 = new Scene(view2);
			            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
			            window.setScene(scene2);
			            window.centerOnScreen();
			            new FadeIn(view2).play();
			            window.show();
			            
					}
	                  
				}
				Password.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
				Passwordlabel.setText("Password*");
				Passwordlabel.setTextFill(Color.web("#d50000"));
				errorlabel.setText("Incorrect Password!");
				errorlabel.setTextFill(Color.web("#d50000"));
			}
 
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			Username.setStyle("-fx-border-color:  #d50000 ; -fx-border-width: 1px ;-fx-border-radius: 10px;-fx-background-color: transparent;-fx-text-fill: white;");
			Usernamelabel.setText("UserName*");
			Usernamelabel.setTextFill(Color.web("#d50000"));
			errorlabel.setText("User Not Found");
			errorlabel.setTextFill(Color.web("#d50000"));
		}
		}
		
	}
	
	
		
}




