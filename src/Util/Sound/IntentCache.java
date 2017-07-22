package Util.Sound;

import java.util.ArrayList;

public class IntentCache {
    private ArrayList<PlayerIntent> cache;
    
    public IntentCache(){
    	this.cache = new ArrayList<PlayerIntent>();
    }
    
    public void clear(){
    	cache.clear();
    }	
    
    public void push(PlayerIntent intent){
    	cache.add(intent);
    }
    
    public ArrayList<PlayerIntent> pop(){
    	return cache;
    }
}
