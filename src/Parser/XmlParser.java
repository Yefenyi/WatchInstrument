package Parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Info.ServerInfo;
import Info.SoundInfo;
import Util.Sound.PlayerIntent;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XmlParser {
	  private ArrayList<ServerInfo> serverInfoList;
	  public XmlParser(){
		  try{
		     File inputFile = new File("src/configure.xml");
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         System.out.println("Root element :" 
	 	            + doc.getDocumentElement().getNodeName());
	 	         
	 	     NodeList serverList = doc.getElementsByTagName("server");
	 	     System.out.println("----------------------------");
	 	     
	 	     int length = serverList.getLength();
	 	     this.serverInfoList = new ArrayList<ServerInfo>(length); 
	 	     
	         for (int temp = 0; temp < length; temp++) {
	        	 
		            Node root = serverList.item(temp);

		            System.out.println("\nCurrent Element :" 
		               + root.getNodeName());
		            
		            if (root.getNodeType() == Node.ELEMENT_NODE) {
		               Element serverElement = (Element) root;
		               
		               String name = serverElement.getAttribute("name");
		               System.out.println("server name : " 
		                  + name);
		             
		               String uuid =serverElement .getElementsByTagName("uuid") .item(0) .getTextContent();		              
		               System.out.println("uuid : " + uuid);
		               
		               String model =serverElement .getElementsByTagName("model") .item(0) .getTextContent();		              
		               System.out.println("model : " + model);
		               
		               double panning =Double.parseDouble(serverElement.getElementsByTagName("panning") .item(0) .getTextContent());		              
		               System.out.println("panning : " + panning);
		               
		               int interrupt = Integer.parseInt(serverElement.getElementsByTagName("interrupt") .item(0) .getTextContent());		              
		               System.out.println("interrupt : " + panning);
		               	               
		               Element content = (Element) serverElement.getElementsByTagName("soundList").item(0);
		               NodeList soundList  =  content.getElementsByTagName("sound");
		               
		               int soundLength = soundList.getLength();
		               ArrayList<SoundInfo> soundInfoList = new ArrayList<SoundInfo>(soundLength);
		               
		               for  (int sindex = 0; sindex < soundLength; sindex++){
		            	   Node soundNode = soundList.item(sindex);
		            	   
		            	   Element soundElement = (Element) soundNode;	 
		            	   String soundname = soundElement.getTextContent();
		            	   int start = Integer.parseInt(soundElement.getAttribute("start"));
		            	   String triggerData = soundElement.getAttribute("trigger");
		            	   String[] triggerArray = triggerData.split(",");
		            	   ArrayList<Integer> triggerPoints  = new ArrayList<>();
		            	   
		            	   for(String point : triggerArray){
		            		   triggerPoints.add(Integer.parseInt(point)-1);
		            	   }
		            	   
		            	   PlayerIntent intent = new PlayerIntent(start,triggerPoints);
		            	   
		            	   System.out.println("sound source : " 
		     	                 + soundname+ ", " + SoundParser.getLocation(soundname)+", start : "+start+",triggerPoints : "+triggerData);
		            	   soundInfoList.add(sindex, new SoundInfo(soundname,intent)); 
		               }		               
		               serverInfoList.add(temp, new ServerInfo(name, uuid,model, panning, interrupt, soundInfoList));
		            }
		         }	 	     
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  
	  }
	  
	  public ArrayList<ServerInfo> getServerInfo(){
		  return this.serverInfoList;
	  }
}
