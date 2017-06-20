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
	private ArrayList<Integer> outputlist = new ArrayList<Integer>(Arrays.asList(output1,output2,output3,output4));
	
	public UserModel(String path){
		NeuralNetwork nnNet = NeuralNetwork.createFromFile(path);
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
			if(seg.segment(ppc)==true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    double[][] buffsegment = seg.getBuffSegment();
        featurelist = Features.getFeature(seg);
        process();
        
        if (output[0] > 0.93)
        {
        	return output1;
        }
        
        if (output[1] > 0.93)
        {
        	return output2;
        }
        
        if (output[2] > 0.93)
        {
        	return output3;
        }
        
        if (output[3] > 0.93)
        {
        	return output4;
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
