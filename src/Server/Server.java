package Server;

import Info.ServerInfo;
import Info.SoundInfo;
import Parser.SoundParser;
import Util.ModelMap;
import Util.Preprocessing;
import Util.DataCollector.DataCollector;
import Util.DataCollector.Simple3AxisCollector;
import Util.Model.Model;
import Util.Sound.btAudioPlayer;
import gui.view.mainLayoutController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.bluetooth.*;
import javax.microedition.io.*;


public class Server implements Runnable{
	private static int DEFAULT_PLAYER_NUMBER = 5;
	private mainLayoutController controller;
    private String name;
    final private int id;
    private UUID uuid;      
    private ArrayList<btAudioPlayer> playerlist= new ArrayList<btAudioPlayer>();
    private Model model;
    private boolean connected = false;
    private int interrupt = 0;
    
    private StreamConnection connection;
    OutputStream outStream;
    BufferedWriter bWriter;
    PrintWriter pWriter;
    private double panning;
    private double volume;
    private StreamConnectionNotifier streamConnNotifier;
    private Map<Integer,btAudioPlayer> outputMap = new HashMap<Integer,btAudioPlayer>();
    private DataCollector sac;
    
    public int getId(){
    	return this.id;
    }
    
    public String getName(){
    	return this.name;
    }
    
    public double getPanning(){
    	if (playerlist.size()>0){
    	    return playerlist.get(0).getPanning();
    	}
    	return 0;
    }
    
    public double getVolume(){
    	if (playerlist.size()>0){
    	    return playerlist.get(0).getVolume();
    	}
    	return 0;
    }
    
    public void setPanning(double panning){
    	int length = playerlist.size();
    	for(int i =0;i<length;i++){
    		  playerlist.get(i).setPanning(panning); 		
    	}
    }
    
    public void setVolume(double volume){
    	int length = playerlist.size();
    	for(int i =0;i<length;i++){
    		  playerlist.get(i).setVolume(volume); 		
    	}
    }
    
    public void setController(mainLayoutController controller){
        this.controller = controller;	
    }
    
	public Server(ServerInfo serverinfo,int id,int interrupt) throws IOException{
		this.controller = controller;
		this.interrupt = interrupt;
		this.id = id;
		this.name = serverinfo.getName();
		this.uuid = new UUID(serverinfo.getUUID(), true);
		this.panning = panning;
		this.model =  ModelMap.getModelByName(serverinfo.getModel());
		ArrayList<SoundInfo> playlist = serverinfo.getSoundSource();
		for(int index = 0; index<playlist.size();index++){
			String path = SoundParser.getLocation(playlist.get(index).getName());
		    this.playerlist.add(new btAudioPlayer(path,this.interrupt));
		}
		
		outputMap.put(model.getOutPutArray().get(0), null);

		for(int index = 1;index<model.getOutPutArray().size();index++){
			outputMap.put(model.getOutPutArray().get(index),playerlist.get(index-1));
		}		
		
		LocalDevice localDevice = LocalDevice.getLocalDevice();
		System.out.println("\nAddress: "+localDevice.getBluetoothAddress());
		System.out.println("Name: "+localDevice.getFriendlyName());
		String connectionString = "btspp://localhost:" + this.uuid +";name=" + this.name;
		System.out.println(connectionString);
		
		this.streamConnNotifier = (StreamConnectionNotifier)Connector.open( connectionString );		
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public void sendMsg(String msg){
		new Thread(){
		public void run(){
			pWriter.write(msg);
		    pWriter.flush();
		}
		}.start();
	}
	
	void initServer(){
		
		
		System.out.println("\n" + this.name + " started. Waiting for clients to connect¡­");
		try {
			this.connection = streamConnNotifier.acceptAndOpen();

			outStream=connection.openOutputStream();
			pWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outStream)));
			
			connected = true;
			RemoteDevice dev;
			dev = RemoteDevice.getRemoteDevice(this.connection);
	
			sac = new Simple3AxisCollector(this.name,this.connection);
			System.out.println(this.name + ": Connected");
			controller.setConnected(this.id);
			//OutputStream outStream = connection.openOutputStream();
			//PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
		} catch (Exception e) {
			
			//e.printStackTrace();
		}		
		
	}
	
	@Override
	public void run() {        
		try {
			 initServer();
			 try {
		            while (true) {	 
		            	    double[] newdataarray = sac.listen();		            	    
		            		if (newdataarray!=null){
		            	        //System.out.println(newdataarray[0]+","+newdataarray[1]+","+newdataarray[2]);
		            	        ArrayList<Double> newdata = new ArrayList<Double>();
		            	        for (double data:newdataarray){
		            	            newdata.add(data);
		            	        }
			            		int output = this.model.update(newdata);
			            		
			            		//System.out.println(output);
			            	    btAudioPlayer player = outputMap.get(output);
			            		if(player!=null){	
			            			for(int i =0;i<playerlist.size();i++){
				            			playerlist.get(i).stop();
				            		}
			            			player.play();
			            		}
		            		}
		            	}

		} catch(NullPointerException npe){
			connected = false;
			controller.setDisconnected(this.id);
			//npe.printStackTrace();
			run();			

	}
   }catch(Exception e){
	    //e.printStackTrace();	
	}

}
}
