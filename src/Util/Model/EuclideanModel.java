package Util.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class EuclideanModel extends Model{

	static final int TRIGGERED = 1;
	static final int UNTRIGGERED = 0;
	private ArrayList<Integer> outputArray = new ArrayList<Integer>(Arrays.asList(UNTRIGGERED,TRIGGERED));
	
	private double lastx=0., lasty=0., lastz=0.;
	private int FLAG = 0;
	private long lasttime;
	
	public EuclideanModel(){
		this.lasttime = System.currentTimeMillis();	
	}
	
	@Override
	public int update(ArrayList<Double> newdata) {
		
		assert(newdata.size()==3);
		
		double x = newdata.get(0), y = newdata.get(1), z = newdata.get(2);
		
		double eudist =  Math.pow((x-lastx),2)+Math.pow((y-lasty),2)+Math.pow((z-lastz),2);
		if(eudist>130){
			long time = System.currentTimeMillis();
			if ((time-lasttime)>220){
    			if( FLAG == 0){
    			    FLAG = 1;
    			    lasttime = time;
    			    return TRIGGERED;
    			}
		    }
		}
		else{
			FLAG =0;
		}

		lastx = x;
		lasty = y;
		lastz = z;
		
		return UNTRIGGERED;
	}

	@Override
	protected double process() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Integer> getOutPutArray() {
		// TODO Auto-generated method stub
		return this.outputArray;
	}

}
