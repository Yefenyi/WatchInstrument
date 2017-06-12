package Util.Model;

import java.util.ArrayList;

public abstract class Model {
     public abstract int update(ArrayList<Double> newdata);
     protected abstract double process(); 
     public abstract ArrayList<Integer> getOutPutArray();
} 
