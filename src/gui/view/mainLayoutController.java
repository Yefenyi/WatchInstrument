package gui.view;

import java.util.ArrayList;
import java.util.Arrays;

import Server.Server;
import application.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;

public class mainLayoutController 
{	
	    @FXML
	    private Label name1;
	    @FXML
	    private Label name2;
	    @FXML
	    private Label name3;
	    @FXML
	    private Label name4;
	    @FXML
	    private Label name5;
	    
	    @FXML
	    private Slider panning1;
	    @FXML
	    private Slider panning2;
	    @FXML
	    private Slider panning3;
	    @FXML
	    private Slider panning4;
	    @FXML
	    private Slider panning5;

	    @FXML
	    private Slider volume1;
	    @FXML
	    private Slider volume2;
	    @FXML
	    private Slider volume3;
	    @FXML
	    private Slider volume4;
	    @FXML
	    private Slider volume5;
	    
	    
	    @FXML
	    private Circle signal1;
	    @FXML
	    private Circle signal2;
	    @FXML
	    private Circle signal3;
	    @FXML
	    private Circle signal4;
	    @FXML
	    private Circle signal5;
	    
	    @FXML
	    private GridPane layout;
	    
	    @FXML
	    private Slider bpmslider;
	    @FXML
	    private Label bpm;
	    
	    @FXML
	    private Button sync;


	    private MainApp mainApp;
	    
	    private ArrayList<Label> names;
	    private ArrayList<Slider> pannings;
	    private ArrayList<Slider> volumes;
	    private ArrayList<Circle> signals;
	    private int bpmvalue;
	    
	    boolean start = false;
	    
	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public mainLayoutController() {
	    }
	    
	    public double getPanning(int index){
	    	return pannings.get(index).valueProperty().doubleValue();
	    }
	    
	    public void setPanning(int index, double value){
	    	pannings.get(index).valueProperty().set(value);
	    }
	    
	    public double getVolume(int index){
	    	return volumes.get(index).valueProperty().doubleValue();
	    }
	    
	    public void setVolume(int index, double value){
	    	volumes.get(index).valueProperty().set(value);
	    }
	    
	    public String getName(int index){
	    	return names.get(index).getText();
	    }
	    
	    public void setName(int index, String value){
	    	names.get(index).setText(value);
	    }
	    
	    public void setConnected(int index){
	    	signals.get(index).setFill(Color.LIGHTGREEN);
	    }
	    
	    public void setDisconnected(int index){
	    	signals.get(index).setFill(Color.RED);
	    }
	    
	    
	    public void initialize() {
	    	names = new ArrayList<>(Arrays.asList(name1, name2, name3, name4, name5));
	    	pannings = new ArrayList<>(Arrays.asList(panning1, panning2, panning3, panning4, panning5));
	    	volumes = new ArrayList<>(Arrays.asList(volume1, volume2, volume3, volume4, volume5));
	    	signals = new ArrayList<>(Arrays.asList(signal1, signal2, signal3, signal4, signal5));
	    	start = false;
	    	sync.setDisable(true);
	    	ObservableList<Node> childrens=layout.getChildren();	
	    	bpmvalue = bpmslider.valueProperty().intValue();
	    	bpm.setText(""+this.bpmvalue);
	        bpmslider.valueProperty().addListener((observable, oldValue, newValue) -> { 
      	        this.bpmvalue = newValue.intValue();
	        	bpm.setText(""+this.bpmvalue);
      	});
	    }
	    
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	        int length = mainApp.getServerManager().getServerCount();	
	        for (int index = 0;index<5; index++){
	        	setPanning(index, 0);
	        	setVolume(index, 5);
	        }
	        
	    	for(int index= 0;index<5;index++){
	    		Server server=mainApp.getServerManager().getServer(index);
	            pannings.get(index).valueProperty().addListener((observable, oldValue, newValue) -> { 
	        	      server.setPanning(newValue.doubleValue());
	        	});
	            
	            volumes.get(index).valueProperty().addListener((observable, oldValue, newValue) -> { 
	        	      server.setVolume(newValue.doubleValue());
	        	});
	        };
	                	
	    	this.sync.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override public void handle(ActionEvent e) {
	    	    	if(start == false){
	    	    		mainApp.startSyncing(bpmvalue);
	    	    		start = true;
	    	    	}
	    	    	
	    	    	else if(start == true){
	    	    		mainApp.stopSyncing();
	    	    		start = false;
	    	    	}
	    	    }
	    	});
	    	
	    	this.sync.setDisable(false);
	    }


	}
