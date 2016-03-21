package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {

    private int category;
    private List<Boolean> vals;

    public Instance(int cat, Scanner s){
      category = cat;
      vals = new ArrayList<Boolean>();
      while (s.hasNextBoolean()) vals.add(s.nextBoolean());
    }

    public boolean getAtt(int index){
      return vals.get(index);
    }

    public int getCategory(){
      return category;
    }


    public List<Boolean> getVals(){
    	return vals;
    }


//    public String toString(){
//      StringBuilder ans = new StringBuilder(categoryNames.get(category));
//      ans.append(" ");
//      for (Boolean val : vals)
//	ans.append(val?"true  ":"false ");
//      return ans.toString();
//    }

  }