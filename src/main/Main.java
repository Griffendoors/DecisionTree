package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private int numCategories;
	private int numAtts;
	private List<String> categoryNames;
	private List<String> attNames;
	private List<Instance> allInstances;

	private Tree tree;

	private double BASELINE_ACCURACY;


	private List<Instance> testInstances;

	public Main(String t1, String t2){
		String trainFile = "assets/hepatitis-training-run10.dat";
		String testFile = "assets/hepatitis-test-run10.dat";
		BASELINE_ACCURACY = 0;
		readDataFile(trainFile);

		System.out.println("\n============");
		System.out.println("Calulating Baseline Accuracy");

		calcBaseLine();

		System.out.println("Baseline Accuracy: "+BASELINE_ACCURACY);
		System.out.println("\n============");
		System.out.println("Building Decision Tree\n");

		tree = new Tree(allInstances,attNames);

		System.out.println("\n============");
		System.out.println("Finished Building Decision Tree");
		System.out.println("============\n");

		readTests(testFile);

		System.out.println("============\n");
		System.out.println("Test Instance Classification:\n");

		testTree();
	}

	private void calcBaseLine(){
		int live = 0;
		int die = 0;
		int total = 0;
		for(Instance instance : allInstances){
			if(instance.getCategory()==0) live++;
			else die ++;
			total++;
		}
		BASELINE_ACCURACY = (1-(((live*1.0)/(total*1.0))*((die*1.0)/(total*1.0)))) *100;
	}


	private void testTree(){
		int totalTested = 0;
		int correct = 0;
		int incorrect = 0;
		for(Instance i : testInstances){

			int actual = i.getCategory();			//actual value recorded in data
			System.out.println("actual="+actual);

			int tested = tree.testTree(i);			// traverse the tree to find the predicted outcome
			System.out.println("tested="+tested);

			if(tested==404404) System.out.println("ERROR");

			else if(actual == tested){
				System.out.println("CORRECT");
				correct++;
			}
			else{
				System.out.println("INCORRECT");
				incorrect++;
			}

			totalTested++;
		}
		System.out.println("============\n");
		System.out.println("Classification Statistics:\n");
		System.out.println("Total tested: "+totalTested);
		System.out.println("Correct: "+correct);
		System.out.println("Incorrect: "+incorrect);

		double accuracy = ((1.0*correct)/(1.0*totalTested) * 100);
		System.out.println("\nAccuracy: "+accuracy+"%");
	}

	private void readTests(String fname){
		System.out.println("Reading Test Data\n");
		testInstances = new ArrayList<Instance>();
		try {
			Scanner din = new Scanner(new File(fname));

			categoryNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) categoryNames.add(s.next());
			numCategories=categoryNames.size();
			System.out.println(numCategories +" categories");

			attNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) attNames.add(s.next());
			numAtts = attNames.size();
			System.out.println(numAtts +" attributes");

			testInstances = readInstances(din);
			din.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}


	private void readDataFile(String fname){
		System.out.println("Reading Training Data\n");
		try {
			Scanner din = new Scanner(new File(fname));

			categoryNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) categoryNames.add(s.next());
			numCategories=categoryNames.size();
			System.out.println(numCategories +" categories");

			attNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) attNames.add(s.next());
			numAtts = attNames.size();
			System.out.println(numAtts +" attributes");

			allInstances = readInstances(din);
			din.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}

	private List<Instance> readInstances(Scanner din){
		/* instance = classname and space separated attribute values */
		List<Instance> instances = new ArrayList<Instance>();
		String ln;
		while (din.hasNext()){
			Scanner line = new Scanner(din.nextLine());
			instances.add(new Instance(categoryNames.indexOf(line.next()),line));
		}
		System.out.println("Read " + instances.size()+" instances");
		return instances;
	}




	public static void main(String args[]){
		new Main(args[0],args[1]);
	}

}
