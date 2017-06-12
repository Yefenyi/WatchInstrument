package Info;

import java.util.ArrayList;

public class ServerInfo {
	
	private String name;
	private String uuid;
	private String model;
    private ArrayList<SoundInfo> soundSource;
    
    public ServerInfo(String name, String uuid, String model, ArrayList<SoundInfo> soundsource){
    	this.name = name;
    	this.uuid = uuid;
    	this.model = model;
    	this.soundSource = soundsource;
    }
    
    public String getName(){
    	return name;
    }
    
    public String getModel(){
    	return model;
    }
    
    public String getUUID(){
    	return uuid;
    }
    
    public ArrayList<SoundInfo> getSoundSource(){
    	return soundSource;
    }
}
  