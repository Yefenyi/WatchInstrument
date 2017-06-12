package Util;

import Util.Model.EuclideanModel;
import Util.Model.Model;

public class ModelMap {
     static public Model getModelByName(String modelName){
    	 switch(modelName){
    	     case "Euclidean":
    	    	 return new EuclideanModel();
    	 default:
    		 return null;
    	 }
     }
}
