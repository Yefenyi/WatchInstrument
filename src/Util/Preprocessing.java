package Util;

import Util.segmentation.Euclidean;
import Util.ParameterNameConstants;


//import net.sf.javaml.distance.fastdtw.*;


public class Preprocessing implements ParameterNameConstants {
	//private int SIZE_OF_BUFFER = 5;
	private double[][] sensorsHistory;
	private double[][] historyAverage;
	private double[] euclideanHistory;
	private double[] euclideanAverage;
	private double euclidean = 0;
	private int idx = 0;
	
	public Preprocessing(){
		initArrays();
	}
	
//	public Preprocessing(int buffer_size){
//		SIZE_OF_BUFFER = buffer_size;
//		initArrays();
//	}
	
	private void initArrays(){
		sensorsHistory = new double[SIZE_OF_BUFFER][NUMBER_OF_AXIS];
		historyAverage = new double[SIZE_OF_BUFFER][NUMBER_OF_AXIS];
		euclideanHistory = new double[SIZE_OF_BUFFER];
		euclideanAverage = new double[SIZE_OF_BUFFER];
		euclidean = 0;
	}	
	public double[][] getSensorsHistory()
	{
		return sensorsHistory;
	}
	
	public double[][] getHistoryAverage()
	{
		return historyAverage;
	}
	
	public double[] getEuclideanHistory()
	{
		return euclideanHistory;
	}
	
	public double[] getEuclideanAverage()
	{
		return euclideanAverage;
	}
	
	public void preprocess(double[] arrayAcc)
	{
		if (idx<SIZE_OF_BUFFER)
			fillInitialBuffer(arrayAcc);
		
		else if (idx>SIZE_OF_BUFFER)
		{
			fillBuffer(arrayAcc);
			historyAvrg();
			euclidean = Euclidean.calculateEuclidean(historyAverage[SIZE_OF_BUFFER-1][0],historyAverage[SIZE_OF_BUFFER-1][1],historyAverage[SIZE_OF_BUFFER-1][2],historyAverage[SIZE_OF_BUFFER-2][0],historyAverage[SIZE_OF_BUFFER-2][1],historyAverage[SIZE_OF_BUFFER-2][2]);
			filleuclideanHistory();
			eucAvrg();
			
		}
		
		idx++;
	}
	
	public void fillInitialBuffer(double[] arrayAcc){

			for (int i = 0; i< NUMBER_OF_AXIS; i++)
			{
				sensorsHistory[idx][i] = arrayAcc[i];
			}
			euclideanHistory[0] = 0;
			euclideanAverage[0] = euclidean;
		//	System.out.println(idx);
			
			if (idx>0)
			{
				euclidean = Euclidean.calculateEuclidean(sensorsHistory[idx][0],sensorsHistory[idx][1],sensorsHistory[idx][2],sensorsHistory[idx-1][0],sensorsHistory[idx-1][1],sensorsHistory[idx-1][2]);
				euclideanHistory[idx] = euclidean;
				euclideanAverage[idx] = euclidean;
			//	System.out.println(euclidean);
			//	System.out.println(euclideanArr[idx]);
			}
		
	}
	
	public void fillBuffer(double[] arrayAcc){
		for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
		{
			sensorsHistory[i][0] = sensorsHistory[i+1][0];
			sensorsHistory[i][1] = sensorsHistory[i+1][1];
			sensorsHistory[i][2] = sensorsHistory[i+1][2];
		}
		
		sensorsHistory[SIZE_OF_BUFFER-1][0] = arrayAcc[0];
		sensorsHistory[SIZE_OF_BUFFER-1][1] = arrayAcc[1];
		sensorsHistory[SIZE_OF_BUFFER-1][2] = arrayAcc[2];
	}
		
		//eucAvrg();

	public void historyAvrg()
	{
		double sumx = 0;
		double sumy = 0;
		double sumz = 0;
		
		for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
		{
			historyAverage[i][0] = historyAverage[i+1][0];
			historyAverage[i][1] = historyAverage[i+1][1];
			historyAverage[i][2] = historyAverage[i+1][2];
		}
		
		for (int i = 0; i< SIZE_OF_BUFFER; i++)
		{
			sumx = sumx + sensorsHistory[i][0];
			sumy = sumy + sensorsHistory[i][1];
			sumz = sumz + sensorsHistory[i][2];
		}
		
		historyAverage[SIZE_OF_BUFFER-1][0] = sumx/SIZE_OF_BUFFER;
		historyAverage[SIZE_OF_BUFFER-1][1] = sumy/SIZE_OF_BUFFER;
		historyAverage[SIZE_OF_BUFFER-1][2] = sumz/SIZE_OF_BUFFER;
	}
	
	public void filleuclideanHistory()
	{
		for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
		{
			euclideanHistory[i] = euclideanHistory[i+1];
		}
		
		euclideanHistory[SIZE_OF_BUFFER-1] = euclidean;
	}
	
	public void eucAvrg()
	{
		double sum = 0;
		
		for (int i = 0; i< SIZE_OF_BUFFER-1; i++)
		{
			euclideanAverage[i] = euclideanAverage[i+1];
		}
		
		for (int i = 0; i< SIZE_OF_BUFFER; i++)
		{
			sum = sum + euclideanHistory[i];
		}
		
		euclideanAverage[SIZE_OF_BUFFER-1] = sum/SIZE_OF_BUFFER;
	}
}

