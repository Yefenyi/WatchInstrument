package Util.Sound;
import jaco.mp3.player.MP3Player;
import java.io.File;
import java.io.IOException;

public class btAudioPlayer {
	private MP3Player[] players;
	int index = 0,count;
	
	public btAudioPlayer(String path, int count){
		assert(count>0);
		this.count = count;
		players = new MP3Player[count];
		for(int i=0; i<count; i++){
			players[i] = new MP3Player(new File(path));			
		}
	}
	public void  play(){		
		index = (index+1)%count;
		players[index].play();		
		}
	
}
