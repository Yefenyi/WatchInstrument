package Server;

import Info.ServerInfo;
import Info.SoundInfo;
import Parser.SoundParser;
import Util.ModelMap;
import Util.DataCollector.DataCollector;
import Util.DataCollector.Simple3AxisCollector;
import Util.Model.Model;
import Util.Sound.btAudioPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.bluetooth.*;
import javax.microedition.io.*;


public class Server implements Runnable{
	private static int DEFAULT_PLAYER_NUMBER = 5;
    private String name;
    private UUID uuid;  
    private ArrayList<btAudioPlayer> playerlist= new ArrayList<btAudioPlayer>();
    private Model model;
    private StreamConnectionNotifier streamConnNotifier;
    private Map<Integer,btAudioPlayer> outputMap = new HashMap<Integer,btAudioPlayer>();
    
	public Server(ServerInfo serverinfo) throws IOException{
		this.name = serverinfo.getName();
		this.uuid = new UUID(serverinfo.getUUID(), true);
		this.model =  ModelMap.getModelByName(serverinfo.getModel());
		ArrayList<SoundInfo> playlist = serverinfo.getSoundSource();
		for(int index = 0; index<playlist.size();index++){
			String path = SoundParser.getLocation(playlist.get(index).getName());
		    this.playerlist.add(new btAudioPlayer(path,DEFAULT_PLAYER_NUMBER));
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
	
	@Override
	public void run() {        
		try {
			System.out.println("\n" + this.name + " started. Waiting for clients to connect¡­");
			StreamConnection connection = streamConnNotifier.acceptAndOpen();		
			RemoteDevice dev;
			dev = RemoteDevice.getRemoteDevice(connection);
			//System.out.println(this.name + ": Remote device address: "+dev.getBluetoothAddress());
			//System.out.println(this.name + ": Remote device name: "+dev.getFriendlyName(true));
			System.out.println(this.name + ": Connected");
						
			OutputStream outStream = connection.openOutputStream();
			PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
			
			 try {
				    DataCollector sac = new Simple3AxisCollector(this.name,connection);
		            while (true) {	             	
		            	    ArrayList<Double> newdata = sac.listen();
		            		if (newdata!=null){
			            		int output = this.model.update(newdata);
			            	    btAudioPlayer player = outputMap.get(output);
			            		if(player!=null){	
			            			player.play();
			            		}
		            		}
		            	}

		} catch(NullPointerException npe){
			run();			

	}
   }catch(Exception e){
	    e.printStackTrace();	
	}

}
}
