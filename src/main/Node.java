package main;

import java.util.List;

public abstract class Node {

	public abstract void report(String indent);

	public abstract int test(Instance instance, List<String> attNames);




}
