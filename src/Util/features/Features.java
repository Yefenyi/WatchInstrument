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
	DTW[] dtwForward = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwBackward = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwConDown = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwConDownRight3 = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwsemiCircleUp = new DTW[NUMBER_OF_AXIS];
	DTW[] dtwPat = new DTW[NUMBER_OF_AXIS];
	double[][] acc = new double[NUMBER_OF_AXIS][SIZE_OF_SEGMENT];
    double[] ax = new double[sample.length];
    double[] ay = new double[sample.length];
    double[] az = new double[sample.length];
	
    
	double[][] down10 = {
			{-2.6636904, -2.4698718, -1.6902902000000002, 2.9905497999999997, 5.0842696, 6.775516599999999, 9.120482599999999, 12.691052599999999, 8.494519999999998, 6.584090399999998},
			{-2.475136, 1.2997808000000002, 5.9385069999999995, 8.3413796, 9.710552799999999, 7.6240116, 1.0059430000000007, -14.4177132, -26.5119956, -32.573494},
			{1.9865213999999998, 0.5345565999999998, -1.9884356000000003, -2.0592632, -1.4404794, -0.7585250000000002, -2.0587846, -1.9334006000000001, -1.1289336000000003, -1.9195222}
			};
	
	double[][] right10 = {
			{0.9489932, 2.172204, 4.0883766, 5.8657652, 6.9678993999999985, 7.0597838, 7.0013992, 6.0323058000000005, 4.0950766, 2.3133808},
			{-11.602796799999998, -12.601561199999999, -13.269637, -12.4742628, -10.833743599999998, -10.5001844, -11.603275400000001, -10.336036799999999, -8.7878806, -8.6802034},
			{6.146682800000001, 10.0216198, 13.529498, 15.046547800000003, 14.4024, 9.800523600000002, 1.3404599999999995, -9.0448692, -16.673666400000002, -20.912791}
			};
	
	double[][] left10 = {
			{-3.9405004, -4.314738, -4.5994839999999995, -0.7948961999999998, 0.13064779999999998, 1.3423732000000002, 2.504328, 6.8942006000000005, 9.644989599999999, 9.175517800000002},
			{1.0762916, 1.9989642, 3.0336208, 3.3848872, 2.7747174, 1.735754, 0.5546565999999998, 0.4603794, 0.5804990000000001, -1.85348},
			{3.9376288, 0.0602992000000004, -3.9582071999999995, -5.664289800000001, -5.6312686, -1.7730814000000001, 1.2543176, 7.2095748, 14.377992599999999, 19.3598486}
			};
	
	double[][] up10 = {
			{0.47617180000000003, 0.2057828, 0.23353959999999999, -0.015314000000000005, 0.8240882, 1.3150954000000001, 1.8702302, 2.57037, 3.5136206000000003, 3.5160134, 3.6547970000000007, 3.3791438, 2.2459032, 1.1787044000000002, -0.00334999999999992, -1.0920842, -1.7955739999999998},
			{-12.3187294, -14.843636, -18.5367184, -22.0828816, -25.571138400000002, -27.531338600000005, -25.4768608, -19.89297, -12.9317704, -4.7483168000000004, 3.1858044, 7.566584000000001, 7.487621, 5.3551374, 1.9511077999999997, -2.1348762, -5.6379686},
			{-1.1696118, -2.0118853999999997, -3.2499318, -5.0325846, -7.259824, -9.357851, -10.7428168, -10.920842599999999, -9.849815199999998, -7.8747793999999995, -5.424529, -2.4933211999999996, 0.026320800000000234, 1.7166105999999999, 2.5741982, 2.9900708000000003, 2.54692}
			};
	
	
	double[][] down15 = {
			{-4.4635714, -4.393700999999999, -4.1601615999999995, -3.3863228, 0.29383839999999994, 4.2314672, 5.7853664, 6.4127646, 6.4874206, 3.7744384, 0.729811, -0.2943172, -0.44506499999999993, -0.6427122, -0.7355537999999999},
			{-1.8084954000000004, 0.4790429999999999, 4.4444288, 7.929335800000001, 8.516056000000003, 6.870272600000002, 1.7185256000000002, -6.027041, -14.977633400000002, -21.835941400000003, -25.77357, -27.026452, -25.5141884, -21.883798, -17.451333599999998},
			{2.3799012, 1.1758328000000002, -0.9332010000000001, -4.0476988, -5.2512886000000005, -4.0055852000000005, -0.9427724000000002, 1.8640085999999996, 3.8175090000000003, 4.7976092, 3.6916466, 0.6853044, -0.9102298000000001, -0.2718248, -0.49627160000000003}
			};
	
	double[][] right15 = {
			{-2.5502706, -1.2184256, 0.6101694000000002, 3.1848466, 5.5963332, 6.8334226000000005, 6.7003818, 5.7509098, 3.3466016000000005, 0.6259624, -1.2016756000000002, -2.4052654, -3.2982667999999995, -3.3796228, -3.122155},
			{-11.1079616, -12.1100758, -13.912828, -13.639088999999998, -13.1523888, -12.872906799999999, -12.1933452, -8.959685, -7.772844999999999, -7.71781, -7.896315000000001, -6.9281788, -6.894679399999999, -7.7063248, -8.6136832},
			{4.3678586, 8.0188276, 11.100304400000002, 13.737195200000002, 15.071911600000002, 11.9952202, 2.6689543999999996, -7.8790864, -15.9893192, -20.9333694, -20.698394200000003, -15.4274844, -8.308836800000002, -3.3877581999999995, -0.9667001999999998}
			};
	
	double[][] pat15 = {
			{-5.1445686, -5.522156000000001, -5.6470614, -4.7588458000000005, -2.8192238, -0.3201598000000002, 3.599284, 10.0201848, 12.795859, 12.440764, 10.9136646, 8.8323872, 4.4300716, 2.3818156, 2.1932612},
			{-1.5428922, -2.0161926, -2.3818158, -2.9388648, -4.1462829999999995, -4.4128434, -2.478964, -0.7590032000000002, 0.7135398000000001, 2.2358535999999996, 2.428715, 1.3141382, 0.49675, -0.24933199999999994, -0.4666003999999999},
			{0.9604792, -2.0147562, -5.4001216, -8.054719, -10.895478, -12.714502, -10.692567200000001, -1.953500600000001, 7.463692400000001, 17.1163392, 24.7666718, 28.8952478, 25.954947400000002, 21.027646800000003, 15.950555400000002}
			};
	
	
	double[][] down20 = {
			{-4.5004206, -4.0563128, -3.111148, -1.5352349999999997, 1.5826128000000002, 4.1601612, 5.5030134, 6.8090162, 9.5219984, 7.9441711999999995, 5.2024752, 2.9623146, 1.0858629999999998, -2.5536202, -3.3221947999999997, -2.3473588, -0.7551748, 0.09523440000000005, 0.6154339999999999, 0.9528220000000001},
			{-1.191147, 0.33068800000000004, 3.3221947999999997, 8.0082986, 11.804272399999999, 14.1344028, 12.337871600000001, 5.6489758, -7.317251999999999, -18.5553826, -26.0879882, -30.1701434, -30.393633599999998, -25.608466200000002, -20.7122728, -17.852849600000003, -15.257594400000002, -13.1365962, -11.3027372, -10.177631799999999},
			{2.3119454, 1.9994428, 1.578306, 0.8355738, 0.2038686, 1.5917058000000002, 0.5747561999999998, -4.3377088, -5.9854066, -7.1387472, -11.0993472, -11.2673234, -6.1744396, -4.103690800000001, -3.1159336000000004, -1.2002398, -0.21966100000000005, -0.5000998, -0.7771886, -0.7331605999999999}  
			};
	
	double[][] right20 = {
			{-0.8867801999999999, -0.27230340000000003, 0.6417548, 1.5567701999999999, 2.7225534, 3.9845281999999997, 4.995256599999999, 5.7954164, 6.114619, 5.3766726, 4.1146978, 2.7469602, 1.2059826, -0.1770688, -0.9394221999999999, -1.3715658, -1.7257034, -2.0985052, -2.16359, -1.9410574},
			{-10.6667254, -11.740624200000001, -12.8465866, -13.608461199999999, -13.428520800000001, -13.763994400000001, -13.520405, -12.213445199999999, -9.8632148, -7.777630599999999, -6.5644696, -5.7126247999999995, -4.7746384, -5.691568, -7.981020600000001, -8.9639922, -8.9362356, -9.8828362, -10.8452294, -10.9945416},
			{1.4270796, 3.4494934, 6.9406216, 9.743573799999998, 12.7216806, 15.389199, 15.397334599999999, 11.308000799999999, 5.532205999999998, -1.6180263999999998, -8.2983086, -13.058589800000002, -14.893885000000001, -13.5438546, -10.6772532, -8.2250884, -5.8753367999999995, -3.6069407999999994, -2.0204994, -0.9236293999999999}
			};
	
	double[][] left20 = {
			{-2.8914866, -3.0886554, -3.4226936, -3.5251064000000008, -2.5942985999999997, -3.033621, -2.1559336, -0.7747963999999999, 0.9049652, 1.262453, 2.745046, 3.1178478, 2.9527432, 2.1425334, 1.4730216, 0.8762516000000001, 0.06173480000000007, -0.7250254, -1.4691931999999999, -2.0291135999999996},
			{-9.211888400000001, -9.227681, -9.3229154, -9.2611808, -8.305487600000001, -8.414600400000001, -8.6950392, -7.649375600000001, -7.8006018, -10.1632748, -11.3094368, -11.6492176, -12.6484604, -12.8767358, -12.089975800000001, -11.1668248, -10.532248200000002, -10.512627, -10.3274224, -9.9254282},
			{-0.4048654, -1.1710471999999998, -4.728696, -9.024291199999999, -13.4136852, -16.071153799999998, -16.9541054, -13.442398799999998, -7.157889600000002, 1.2964314, 7.825008600000001, 12.1718104, 13.881721399999998, 12.918370999999999, 9.51482, 6.0122062000000005, 3.1599613999999994, 1.2619745999999998, 0.07465600000000006, -0.6685546}
			};
	
	double[][] up20 = {
			{1.6170696, 1.7501106, 2.422015, 2.3258236, 1.4696715999999999, 1.2557534000000001, 1.3820944000000002, 1.0451849999999998, 1.3208381999999999, 1.9410577999999998, 1.8228521999999998, 1.28686, 0.5551348, -0.07226340000000002, -0.45415780000000006, -0.9111868000000001, -1.4792429999999999, -2.0516061999999997, -2.6981468, -3.3212378},
			{-12.044512000000001, -14.329657400000002, -18.1696594, -21.7359224, -24.907847999999998, -26.8594342, -25.886512800000002, -21.3669492, -15.1034966, -8.085826599999999, -1.5902702, 3.1657041999999995, 5.5666624, 5.5140204, 4.135275999999999, 2.3755942, 0.38285139999999995, -1.623291, -3.322195, -4.6899326},
			{-2.8106093999999997, -3.6997822, -4.8813580000000005, -5.7839306, -6.411807400000001, -6.6305114000000005, -6.376393800000001, -5.725546, -4.9105508, -3.903651, -2.9584859999999997, -1.8137593999999997, -0.5661418000000001, 0.33068820000000004, 0.6824332, 0.9676576000000001, 1.245225, 1.408894, 1.4988642, 1.713261}
			};
	
	double[][] forward20 ={
			{-8.5280196, -11.170653000000001, -14.4253708, -17.5910752, -20.4591122, -21.432033999999998, -19.6139674, -15.334164600000003, -3.2896528000000016, 7.512505999999999, 15.2633372, 19.818314400000002, 21.357378, 14.170296200000001, 7.976234999999998, 3.9261432, 1.9424932000000001, 0.8216950000000001, 0.49627100000000013, 0.7116252000000001},
			{-7.3167728, -7.9456066000000005, -9.2583092, -10.981620000000001, -12.665210199999999, -13.197374, -12.2761374, -9.832586799999998, -10.162317999999999, -12.5130266, -11.248659200000002, -11.3549006, -13.525190599999998, -12.1899954, -7.7010604, -7.447421199999999, -7.0951976000000005, -6.0457058, -5.8844296, -6.731967399999999},
			{3.1374690000000003, 3.7677384, 4.460221199999999, 4.6310686, 4.6707896, 4.861737, 4.077848400000001, 1.8075383999999999, 1.975036, 1.7845672000000001, 0.3215953999999999, 0.21726839999999994, 1.8061025999999998, 2.098984, 2.4775286000000003, 3.1948967999999995, 3.3547372, 3.3145378, 2.6679972000000003, 2.1722044000000005}
			};
	
	double[][] backward20 = {
			{-0.5771490000000001, 0.9667002, 4.6057049999999995, 8.4193856, 12.9389492, 16.6387316, 18.1270674, 15.189159600000002, 8.883115, 0.8685946000000009, -7.145447, -13.598411199999997, -16.8335074, -16.548283, -14.947005999999998, -12.380943, -9.8244512, -7.927899999999999, -6.290252199999999, -5.2709098},
			{-9.6827962, -10.233624, -10.740423799999999, -11.655917599999999, -12.4680414, -12.9480416, -13.0241334, -12.1220392, -10.8284794, -9.220024200000001, -7.171768, -5.1756752, -4.424329, -3.9151364, -4.16064, -5.381457999999999, -6.538627, -7.223931400000001, -7.8274012, -8.0528052},
			{0.2344964, -0.17276200000000003, -0.8767303999999999, -1.553899, -1.3074382, -1.1160124, -0.4833499999999999, 1.49025, 3.5959338, 4.6664826, 5.716931600000001, 6.8554366, 6.872186600000001, 6.6132832, 6.479284999999999, 6.385008, 5.7217176, 4.899543599999999, 4.258745800000001, 3.6748971999999993}
			};
	
	double[][] conDown20 = {
			{-5.7576098, -4.1965322, -1.4165509999999997, 2.260739, 6.145247, 9.583733200000001, 11.017034, 9.0989474, 5.010091999999998, -0.9102297999999998, -7.910671600000001, -13.500305, -16.8205858, -19.0085828, -19.4842758, -17.1048532, -16.877056800000002, -15.8098576, -12.579068400000002, -10.440842},
			{1.4438292000000001, 0.9585648000000001, 0.32015959999999993, 0.027278000000000014, 0.003828399999999954, -0.3024527999999999, -0.4479364, -0.28522440000000004, 0.33308099999999996, 0.618784, 1.8128024, 3.0072994, 4.1568114000000005, 4.156333, 3.7887954000000006, 2.6641688, 3.0924840000000002, 2.0750558, 1.6232908000000001, 1.0882556},
			{1.6922044000000003, 0.8977872000000001, -0.5838486000000002, -2.482314, -4.6937608, -6.3988862, -7.1717678, -6.523312800000001, -4.242952999999999, -0.4852643999999998, 3.5385059999999995, 7.8810004, 11.696117, 14.1860884, 15.677774199999998, 17.056518399999998, 17.154624000000002, 16.850735399999998, 14.5832966, 12.3010222}
			};
	
	double[][] conDownRight3 = {
			{-5.2981878, -3.616990800000001, -1.0691134000000002, 2.0348564, 5.4230934, 8.1863248, 9.3176514, 8.762038, 7.010491800000001, 3.6203405999999996, 0.22875399999999999, -2.1300906, -4.379344, -6.9913494, -8.482556599999999, -9.683753399999999, -10.7437738, -11.194581200000002, -10.6691174, -10.0019984},
			{1.7170894, 0.7652246, -0.251725, -1.1724831999999998, -1.9917858, -2.1028125999999996, -1.4639289999999998, 0.39290139999999985, 3.2958736, 7.9580494, 12.914542599999999, 17.2804866, 20.676859, 20.911834399999996, 18.528583, 12.8240934, 4.9517071999999995, -3.8251660000000003, -10.711231000000002, -16.2319516},
			{-1.7702102, -2.4899712000000003, -3.7337604, -5.2039105999999995, -6.7874806, -8.282037599999999, -8.652925, -6.9219572, -3.333201999999999, 2.6469402000000004, 9.695716999999998, 17.4656908, 23.3022632, 26.952274799999998, 27.7256352, 26.649343799999997, 23.495124999999998, 19.8465498, 15.8213438, 11.681760599999999}
			};
	
	double[][] semiCircleUp = {
			{-6.9731634, -6.404629, -5.893522, -5.904529, -6.4692352, -7.501499, -8.6294754, -9.553105200000001, -9.8335442, -9.3932648, -8.0772124, -6.4864638, -4.884229599999999, -3.0455846, -1.0566706, 0.9619145999999998, 3.0565914, 4.9201216, 6.7118674, 7.9441711999999995},
			{-0.06029920000000004, -1.8022742, -4.3214378, -8.0183486, -12.008141, -15.341342799999998, -17.048861, -15.6897378, -10.490613, -3.4624144, 5.0847482, 13.599367999999998, 20.1638376, 23.9243976, 25.6314374, 25.0140894, 22.564318, 19.4603484, 14.7766376, 9.513384799999999},
			{5.3862436, 4.3300516, 3.3777082, 2.6029124, 2.3894726, 3.425086399999999, 6.162954000000001, 10.117332600000001, 14.930255800000001, 19.897277, 23.7167, 25.2246572, 24.481924999999997, 21.9752042, 18.292171800000002, 13.951592, 9.298987400000001, 5.4451073999999995, 1.9295724, -1.3083954}
			};
	

	for (int j = 0; j<NUMBER_OF_AXIS ; j++){
		for (int i =0; i<sample.length; i++ )
		
		{
			acc[j][i] = sample[i][j];
		}
	}
	
	
	
	System.out.println("Down");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwDown[i] = new DTW(down15[i], acc[i]);
		System.out.println(dtwDown[i]);
		dtw[0][i] = dtwDown[i];
	}
	
	System.out.println("Right");
	for (int i = 0; i< NUMBER_OF_AXIS; i++ )
	{
		dtwRight[i] = new DTW(right15[i], acc[i]);
		System.out.println(dtwRight[i]);
		dtw[1][i] = dtwRight[i];
	}
	
	System.out.println("Pat");
		for (int i = 0; i< NUMBER_OF_AXIS; i++ )
		{
			dtwPat[i] = new DTW(pat15[i], acc[i]);
			System.out.println(dtwPat[i]);
			dtw[2][i] = dtwPat[i];
		}
	
	

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
