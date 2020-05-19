package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SuperAdminController implements Initializable {
	
	  @FXML
	    private Button tbtn1;

	    @FXML
	    private Button tbtn2;

	   
	    
	    @FXML
	    private Button tbtn7;

	   
	    @FXML
	    private BorderPane pa;
	    
	    Alert error = new Alert(AlertType.ERROR);
	    
	    public void SuperAddDept(ActionEvent e)
	    { LoadPane("Department");}
	    
	    public void SuperAddTeach(ActionEvent e)
	    {LoadPane("AdminStaff");}
	    
	    public void SuperSignOut(ActionEvent e)
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
		
		LoadPane("Department");
	}

}