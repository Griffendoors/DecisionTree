package main;

import java.util.ArrayList;
import java.util.List;

public class Tree {

	private List<String> attNames;
	private List<Instance> allInstances;
	private Node root;

	private List<Instance> initialInstances;

	//TODO Everywhere - live = 0, die = 1 ?

	private List<String>allAttributes;


	public Tree(List<Instance> allInstances, List<String> attNames){
		this.allAttributes = clone(attNames);
		this.initialInstances = allInstances;
		this.allInstances = allInstances;
		this.attNames = attNames;
		root = buildTree(allInstances, attNames);
		printTree(root);
	}

	private void printTree(Node root){
		root.report("	");
	}

	public int testTree(Instance i){
		return root.test(i,allAttributes);
	}



	private Node buildTree(List<Instance> allInstances, List<String> attNames){
		if(allInstances.isEmpty()){
			return instancesEmpty(initialInstances,attNames);
		}
		if(calcPureOrNot(allInstances)){
			return instancesPure(allInstances,attNames);

		}
		if(attNames.isEmpty()){
			return attributesEmpty(allInstances, attNames);

		}
		return findBestAttribute(allInstances,attNames);

	}


	/**
	 * @param allInstances
	 * @param attNames
	 * @return leaf node containing name&prob of overall most probable class (baseline)
	 */
	private Node instancesEmpty(List<Instance> allInstances, List<String> attNames){
		double live = 0.0;
		double die = 0.0;
		double total = 0.0;
		for(Instance i : allInstances){
			if((i.getCategory())==0) live++;
			else die ++;
			total++;
		}
		double prob;
		if(total==0) prob = 0.0;
		else prob = (live/total)*(die/total);
		prob = 1-prob;
		int className;
		if(live>die) className = 0;
		else className = 1;
		return new LeafNode(className,prob);
	}


	private Node instancesPure(List<Instance> allInstances, List<String> attNames){

		int className = allInstances.get(0).getCategory();
		double prob = 1;

		return new LeafNode(className,prob);
	}


	private Node attributesEmpty(List<Instance> allInstances, List<String> attNames){
		double live = 0;
		double die = 0;
		double total = 0;
		for(Instance i : allInstances){
			if((i.getCategory())==0) live++;
			else die ++;
			total++;
		}
		double prob;
		if(total==0) prob = 0.0;
		else prob = (live/total)*(die/total);
		int className = -1;
		if(live>die) className = 0;
		else className = 1;

		return new LeafNode(className,prob);
	}

	private Node findBestAttribute(List<Instance> allInstances, List<String> attNames){
		int attIndex = 0;
		double bestWeightedAvgPure = 0.0;
		String bestAttribute = null;
		List<Instance> bestInstAttTrue = new ArrayList<Instance>();
		List<Instance> bestInstAttFalse = new ArrayList<Instance>();

		for(String attribute : attNames){
			List<Instance> instAttTrue = new ArrayList<Instance>();
			List<Instance> instAttFalse = new ArrayList<Instance>();
			for(Instance instance : allInstances){
				if(instance.getAtt(attIndex)) instAttTrue.add(instance);
				else instAttFalse.add(instance);
			}

			double weightedAvgPure = calcWeightAvgPure(instAttTrue,instAttFalse);
			if(weightedAvgPure>=bestWeightedAvgPure){
				bestAttribute = attribute;
				bestInstAttTrue = instAttTrue;
				bestInstAttFalse = instAttFalse;
			}
			attIndex++;
		}
		List<String> tempAtts = attNames;
		tempAtts.remove(bestAttribute);
		Node leftChild = buildTree(bestInstAttTrue,clone(tempAtts));
		Node rightChild = buildTree(bestInstAttFalse,clone(tempAtts));
		return new InternalNode(bestAttribute,leftChild,rightChild);

	}

	private double calcWeightAvgPure(List<Instance> I1,List<Instance> I2){
		double live1 = 0;
		double live2 = 0;
		double die1 = 0;
		double die2 = 0;
		double total1 = 0;
		double total2 = 0;
		double bothTotal = 0;
		for(Instance i : I1){
			if((i.getCategory())==0) live1++;
			else die1 ++;
			total1++;
			bothTotal++;
		}
		for(Instance i : I2){
			if((i.getCategory())==0) live2++;
			else die2 ++;
			total2++;
			bothTotal++;
		}
		if(total1==0 || total2==0) return 0;
		double weightedPurity = ((live1/total1 * die1/total1) * total1/bothTotal) + ((live2/total2 * die2/total2)*total2/bothTotal);	// WEIGHTED AVERAGE PURITY FORMULA
		return weightedPurity;

	}


	private boolean calcPureOrNot(List<Instance> allInstances){
		int firstClass = allInstances.get(0).getCategory();
		for(Instance i : allInstances){
			if(i.getCategory()!=firstClass) return false;
		}
		return true;
	}

	private double calcPurity(List<Instance> allInstances){
		double live = 0;
		double die = 0;
		double total = 0;
		for(Instance i : allInstances){
			if((i.getCategory())==0) live++;
			else die ++;
			total++;
		}
		double purity = (live/total)*(die/total);
		return purity;
	}

	private List<String> clone(List<String> list){
		List<String>temp = new ArrayList<String>();
		temp.addAll(list);
		return temp;

	}




}
