package Parser;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Util.Sound.Sound;
import Util.Sound.SoundListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SoundParser {
	private static ObservableList<Sound> soundData = FXCollections.observableArrayList();
	private File defaultfile;
	private static Map<String,String> locationMap;
	
	
	public SoundParser(){
		this.defaultfile = new File("src/soundlist.xml");
		loadSoundDataFromFile(this.defaultfile);

		int length = soundData.size();
		locationMap = new HashMap();
		for(int index = 0;index<length; index++){
			Sound sound= soundData.get(index);
			locationMap.put(sound.getName(),sound.getLocation());
		}
	}	
	
	public static String getLocation(String name){
		return locationMap.get(name);
	}
	
	public File getDefaultFile(){
		return defaultfile;
	}
	
    public ObservableList<Sound> getSoundData() {
    	return soundData;
    }
	
	public void loadSoundDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SoundListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SoundListWrapper wrapper = (SoundListWrapper) um.unmarshal(file);

            soundData.clear();
            soundData.addAll(wrapper.getSounds());
            
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }
	
	public void saveSoundDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SoundListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our sound data.
            SoundListWrapper wrapper = new SoundListWrapper();
            wrapper.setSounds(soundData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }
    }

}
