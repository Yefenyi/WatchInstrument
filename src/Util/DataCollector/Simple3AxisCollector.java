package Util.DataCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.microedition.io.StreamConnection;

public class Simple3AxisCollector extends DataCollector{

	StreamConnection connection;
	InputStream inputStream;
	BufferedReader bReader;
	String name;
	
	public Simple3AxisCollector(String name,StreamConnection connection){
		this.name = name;
		this.connection = connection;
		try {
			this.inputStream = connection.openInputStream();
		} catch (Exception e) {
			//e.printStackTrace();
		}
        this.bReader=new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(this.name + "Listening started...");
	}
	
	@Override
	public double[] listen() {
		// TODO Auto-generated method stub
		try{
			String lineRead =bReader.readLine();
			//System.out.println(lineRead);
	    	if(lineRead.startsWith("/p")){
	    		String[] numbers = lineRead.split("/p");
	    		double x = Double.parseDouble(numbers[1]);
	    		double y = Double.parseDouble(numbers[2]);
	    		double z = Double.parseDouble(numbers[3]);
			return new double[]{x,y,z};
	    }else return null;
	}catch(IOException e){  
		return null;
    }    	
	}
}
