package application;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.util.StringUtils;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentSideBarController implements Initializable {
	
		Connection con = null;
		Alert error = new Alert(AlertType.ERROR);
		String UACID = null;
		
	  	@FXML
	    private Button sbtn1;

	    @FXML
	    private Button sbtn2;

	    @FXML
	    private Button sbtn3;

	    @FXML
	    private Button sbtn4;

	    @FXML
	    private Button sbtn5;

	    @FXML
	    private Button sbtn7;

	    @FXML
	    private Button sbtn6;
	    
	    @FXML
	    private Button sbtn8;
	    
	    
	    @FXML
	    private BorderPane pa;
	    
	    @FXML
		private Label fullname;
	    
	    @FXML
		private ImageView studentimage;
	     
	    public void ShowProfile(ActionEvent e)
	    { LoadPane("studentdashboard");}
	    
	    public void ShowCourse(ActionEvent e)
	    {LoadPane("StudentCourse");}
	    
	    public void ShowAnnouncement(ActionEvent e)
	    { LoadPane("viewannouncement");}
	    
	    public void ShowAssignment(ActionEvent e)
	    { LoadPane("Assignment");}
	    
	    public void ShowAttendence(ActionEvent e)
	    {LoadPane("StudentViewAttendence");}
	    
	    public void ShowResource(ActionEvent e)
	    {LoadPane("Resource");}
	    
	    public void AddMarks(ActionEvent e)
	    {LoadPane("StudentMarkView");}
	    
	    public void StudentSignOut(ActionEvent e)
	    {
	    	 Pane view3 = null;
	  		try {
	  			view3 = FXMLLoader.load(getClass().getResource("Login.fxml"));
	  		} catch (IOException e1) {
	  			// TODO Auto-generated catch block
	  			e1.printStackTrace();
	  		}
	            Scene scene3 = new Scene(view3);
	           Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
	           window.setScene(scene3);
	           window.centerOnScreen();
	           new FadeIn(view3).play();
	           window.show();
	           
	    }
	    
	    public void LoadPane(String ui)
	    {
	    	Parent root = null;
	    	try {
				root=FXMLLoader.load(getClass().getResource(ui + ".fxml"));
				new FadeIn(root).play();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	    	
	    	pa.setCenter(root);
	    	
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		try {
		
			con = DBConnector.getConnection();
			BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
			UACID = fd.readLine();
			fd.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String query = "select fname, mname, lname, Image from `student` where `StdID` LIKE '" + UACID + "'";	
		
		try {
		
			ResultSet rs = con.createStatement().executeQuery(query);
		
			if(!rs.isBeforeFirst()) {
				throw new SQLException();			
			}
			rs.next();
			InputStream is = rs.getBinaryStream("Image");
			
			OutputStream os = new FileOutputStream(new File("studentphoto.jpg"));
			byte[] content = new byte[1024];
			
			int size = 0;

			if(is != null) {
			while((size = is.read(content)) != -1) {		
				os.write(content,0,size);
				
			}
			os.close();
			is.close();
			}

			Image image = new Image("file:studentphoto.jpg",145,193,false,false);
			studentimage.setImage(image);
			
			if(!StringUtils.isNullOrEmpty(rs.getString("mname")))
					fullname.setText(rs.getString("fname")+ " " + rs.getString("mname") + " " + rs.getString("lname"));
			else 
					fullname.setText(rs.getString("fname")+ " " + rs.getString("lname"));
			
			LoadPane("studentdashboard");
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}