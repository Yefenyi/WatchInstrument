package Util.Sound;
import jaco.mp3.player.MP3Player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import kuusisto.tinysound.internal.StreamSound;

public class btAudioPlayer{
	
	private ArrayList<Sound> players;
	int index = 0,count;
	double volume=5,panning=0;
	String path;
	Thread playThread = new Thread();
	
	public btAudioPlayer(String path, int count){
		TinySound.init();
		this.path = path;
		assert(count>0);
		this.count = count;
	}
	
	public void setPanning(double panning){
		this.panning = panning;
	}
	
	public void setVolume(double volume){
		this.volume = volume;
	}
	
	public double getPanning(){
		return this.panning;
	}
	
	public double getVolume(){
		return this.volume;
	}
	
	@SuppressWarnings("deprecation")
	public void play(double volume,double panning){		
		/*
		 * index = (index+1)%count;
		players.get(index).play(volume,panning);	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		*/
		
		if(playThread.isAlive()){			
			playThread.destroy();
		}
		
		playThread = new Thread(){
			 public void run(){
	            	Sound player = TinySound.loadSound(path);
	            	player.play(volume,panning);
	            	try {
	        			Thread.sleep(1000);
	        		} catch (InterruptedException e) {}
	            }			
		};
		playThread.start();
		
	}
		
		public void play(){		
			/*
			 * index = (index+1)%count;
			players.get(index).play(volume,panning);	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			*/
			new Thread(new Runnable(){
	            public void run(){
	            	Sound player = TinySound.loadSound(path);
	            	player.play(volume,panning);
	            	try {
	        			Thread.sleep(1000);
	        		} catch (InterruptedException e) {}
	            }
	        }).start();
}

	
	public void terminate(){
		TinySound.shutdown();		
	} 
}
