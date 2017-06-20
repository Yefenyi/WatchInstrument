package Util;

import Util.Model.EuclideanModel;
import Util.Model.Model;
import Util.Model.UserModel;

public class ModelMap {
     static public Model getModelByName(String modelName){
    	 switch(modelName){
    	     case "Euclidean":
    	    	 return new EuclideanModel();
    	     case "UserModel":
    	    	 return new UserModel("src/UserModel/myMlPerceptron.nnet");
    	 default:
    		 return null;
    	 }
     }
}
