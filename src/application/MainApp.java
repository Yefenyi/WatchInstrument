package application;

import java.io.IOException;

import Parser.SoundParser;
import Server.ServerManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gui.view.mainLayoutController;

public class MainApp extends Application {
	    private Stage primaryStage;
	    private BorderPane rootLayout;
	    private ServerManager servermanager;
	    private mainLayoutController controller;
	    
	    public ServerManager getServerManager(){
	    	return this.servermanager;
	    }

	    public mainLayoutController getController(){
	    	return controller;
	    }
	    
	    @Override
	    public void start(Stage primaryStage) {
		    
	        try {
	        	new SoundParser();
				this.servermanager = new ServerManager();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	        
	        this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("VirtualInstrument");

	        initRootLayout();
	        showServerOverview();
		    controller.setMainApp(this);
		    servermanager.setController(this.controller);
	       
	    }

	    /**
	     * Initializes the root layout.
	     */
	    public void initRootLayout() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("../gui/view/RootLayout.fxml"));
	            rootLayout = (BorderPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Shows the person overview inside the root layout.
	     */
	    public void showServerOverview() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("../gui/view/mainLayout.fxml"));
	            AnchorPane personOverview = (AnchorPane) loader.load();

	            controller = loader.getController();
	            // Set person overview into the center of root layout.
	            rootLayout.setCenter(personOverview);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Returns the main stage.
	     * @return
	     */
	    public Stage getPrimaryStage() {
	        return primaryStage;
	    }
	    
	    public void startSyncing(int bpm){
	    	this.servermanager.StartSyncing(bpm);
	    }
	    
	    public void stopSyncing(){
	    	this.servermanager.StopSyncing();
	    }

	public static void main(String[] args) {
		launch(args);
		
	}
}
