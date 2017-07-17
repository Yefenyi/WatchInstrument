package Server;

import Parser.XmlParser;

import java.io.IOException;
import java.util.ArrayList;
import Info.ServerInfo;
import Server.Server;
import application.MainApp;
import gui.view.mainLayoutController;

public class ServerManager {
	
	 private ArrayList<Server> serverList;
	 
	 public Server getServer(int index){
		return serverList.get(index);
	 }
	
	 public int getServerCount(){
	    return serverList.size(); 	 
	 }
     
     public ServerManager() throws IOException{
		 XmlParser xmlparser =  new XmlParser();
		 ArrayList<ServerInfo> serverinfolist = xmlparser.getServerInfo();
		 
		 int length = serverinfolist.size();
		 serverList = new ArrayList<Server>(length);
		 
		 for (int index=0; index < length; index++){
			 Server server = new Server(serverinfolist.get(index),index);
			 serverList.add(index,server);
		 }
		 
		 for (int index=0; index < length; index++){
			 Thread serverThread = new Thread(serverList.get(index));
			 serverThread.start();   		 
		 }
	 }
     
     public void setController(mainLayoutController controller){
    	 for (int i= 0; i<serverList.size();i++){
    		    serverList.get(i).setController(controller);
    	 } 
     }
}
