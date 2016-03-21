package main;

import java.util.List;

public class InternalNode extends Node {

	private Node leftChild;
	private Node rightChild;
	private String bestAttribute;


	public InternalNode(String bestAttribute, Node leftChild, Node rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.bestAttribute = bestAttribute;
	}


	public String getBestAttribute() {
		return bestAttribute;
	}


	public Node getLeftChild() {
		return leftChild;
	}


	public Node getRightChild() {
		return rightChild;
	}


	public void report(String indent){
		System.out.format("%s%s = True:\n",indent, bestAttribute);
		leftChild.report(indent+"    ");
		System.out.format("%s%s = False:\n",indent, bestAttribute);
		rightChild.report(indent+"    ");
	}

	public int test(Instance instance, List<String> attNames){
		int index = attNames.indexOf(bestAttribute);
		if(instance.getAtt(index)) return leftChild.test(instance,attNames);
		else return rightChild.test(instance,attNames);

	}




}
