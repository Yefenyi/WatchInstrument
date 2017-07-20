package Util.features;

import Util.classifier.DTW;
import Util.segmentation.Segmentation;

import java.util.ArrayList;
import java.util.List;
import Util.ParameterNameConstants;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Features implements ParameterNameConstants{
	
	static double[] MAD = new double [NUMBER_OF_AXIS];
	static double[] RMS = new double [NUMBER_OF_AXIS];
	static double[] RSS = new double [NUMBER_OF_AXIS];
	static double[] STD = new double [NUMBER_OF_AXIS];
	static double[] DTWDown = new double [NUMBER_OF_AXIS];
	static double[] DTWy = new double [NUMBER_OF_AXIS];
	static double[] DTWz = new double [NUMBER_OF_AXIS];

	public static double[] RMS(double[][] arr){
	    double[] ms = new double[NUMBER_OF_AXIS];
	    
	    for (int i = 0; i < arr.length; i++)
	    {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    		ms[j] += arr[i][j] * arr[i][j];
	    }
	    
	    for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    	ms[j] /= arr.length;
	    
	    for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    	RMS[j] = (double) Math.sqrt(ms[j]);
	    
	    return RMS;
	}

	public static double[] RSS(double[][] arr){
		double[] rss = new double [NUMBER_OF_AXIS];
		
		for (int i = 0; i < arr.length; i++)
	    {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    		rss[j] += arr[i][j] * arr[i][j];
	    }
		
		for (int j = 0; j<NUMBER_OF_AXIS; j++)
	    	RSS[j] = (double) Math.sqrt(rss[j]);
		
		return RSS;
	}

	public static double[] MAD (double[][] arr){
		double [] mad   = new double [NUMBER_OF_AXIS];
		double [] mean  = new double [NUMBER_OF_AXIS];
		double [] total = new double [NUMBER_OF_AXIS];
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
			   total[j] += arr[i][j];
				}// this is the calculation for summing up all the values
			}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			mean[i] = total[i] / arr.length;
		}
//		double mean = total[0] / arr.length;
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
			   arr[i][j] = (double) Math.abs((arr[i][j]-mean[j]));
			}
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			total[i] = 0;
		}
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
				   total[j] += arr[i][j];
					} // this is the calculation for summing up all the values
			}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			mad[i] = total[i] / arr.length;
		}

		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			MAD[i] = mad[i];
		}
		
		return MAD;
	}
	
	public static double[] STD(double[][] arr) {
		double [] std   = new double [NUMBER_OF_AXIS];
		double [] mean  = new double [NUMBER_OF_AXIS];
		double [] total = new double [NUMBER_OF_AXIS];
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
			   total[j] += arr[i][j];
				}// this is the calculation for summing up all the values
			}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			mean[i] = total[i] / arr.length;
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			total[i] = 0;
		}
		
		for(int i = 0; i < arr.length; i++){
			for (int j = 0; j < NUMBER_OF_AXIS; j++){
				total[j] += (double) (arr[i][j]-mean[j]) * (arr[i][j]-mean[j]);
			}
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			total[i] = total[i]/arr.length;
		}
		
		for (int i = 0; i< NUMBER_OF_AXIS; i++)
		{
			STD[i] = (double) Math.sqrt(total[i]);
		}
		
		return STD;
	}
	
	public static double[] calculateMean(double[][] sample) {
		
	    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
		DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
		double [] mean  = new double [NUMBER_OF_AXIS];
		
		for (int i = 0; i<NUMBER_OF_AXIS; i++){
			stats[i] = new DescriptiveStatistics();
		}

	    // Add the data from the series to stats
	    for (int i = 0; i < sample.length; i++) {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
	    		//.out.println(sample.length);
	    		stats[j].addValue(sample[i][j]);
	    	}
	    }
	    
	    for (int i = 0; i< NUMBER_OF_AXIS; i++)
	    {
	    	mean[i] = stats[i].getMean();
	    }

	    return mean;
	}
	
	
