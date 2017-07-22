package Util.Sound;

import java.util.ArrayList;

public class PlayerIntent {
	private int start;
	private ArrayList<Integer> triggerPoints;
	private int index;
	
	public PlayerIntent(int start, ArrayList<Integer> triggerPoints){
		this.start = start;
		this.triggerPoints = triggerPoints;
	}
	
	public int getStart(){
		return start;
	}
	
	public ArrayList getTriggerPoints(){
		return triggerPoints;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return this.index;
	}
}
