package Info;

import java.util.ArrayList;

import Parser.SoundParser;
import Util.Sound.PlayerIntent;

public class SoundInfo {
	private String name;
	PlayerIntent intent;
    
    public SoundInfo(String name, PlayerIntent intent){
   	 this.name = name;
   	 this.intent = intent; 
    }
    
    public PlayerIntent getPlayerIntent(){
    	return this.intent;
    }
    
    public String getName(){
   	 return name;
    }
}
