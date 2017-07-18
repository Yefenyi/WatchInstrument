package Util.Model;

import java.util.ArrayList;
import java.util.Arrays;

import Util.segmentation.ChannelSegmentation; 

public class EuclideanModel extends Model{

	static final int TRIGGERED = 1;
	static final int UNTRIGGERED = 0;
	private ArrayList<Integer> outputArray = new ArrayList<Integer>(Arrays.asList(UNTRIGGERED,TRIGGERED));
	
	private double lastx=0., lasty=0., lastz=0.;
	private int FLAG = 0;
	private long lasttime;
	private ChannelSegmentation cnlseg = new ChannelSegmentation();
	
	public EuclideanModel(){
		this.lasttime = System.currentTimeMillis();	
	}
	
	@Override
	public int update(ArrayList<Double> newdata) {
		
		assert(newdata.size()==3);
		
		double x = newdata.get(0), y = newdata.get(1), z = newdata.get(2);
		
		double triggered  = cnlseg.update(new double[]{x,y,z});
		if(triggered == 1){
			return TRIGGERED;
		}
		
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
