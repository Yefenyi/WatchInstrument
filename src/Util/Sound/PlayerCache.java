package Util.Sound;

import java.util.ArrayList;

public class PlayerCache {
    private ArrayList<Integer> cache;
    
    public PlayerCache(){
    	this.cache = new ArrayList<Integer>();
    }
    
    public void clear(){
    	cache.clear();
    }	
    
    public void push(int index){
    	cache.add(index);
    }
    
    public ArrayList<Integer> pop(){
    	return cache;
    }
}
