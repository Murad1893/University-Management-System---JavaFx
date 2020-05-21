package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
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

public class TeacherSidebarController implements Initializable {
	
		Connection con = null;
		Alert error = new Alert(AlertType.ERROR);
		String UACID = null;
	
	  	@FXML
	    private Button tbtn1;

	    @FXML
	    private Button tbtn2;

	    @FXML
	    private Button tbtn3;

	    @FXML
	    private Button tbtn4;
	    
	    @FXML
	    private Button tbtn7;

	    @FXML
	    private Button helpbtn;
	    
	    @FXML
	    private BorderPane pa;
	    
	    @FXML
		private ImageView staffimage;
	    
	    @FXML
		private Label fullname;
	    
	    public void LoadHelpGuide(ActionEvent e) {
	    	try {
	           Desktop.getDesktop().browse(new URL("http://localhost/help/teacher.html").toURI());
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        } catch (URISyntaxException e1) {
	            e1.printStackTrace();
	        }
	    }
	    
	    public void TeachShowProfile(ActionEvent e)
	    { LoadPane("teacherdashboard");}
	    
	    public void TeachAddAttendence(ActionEvent e)
	    {LoadPane("attendance");}
	    
	    public void TeachGrading(ActionEvent e)
	    { LoadPane("Marks");}
	    
	    public void TeachAnnounce(ActionEvent e)
	    { LoadPane("Announcement");}
	    
	    
	    
	    public void TeachSignOut(ActionEvent e)
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
	           window.show();
	           new FadeIn(view3).play();
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
		
		
		String query = "select fname, mname, lname, Image from `academic_staff` where `StaffID` LIKE '" + UACID + "'";	
		
		try {
		
			ResultSet rs = con.createStatement().executeQuery(query);
		
			if(!rs.isBeforeFirst()) {
				throw new SQLException();			
			}
			rs.next();
			InputStream is = rs.getBinaryStream("Image");
			
			OutputStream os = new FileOutputStream(new File("staffphoto.jpg"));
			byte[] content = new byte[1024];
			
			int size = 0;

			if(is != null) {
			while((size = is.read(content)) != -1) {		
				os.write(content,0,size);
				
			}
			os.close();
			is.close();
			}

			Image image = new Image("file:staffphoto.jpg",145,193,false,false);
			staffimage.setImage(image);
			
			if(!StringUtils.isNullOrEmpty(rs.getString("mname")))
					fullname.setText(rs.getString("fname")+ " " + rs.getString("mname") + " " + rs.getString("lname"));
			else 
					fullname.setText(rs.getString("fname")+ " " + rs.getString("lname"));
			
			LoadPane("teacherdashboard");
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}