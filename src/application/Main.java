package application;

import java.io.IOException;

import animatefx.animation.FadeIn;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {
			@Override
			public void start(Stage primaryStage) throws IOException {
			    		
				try {
					Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		    		Scene scene = new Scene(root);
		    		primaryStage.setTitle("University Management System");
		    		primaryStage.setScene(scene);
		    		primaryStage.getIcons().add(new Image("file:42496-school-icon.png"));
		    		primaryStage.show();
		    		new FadeIn(root).play();
				}
				
				catch(Exception e) {
					e.printStackTrace();
				}
						
			}

			public static void main(String[] args) {
				launch(args);
			}
}