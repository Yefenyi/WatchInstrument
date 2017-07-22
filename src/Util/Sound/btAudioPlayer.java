package Util.Sound;
import jaco.mp3.player.MP3Player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Server.Server;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import kuusisto.tinysound.internal.StreamSound;

public class btAudioPlayer{
	
	private Sound player;
	private int index = 0,count;
	private double volume=5,panning=0;
	private String path;
	private Thread playThread = new Thread();
	private int interrupting;
	Server server;

	private PlayerIntent intent;	
	
	public btAudioPlayer(String path, int interrupting, int index , PlayerIntent intent, Server server){
		
		TinySound.init();
		this.path = path;
		this.interrupting = interrupting;
		assert(count>0);
		this.count = count;
		this.intent = intent;
		this.index = index;
		this.intent.setIndex(index);
		this.server = server;
		
		player = TinySound.loadSound(path);
		this.playThread = new Thread(){
			 public void run(){
	            	Sound player = TinySound.loadSound(path);
	            	player.play(volume,panning);
	            	try {
	        			Thread.sleep(1000);
	        		} catch (InterruptedException e) {}
	            }			
		};
	}
	
	public void stop(){
		if(interrupting == 0){
			return;
		}
		if(interrupting == 1){
			player.stop();
		}
	}
	
	public PlayerIntent getIntent(){
		return this.intent;
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

		
		public void play(){		
			if(interrupting == 0){
				player.play(volume,panning);				
			}
			if(interrupting == 1){
				//player.stop();
				player.play(volume,panning);
			}
}

	
	public void terminate(){
		TinySound.shutdown();		
	} 
}
