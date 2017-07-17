package Parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Info.ServerInfo;
import Info.SoundInfo;

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
		               	               
		               Element content = (Element) serverElement.getElementsByTagName("soundList").item(0);
		               NodeList soundList  =  content.getElementsByTagName("sound");
		               
		               int soundLength = soundList.getLength();
		               ArrayList<SoundInfo> soundInfoList = new ArrayList<SoundInfo>(soundLength);
		               
		               for  (int sindex = 0; sindex < soundLength; sindex++){
		            	   Node soundNode = soundList.item(sindex);
		            	   
		            	   Element soundElement = (Element) soundNode;	 
		            	   String soundname = soundElement.getTextContent();
		            	   
		            	   System.out.println("sound source : " 
		     	                 + soundname+ ", " + SoundParser.getLocation(soundname));
		            	   soundInfoList.add(sindex, new SoundInfo(soundname)); 
		               }		               
		               serverInfoList.add(temp, new ServerInfo(name, uuid,model, panning, soundInfoList));
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