public static double[] calculateSTD(double[][] sample) {
		
	    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
		DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
		double [] std  = new double [NUMBER_OF_AXIS];
		
		for (int i = 0; i<NUMBER_OF_AXIS; i++){
			stats[i] = new DescriptiveStatistics();
		}

	    // Add the data from the series to stats
	    for (int i = 0; i < sample.length; i++) {
	    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
	    		//.out.println(sample.length);
	    		stats[j].addValue(sample[i][j]);
	    	}
	    }
	    
	    for (int i = 0; i< NUMBER_OF_AXIS; i++)
	    {
	    	std[i] = stats[i].getStandardDeviation();
	    }

	    return std;
	}

public static double[] calculateVarience(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] var  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	var[i] = stats[i].getVariance();
    }

    return var;
}


public static double[] calculateMin(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] min  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	min[i] = stats[i].getMin();
    }

    return min;
}

public static double[] calculateMax(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] max  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	max[i] = stats[i].getMax();
    }

    return max;
}

public static double[] calculateSumOfSquares(double[][] sample) {
	
    //ArrayList<DescriptiveStatistics> list =  new  ArrayList<DescriptiveStatistics>(3);
	DescriptiveStatistics stats[] = new DescriptiveStatistics[NUMBER_OF_AXIS];
	double [] sumsq  = new double [NUMBER_OF_AXIS];
	
	for (int i = 0; i<NUMBER_OF_AXIS; i++){
		stats[i] = new DescriptiveStatistics();
	}

    // Add the data from the series to stats
    for (int i = 0; i < sample.length; i++) {
    	for (int j = 0; j<NUMBER_OF_AXIS; j++){
    		//.out.println(sample.length);
    		stats[j].addValue(sample[i][j]);
    	}
    }
    
    for (int i = 0; i< NUMBER_OF_AXIS; i++)
    {
    	sumsq[i] = stats[i].getSumsq();
    }

    return sumsq;
}

