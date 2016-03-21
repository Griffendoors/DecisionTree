package main;

import java.util.List;

public class LeafNode extends Node{

	private int className;
	private double probability;


	public LeafNode(int className, double probability){
		this.className = className;
		this.probability = probability;
	}




	public int getClassName() {
		return className;
	}

	public double getProbability() {
		return probability;
	}


	@Override
	public void report(String indent){
//		if (count==0){
//			System.out.format("%sUnknown\n", indent);
//		}
//		else{
			System.out.format("%sClass %s, prob=%4.2f\n",indent, className, probability);
	//	}
	}




	@Override
	public int test(Instance instance ,List<String> attNames) {
		return className;

	}

}
