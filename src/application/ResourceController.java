package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;


public class ResourceController implements Initializable {
	
	Connection con = null;
	String UACID=null;
	String section = null;
	Button[] btns = new Button[10];
	Alert error = new Alert(AlertType.ERROR);
	int i=7;
	int j = 0;
	String course = null;
	String Staff = null;

	
	FileChooser filechooser = new FileChooser();
	FileInputStream fs;
	File file;
	
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
	    private TableView<Assignment> table;

	    @FXML
	    private TableColumn<Assignment, String> col_resource;

	    @FXML
	    private TextArea ta;
	    
	    @FXML
	    private Button download;

	    @FXML
	    private Label namelabel;

	    ObservableList<Assignment> names = FXCollections.observableArrayList();
	    
		public void initialize(URL location, ResourceBundle resources) {
				
				try {
					BufferedReader fd = new BufferedReader(new FileReader("UAC.txt"));
					UACID = fd.readLine();
					fd.close();
					
					con = DBConnector.getConnection();
					
					btns[0] = b1;
					btns[1] = b2;
					btns[2] = b3;
					btns[3] = b4;
					btns[4] = b5;
					btns[5] = b6;
					btns[6] = b7;
					btns[7] = b8;
					
					disableButton();
					getCourse();		
					table.setOnMousePressed(new EventHandler<MouseEvent>(){

						@Override
						public void handle(MouseEvent event){
							// TODO Auto-generated method stub
							try {
								if(event.getClickCount() == 1) {
									namelabel.setText(table.getSelectionModel().getSelectedItem().getName());
									
									String num = "";
									int i = 0;
									
								
									while(!(namelabel.getText().charAt(i) + "").equals(".") && i < namelabel.getText().length()) {
										
										num = num + namelabel.getText().charAt(i);
										i++;
									}
										
									String query = "select Resource_Description from resourcespace where StaffID = '" + Staff + "' and CourseID = '" + course + "' and Type = 'Resource' and Resource_num = '"+ num +"'";
									
									try {
										
										ResultSet rs = con.createStatement().executeQuery(query);
										
										if(rs.isBeforeFirst()) {
											rs.next();
										
											ta.setText(rs.getString("Resource_Description"));	
										}
										
										
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										error.setContentText(e1.getMessage());
										error.showAndWait();

									}
									
								}
							}
							catch(NullPointerException e) {
								error.setContentText("No valid value in table!");
								error.showAndWait();
							}
							download.setText("Download Files");
						}
						
					});
				}
				catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				download.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						
						String num = "";
						int i = 0;
						
					
						while(!(namelabel.getText().charAt(i) + "").equals(".") && i < namelabel.getText().length()) {
							
							num = num + namelabel.getText().charAt(i);
							i++;
						}
						
						 String query = "select Files from resourcespace where StaffID = '" + Staff + "' and CourseID = '" + course + "' and Type = 'Resource' and Resource_num = '"+ num +"'";
						
						 ResultSet rs = null;
						 
						 try {
							rs = con.createStatement().executeQuery(query);
							
							if(rs.isBeforeFirst()) {
								
								rs.next();
								
								File save  = filechooser.showSaveDialog(download.getScene().getWindow());
								
								if(save != null) {
									
									String filename = null;
									
									if(save.getPath().indexOf(".") != -1)
									filename = save.getPath().substring(0, save.getPath().indexOf("."));
									
									else
									filename= save.getPath();
										
									save = new File(filename + ".zip"); 
									
									InputStream is = rs.getBinaryStream("Files");
									OutputStream os = new FileOutputStream(save);
									byte[] content = new byte[1024];
									
									int size = 0;
									while((size = is.read(content)) != -1) {
										os.write(content,0,size);
									}
									
									os.close();
									is.close();
									
								}
								
							}
							 
							else {
								download.setText("No File Uploaded");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
					}
				});
				
		}
		
		public void getCourse() {
		
			disableButton();
			buttoneresest();
			i = 7;
			String query = "Select CourseID from student_course where StudentID = '" + UACID + "'";
			ResultSet rs = null;
			
			try {
				
				rs = con.createStatement().executeQuery(query);
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
		}
		
		public void returnSection1(ActionEvent e) {
	
			buttoneresest();
			
			b1.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course= b1.getText();
			ViewAssignment();
		
		}
		
		public void returnSection2(ActionEvent e) {
	
			buttoneresest();
			
			b2.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b2.getText();
			ViewAssignment();

		}
		
		public void returnSection3(ActionEvent e) {
	
			buttoneresest();
		
			b3.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b3.getText();
			ViewAssignment();
		
		}
		
		
		public void returnSection4(ActionEvent e) {

			buttoneresest();
			
			b4.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b4.getText();
			ViewAssignment();
		
		}
		
		public void returnSection5(ActionEvent e) {

			buttoneresest();
		
			b5.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b5.getText();
			ViewAssignment();
		
		}
		
		public void returnSection6(ActionEvent e) {
	
			buttoneresest();
			
			b6.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b6.getText();
			ViewAssignment();
		
		}
		
		public void returnSection7(ActionEvent e) {
			
	
			buttoneresest();
		
			b7.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b7.getText();
			ViewAssignment();
		
		}
		
		public void returnSection8(ActionEvent e) {
	
			buttoneresest();
		
			b8.setStyle("-fx-background-color: #f6b93b;-fx-text-fill: black;");
			course = b8.getText();
			ViewAssignment();
		
		}
		
		public void ViewAssignment() {
			
			if(table.getItems() != null) {
				table.getItems().clear();
			}
			
			String query = "select Resource_num, Resource_title, StaffID from resourcespace where StaffID = (SELECT stc.StaffID from student s, staff_course stc where s.StdID = '"+ UACID +"' and s.Sec_ID = stc.SectionID and stc.CourseID = '"+ course +"') and CourseID = '"+ course +"' and Type = 'Resource' order by Resource_num";
			
			try {	
				ResultSet rs = con.createStatement().executeQuery(query);
				while(rs.next()) {
					names.add(new Assignment(rs.getString("Resource_num") + ". " + rs.getString("Resource_title")));
					Staff = rs.getString("StaffID");
				}
				
				col_resource.setCellValueFactory(new PropertyValueFactory<>("name"));
				
				table.setItems(names);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				error.setContentText(e1.getMessage());
				error.showAndWait();
				e1.printStackTrace();
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
		
            namelabel.setText("");
		}
		
}