package Util.segmentation;

import java.util.ArrayList;

public class ChannelSegmentation extends Segmentation{
      private int mean_lag = 10;
      private int std_lag =5;
      private int avg_lag = 5;
      private double influence = 0.5;
      private double amplitude = 0.01;
      private int loc = 0;
      private int flag = 0;
      private int length_counter = 0;
      private int max_length = 80;
      private double mean = 0;
      private double std = 0;
      private double upperbound, lowerbound;
      
      private static int AXIS_NUM = 3;
           
      private ArrayList<Double> history_x = new ArrayList<Double>();
      private ArrayList<Double> history_y = new ArrayList<Double>();
      private ArrayList<Double> history_z = new ArrayList<Double>();
      private ArrayList<Double> history_dist = new ArrayList<Double>();
      private ArrayList<Double> filtered_dist = new ArrayList<Double>();
      
      ArrayList<Double> data = new ArrayList<Double>();
      
      public double getUpperBound(){
    	  return lowerbound;
      }
      
      public double getLowerBound(){
    	  return upperbound;
      }
      
      @Override
      public double[][] getBuffSegment(){
  		double[][] buffSegment = new double[length_counter][AXIS_NUM];
  		for(int i = 1 ;i<=length_counter;i++){
  			buffSegment[i][0] = history_x.get(history_x.size()-length_counter-1+i);
  			buffSegment[i][1] = history_y.get(history_x.size()-length_counter-1+i);
  			buffSegment[i][2] = history_z.get(history_x.size()-length_counter-1+i);  			
  		}
  		return buffSegment;
  	}
      
      int reference = 0;
      
      public double update(double[] newdata){    	  
 
    	  double current_y = 0;
    	  
    	  this.history_x.add(newdata[0]);
    	  this.history_y.add(newdata[1]);
    	  this.history_z.add(newdata[2]);
    	  if(this.history_x.size()>max_length){
    		  this.history_x.remove(0);
    		  this.history_y.remove(0);
    		  this.history_z.remove(0);
    	  }
    	 
    	  this.history_dist.add(getEuclideanDist(this.history_x.size()-1));
    	      	  
    	  if(history_dist.size()>this.max_length){
    		  history_dist.remove(0);
    	  }
    	      	 
    	 
    	      	  
    	  if(history_dist.size()>2){  
    		  current_y = 0.5*influence*history_dist.get(history_dist.size()-3)+0.5*influence*history_dist.get(history_dist.size()-2)+(1-influence)*history_dist.get(history_dist.size()-1);
    		  filtered_dist.add(current_y);
    		  if(filtered_dist.
    				  size()>max_length){
    			  filtered_dist.remove(0);
    		  }
    	  }
    	  
    	  if(filtered_dist.size()>= Math.max(mean_lag, std_lag)){
    		  getMean();
    		  getSTD();
    		  upperbound = this.mean + 0.2 + 10*amplitude*this.std;
    		  lowerbound = this.mean - 0.3 - 15*amplitude*this.std;
    	  }
    	  
    	  int current_loc = 0;
    	  
    	  if(current_y<lowerbound){
    		  current_loc = -1;
    	  }else if((current_y>=lowerbound)&&(current_y<= upperbound)){
    		  current_loc = 0;
    	  }else{
    		  current_loc = 1;
    	  }
    	  
    	  int tmp_flag=0;    	  
    	  
    	  if((this.loc==-1)&&(current_loc==0)){
    		  if((length_counter>=8)&&(length_counter<=40)){
    			  System.out.println("refernce:"+reference+", length:"+(length_counter+5));
    		  }
    		  length_counter = 0;
    		  tmp_flag = 0;
    	  }
    	  
    	  if((this.loc==0||(this.loc==-1))&&(current_loc==1)){
    		  length_counter = 0;
    		  tmp_flag = 1;
    	  }
    	  
    	  if((this.loc==1)&&(current_loc==0)){
    		  tmp_flag = 2;
    	  }
    	  
    	  if((this.loc>=0)&&(current_loc==-1)){
    		  tmp_flag = 3;
    	  }
    	      	  
    	  this.loc = current_loc;

    	  length_counter++;
    	  
    	  reference++;
    	  
    	  return current_y;

      }
      
      private void DataProcess(){
    	  if ( this.length_counter <= 10 ){
    		  return;
    	  }
    	  else{
    		  System.out.println(this.length_counter);
    	  }
      }
      
      private double getEuclideanDist(int index){
    	  if (this.history_x.size()<=1)
    		  return 0 ;
    	  double x_dist = this.history_x.get(index) - this.history_x.get(index-1);
    	  double y_dist = this.history_y.get(index) - this.history_y.get(index-1);
    	  double z_dist = this.history_z.get(index) - this.history_z.get(index-1);
    	  
    	  double dist = Math.sqrt( Math.pow(x_dist, 2) + Math.pow(y_dist, 2) + Math.pow(z_dist, 2));
    	  return dist;
      }
      
      private double getMean(){
    	  double sum = 0;
    	  for(int i = filtered_dist.size()-1; i>=filtered_dist.size()-mean_lag; i--){
    		  sum+=filtered_dist.get(i);
    	  }
    	  this.mean = sum/mean_lag;
    	  return this.mean;
      }
      
      private double getSTD(){
    	  double sum = 0;
    	  this.std = 0;
    	  for(int i = filtered_dist.size()-1; i<filtered_dist.size()-std_lag; i--){
    		  sum += filtered_dist.get(i);
    	  }
    	  double mean = sum/std_lag;
    	  for(int i = 0; i<filtered_dist.size()-std_lag; i++){
    		  this.std += Math.pow(filtered_dist.get(i) - mean, 2);
    	  }
    	  
    	  this.std = Math.sqrt(this.std/this.std_lag);
    	  return this.std;
      } 
}
