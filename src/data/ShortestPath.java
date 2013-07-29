package data;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import data.NodeConnections;

public class ShortestPath {
	
	public static final double INFINITY = 999.0;
	public static final int ARRAY_LENGTH = 21048;
	private Vector<int[]> pathAlreadyDone = new Vector<int[]>(1,1);
	

	public ShortestPath() {	}
	
	public boolean checkNodes(int source, int destination){
		int[] aux = {source, destination};
		if(pathAlreadyDone.contains(aux))
			return true;
		return false;
	}
		
	public Vector<Integer> calculatePath(NodeConnections[] connections, int source, int destination){
		
		//resulting path
		Vector<Integer> resultingPath = new Vector<Integer>();
		
		//visisted nodes
		Vector<Node> visited = new Vector<Node>(1,1);
		
		//nodes still to visit
		Vector<Node> toVisit = new Vector<Node>(1,1);
		
		//the queue with the nodes to visit, sorted by distances from the actual node
		PriorityQueue<Node> q = new PriorityQueue<Node>(1, new Comparator<Node>() {
			
			@Override
			public int compare(Node n1, Node n2){
				if(n1.getTotalCost() < n2.getTotalCost())
					return -1;
				else if(n1.getTotalCost() > n2.getTotalCost())
					return 1;
				return 0;
			}
		});
		
		Node n;
		int totalCost = 0;
		int prev = 0;
		
		//all distances from the current source
		double[] distances = new double[ARRAY_LENGTH];
		
		for(int i = 0; i < ARRAY_LENGTH; i++){
			n = new Node(totalCost, prev , i);
			distances[i] = INFINITY;
		}
		
		for(int i = 0; i < connections.getConnenctions().length(); i++){
			Node aNode = new Node(Array.getDouble(connections.getConnectionAt(i), i);
			q.add(aNode);
		}
		
		distances[source] = 0;
		return resultingPath;		
	}
	
	public void printPath(Vector<Integer> toPrint){
		System.out.printf("PATH: ");
		for(Integer i: toPrint.toArray(new Integer[toPrint.size()])){
			System.out.printf("%d", i);
			System.out.printf(" --> ");
		}
		System.out.printf("END\n\n");
	}
}
