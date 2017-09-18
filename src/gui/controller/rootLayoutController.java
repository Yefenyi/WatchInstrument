package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class rootLayoutController implements Initializable{

	 @FXML
	 private MenuItem configMenuItem;
	 
	 private ConfigurationLayoutController controller;
	 private Stage stage;
	 
	 @FXML 
	 Parent root;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
    private void handleOpenConfigAction(ActionEvent event) {
        System.out.println("open bible");
        
        stage = new Stage();
        //load up OTHER FXML document
        try {
			  FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("../gui/view/ConfigurationView.fxml"));
	           root=loader.load();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    //create a new scene with root and set the stage
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
        
        
       
    }

}
