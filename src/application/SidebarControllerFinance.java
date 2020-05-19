package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.FadeIn;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SidebarControllerFinance implements Initializable {

    @FXML
    private BorderPane ap;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;
    
    
    @FXML
    private Button btn6;
    
    Alert error = new Alert(AlertType.ERROR);
    
    public void StudentAdd(ActionEvent e)
    { LoadPane("FeeCreation");  }
    
    public void CourseAdd(ActionEvent e)
    { LoadPane("FeeAllocation");}
        
    
    public void AdminSignOut(ActionEvent e)
    {
    	 Pane view2 = null;
 		try {
 			view2 = FXMLLoader.load(getClass().getResource("Login.fxml"));
 		} catch (IOException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
           Scene scene2 = new Scene(view2);
          Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
          window.setScene(scene2);
          window.centerOnScreen();
          new FadeIn(view2).play();
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
    	
    	ap.setCenter(root);
    	
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		LoadPane("FeeCreation");

	}
    
    
    

}
