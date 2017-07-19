package Info;

import java.util.ArrayList;

public class ServerInfo {
	
	private String name;
	private String uuid;
	private String model;
	private double panning;
	private int interrupt;
    private ArrayList<SoundInfo> soundSource;
    
    public ServerInfo(String name, String uuid, String model, double panning,int interrupt, ArrayList<SoundInfo> soundsource){
    	this.name = name;
    	this.uuid = uuid;
    	this.model = model;
    	this.panning = panning;
    	this.soundSource = soundsource;
    	this.interrupt = interrupt;
    }
    
    public int getInterrupt(){
    	return interrupt;
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
    
    public double getPanning(){
    	return this.panning;
    }
    
    public ArrayList<SoundInfo> getSoundSource(){
    	return soundSource;
    }
}
  