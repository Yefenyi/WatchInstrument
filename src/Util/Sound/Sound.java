package Util.Sound;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sound {
	    private final StringProperty name;
	    private final StringProperty location;
	    
	    /**
	     * Default constructor.
	     */
	    public Sound() {
	        this(null, null);
	    }

	    /**
	     * Constructor with some initial data.
	     * 
	     * @param name
	     * @param location
	     */
	    public Sound(String name, String location) {
	        this.name = new SimpleStringProperty(name);
	        this.location = new SimpleStringProperty(location);
	    }

	    public String getName() {
	        return this.name.get();
	    }

	    public void setName(String name) {
	        this.name.set(name);
	    }

	    public StringProperty nameProperty() {
	        return name;
	    }
	    
	    public String getLocation() {
	        return this.location.get();
	    }

	    public void setLocation(String location) {
	        this.location.set(location);
	    }

	    public StringProperty locationProperty() {
	        return location;
	    }
}
