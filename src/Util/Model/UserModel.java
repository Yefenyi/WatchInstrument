package Util.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;

import Util.Preprocessing;
import Util.features.Features;
import Util.segmentation.Segmentation;

public class UserModel extends Model{
	private NeuralNetwork nnNet;
	private Preprocessing ppc;
	private Segmentation seg;
	private ArrayList<Double> featurelist;
	private double[] output;
	private int output1=1, output2=2, output3=3, output4=4;
	private int UNTRIGGERED = 0;
	private ArrayList<Integer> outputlist = new ArrayList<Integer>(Arrays.asList(UNTRIGGERED,output1,output2,output3,output4));
	private double[] initializedOutput = new double[5];
	
	public UserModel(String path){
		nnNet = NeuralNetwork.createFromFile(path);
		ppc = new Preprocessing();
		seg = new Segmentation();
	}


	@Override
	public int update(ArrayList<Double> newdata) {
	    int length = newdata.size();
	    double[] newdataarray = new double[length];
	    for (int i  =0;i<length;i++){
	    	newdataarray[i] = newdata.get(i);
	    }
	    
	    ppc.preprocess(newdataarray);
	    try {
	    	boolean READY_FOR_FEATURE_EXTRACTION = seg.segment(ppc);
	    	//System.out.println("Segmentation: "+READY_FOR_FEATURE_EXTRACTION);
			if(READY_FOR_FEATURE_EXTRACTION){
			    double[][] buffsegment = seg.getBuffSegment();
		        featurelist = Features.getFeature(seg);
		        process();
		        
		        if (output[0] > 0.93)
		        {
		            output = initializedOutput; 
		        	return output1;
		        }
		        
		        if (output[1] > 0.93)
		        {
		            output = initializedOutput; 
		        	return output2;
		        }
		        
		        if (output[2] > 0.93)
		        {
		            output = initializedOutput; 
		        	return UNTRIGGERED;
		        }
		        
		        if (output[3] > 0.93)
		        {
		            output = initializedOutput; 
		        	return UNTRIGGERED;
		        }
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return 0;
	}

	@Override
	protected double process() {
		// TODO Auto-generated method stub
		int featurelength = featurelist.size();
		double[] input = new double[featurelength];
		for (int i=0; i<featurelength;i++){
			input[i] = featurelist.get(i);
		}
		nnNet.setInput(input);
		nnNet.calculate();
		output = nnNet.getOutput();
		return 0;
	}

	@Override
	public ArrayList<Integer> getOutPutArray() {
		// TODO Auto-generated method stub
		return outputlist;
	}
	
}