public static Util.classifier.DTW[][] DTW(double[][] sample)
{
	DTW[][] dtw = new DTW[NUMBER_OF_GESTURES][NUMBER_OF_AXIS];
	DTW[] dtwDown = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwRight = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwLeft = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwUp = new DTW[NUMBER_OF_AXIS];
	double[][] acc = new double[NUMBER_OF_AXIS][SIZE_OF_SEGMENT];
    double[] ax = new double[sample.length];
    double[] ay = new double[sample.length];
    double[] az = new double[sample.length];
	
    
    double[][] down1 = {
			{-0.06652060000000004, 1.1040484, 2.4071796, 3.9486358000000004, 7.4120074, 9.599525799999999, 10.639925, 10.8327866, 9.8297156, 5.9667426},
			{6.6333826, 9.6081394, 12.586246399999999, 13.145209799999998, 7.633103799999999, -1.715175400000001, -12.3216006, -22.7720144, -29.120651199999998, -29.630800999999998},
			{1.7692529999999997, 1.5457634, 1.4744574, 1.0308282, -0.9298508, -2.8019954, -4.3520658, -5.9796638, -6.9745996, -6.025606}
			};
	
	double[][] right1 = {
			{0.9489932, 2.172204, 4.0883766, 5.8657652, 6.9678993999999985, 7.0597838, 7.0013992, 6.0323058000000005, 4.0950766, 2.3133808},
			{-11.602796799999998, -12.601561199999999, -13.269637, -12.4742628, -10.833743599999998, -10.5001844, -11.603275400000001, -10.336036799999999, -8.7878806, -8.6802034},
			{6.146682800000001, 10.0216198, 13.529498, 15.046547800000003, 14.4024, 9.800523600000002, 1.3404599999999995, -9.0448692, -16.673666400000002, -20.912791}
			};
	
	double[][] left1 = {
			{-3.9405004, -4.314738, -4.5994839999999995, -0.7948961999999998, 0.13064779999999998, 1.3423732000000002, 2.504328, 6.8942006000000005, 9.644989599999999, 9.175517800000002},
			{1.0762916, 1.9989642, 3.0336208, 3.3848872, 2.7747174, 1.735754, 0.5546565999999998, 0.4603794, 0.5804990000000001, -1.85348},
			{3.9376288, 0.0602992000000004, -3.9582071999999995, -5.664289800000001, -5.6312686, -1.7730814000000001, 1.2543176, 7.2095748, 14.377992599999999, 19.3598486}
			};
	
	double[][] up1 = {
			{0.47617180000000003, 0.2057828, 0.23353959999999999, -0.015314000000000005, 0.8240882, 1.3150954000000001, 1.8702302, 2.57037, 3.5136206000000003, 3.5160134, 3.6547970000000007, 3.3791438, 2.2459032, 1.1787044000000002, -0.00334999999999992, -1.0920842, -1.7955739999999998},
			{-12.3187294, -14.843636, -18.5367184, -22.0828816, -25.571138400000002, -27.531338600000005, -25.4768608, -19.89297, -12.9317704, -4.7483168000000004, 3.1858044, 7.566584000000001, 7.487621, 5.3551374, 1.9511077999999997, -2.1348762, -5.6379686},
			{-1.1696118, -2.0118853999999997, -3.2499318, -5.0325846, -7.259824, -9.357851, -10.7428168, -10.920842599999999, -9.849815199999998, -7.8747793999999995, -5.424529, -2.4933211999999996, 0.026320800000000234, 1.7166105999999999, 2.5741982, 2.9900708000000003, 2.54692}
			};

	for (int j = 0; j<NUMBER_OF_AXIS ; j++){
		for (int i =0; i<sample.length; i++ )
		
		{
			acc[j][i] = sample[i][j];
		}
	}
	
	//System.out.println("Up");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwLeft[i] = new DTW(left1[i], acc[i]);
		//System.out.println(dtwLeft[i]);
		dtw[0][i] = dtwLeft[i] ;
	}
	
	//System.out.println("Down");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwDown[i] = new DTW(down1[i], acc[i]);
		//System.out.println(dtwDown[i]);
		dtw[1][i] = dtwDown[i];
	}
	
	//System.out.println("Right");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwRight[i] = new DTW(right1[i], acc[i]);
		//System.out.println(dtwRight[i]);
		dtw[2][i] = dtwRight[i];
	}
	
	//System.out.println("Left");
/*	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwLeft[i] = new DTW(left1[i], acc[i]);
	//	System.out.println(dtwLeft[i]);
		dtw[3][i] = dtwLeft[i];
	}*/
	return dtw;
	//DTW dtw = new DTW(sample, templete)
}

public static ArrayList<Double> getFeature(Segmentation seg){
	ArrayList<Double> featurelist = new ArrayList<Double>();
	double[][] buffSegment	= seg.getBuffSegment();
	try{
		
		double[] MeanFeature = Features.calculateMean(buffSegment);
		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
		{
			featurelist.add(MeanFeature[i]);
		}
		
		double[] STDFeature =Features.calculateSTD(buffSegment);
		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
		{
		    featurelist.add(STDFeature[i]);
		}
		
		double[] VarienceFeature =Features.calculateVarience(buffSegment);
		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
		{
		    featurelist.add(VarienceFeature[i]);
		}
		
		double[] MinFeature =Features.calculateMin(buffSegment);
		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
		{
			featurelist.add(MinFeature[i]);
		}
		
		double[] MaxFeature =Features.calculateMax(buffSegment);
		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_AXIS; i++)
		{
		    featurelist.add(MaxFeature[i]);
		}
		
		//UNCOMMENT
		DTW[][] d  = Features.DTW(buffSegment);
		
		for (int i = 0; i<ParameterNameConstants.NUMBER_OF_GESTURES; i++)
		{
			for (int j = 0; j<NUMBER_OF_AXIS; j++)
			{
				featurelist.add(d[i][j].getDistance());
			}
		}
		//System.out.println("feature");

        return featurelist;
	}catch(Exception e){
		return null;
	}
	
} 

}
