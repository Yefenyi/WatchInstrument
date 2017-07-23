package Server;

import Parser.XmlParser;

import java.io.IOException;
import java.util.ArrayList;
import Info.ServerInfo;
import Server.Server;
import application.MainApp;
import gui.view.mainLayoutController;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class ServerManager {
	
	 private ArrayList<Server> serverList;
	 private SyncThread syncthread;
	 private static int timeStamp  =0;
	 
	 
	 Sound one, two, three, four;
	 ArrayList<Sound> counting = new ArrayList<>();
	 
	 public Server getServer(int index){
		return serverList.get(index);
	 }
	
	 public int getServerCount(){
	    return serverList.size(); 	 
	 }
     
     public ServerManager() throws IOException{
    	 TinySound.init();
    	 
    	 one = TinySound.loadSound("raw/One.wav");
    	 two = TinySound.loadSound("raw/Two.wav");
    	 three = TinySound.loadSound("raw/Three.wav");
    	 four = TinySound.loadSound("raw/Four.wav");
    	 
    	 counting.add(one);
    	 counting.add(two);
    	 counting.add(three);
    	 counting.add(four);
    	 
		 XmlParser xmlparser =  new XmlParser();
		 ArrayList<ServerInfo> serverinfolist = xmlparser.getServerInfo();
		 syncthread = new SyncThread(60,serverList);
		 
		 int length = serverinfolist.size();
		 serverList = new ArrayList<Server>(length);
		 
		 for (int index=0; index < length; index++){
			 Server server = new Server(serverinfolist.get(index),index,serverinfolist.get(index).getInterrupt());
			 serverList.add(index,server);
		 }
		 
		 for (int index=0; index < length; index++){
			 Thread serverThread = new Thread(serverList.get(index));
			 serverThread.start();   		 
		 }
	 }
     
     public void StartSyncing(int bpm){
    	 this.syncthread = new SyncThread(bpm,serverList);
    	 new Thread(syncthread).start();
    	 for(Server server:serverList){
    		 server.setSyncing(true);
    	 }
     }     
     
     public void StopSyncing(){
    	 this.syncthread.stopSync();
    	 for(Server server:serverList){
    		 server.setSyncing(false);
    	 }
     }
     
     public void setController(mainLayoutController controller){
    	 for (int i= 0; i<serverList.size();i++){
    		    serverList.get(i).setController(controller);
    	 } 
     }
     
     public static int getTimeStamp(){
    	 return timeStamp;
     }
     
     
     class SyncThread implements Runnable{
    	 private volatile boolean stop_flag = false;
    	 private ArrayList<Server> serverList;
    	 private int bpm = 60; 
    	 
    	 public SyncThread(int bpm, ArrayList<Server> serverList){
    		 this.serverList = serverList;
    		 stop_flag = false;
    		 this.bpm = bpm;
    	 }
    	 
    	 public void stopSync(){
    		 stop();
    	 }
    	 
    	 public void run(){
    		 int phaseCount = 0;
			 timeStamp =0;
    		 while(!(this.stop_flag)){
    			 int interval = 60*1000/bpm;
                 
    			 for(int i =0; i<serverList.size();i++){
    				 if(serverList.get(i).isConnected()){
    					 serverList.get(i).sendMsg("Beep!\n");
    					 counting.get(timeStamp).play();
    					 //serverList.get(i).popCache(timeStamp);
    					 serverList.get(i).playCache(timeStamp);
    					 //System.out.println("timeStamp:"+timeStamp);
    				 }
    			 }
    			 if(phaseCount<8){
    				 //counting.get(timeStamp).play();
    				 phaseCount++;
    			 }
    			 
    			 timeStamp = (timeStamp+1)%4;
    			 
    			 try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		 }
    	 }
    	 
    	 public void stop(){
    	        stop_flag = true;
    	    }
     }
}
