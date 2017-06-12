package Util.Sound;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "soundlist")
public class SoundListWrapper {

	    private List<Sound> sounds;

	    @XmlElement(name = "sound")
	    public List<Sound> getSounds() {
	        return sounds;
	    }

	    public void setSounds(List<Sound> sounds) {
	        this.sounds = sounds;
	    }
	    
}
