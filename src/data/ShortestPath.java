package data;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import data.NodeConnections;

public class ShortestPath {
	
	public static final double INFINITY = 999.0;
	public static final int ARRAY_LENGTH = 21048;
	private Vector<Integer[]> pathAlreadyDone = new Vector<Integer[]>(1,1);
	

	public ShortestPath() {	}
	
	public boolean checkNodes(int source, int destination){
		int[] aux = {source, destination};
		if(pathAlreadyDone.contains(aux))
			return true;
		return false;
	}
		
	public Vector<Integer> calculatePath(NodeConnections[] connections, int source, int destination){
		
		
		System.out.println("##########################");
				
		//resulting path
		Vector<Integer> resultingPath = new Vector<Integer>();
		
		//visited nodes
		Vector<Node> visited = new Vector<Node>(1,1);
		
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
		
		//the instance containing the info of the current selected node
		Node n = null;
		double totalCost = 0;
		int prev = source;
		int current = source;
		
		//adding source to the queue
		n = new Node(totalCost, prev, source);
		q.add(n);
		
		System.out.println("Source: " + source);
		System.out.println("Destination: " + destination);
		
		//adding the current selected node to the visited nodes
		visited.add(n);
		
		//all distances from the current source
		double[] distances = new double[ARRAY_LENGTH];
		double[] previous = new double[ARRAY_LENGTH];
		
		//set all the distances to infinity
		for(int i = 0; i < ARRAY_LENGTH; i++){
			distances[i] = INFINITY;
			previous[i] = -1;
		}
		System.out.println("All distances to infinity.");
		System.out.println("All previous to -1.");
		
		//distance of the source set to 0
		distances[source] = 0;
		System.out.println("Source distance set to 0.");
		
		while(current != destination && !q.isEmpty()){
			
			//extracting the next current node
			n = q.poll();
			visited.add(n);
			current = n.getID();
			System.out.println("Next node: " + current);	
						
			//source and destination (to remember)
			System.out.println("Source: " + source);
			System.out.println("Destination: " + destination);
			
			//the connections for the current node
			NodeConnections conn = connections[current];
			
			//set the distance of the current node's siblings
			for(int i = 0; i < conn.getConnections().size(); i++){
//				System.out.println("Number of siblings: " + conn.getConnections().size());
//				double dist = totalCost + Array.getDouble(conn.getConnectionAt(i), 2);
//				System.out.println("Distance to sibling "+ (int) Array.getDouble(conn.getConnectionAt(i), 1) + ": " + dist);
//				Node sibling = new Node(dist,
//									current,
//									(int) Array.getDouble(conn.getConnectionAt(i), 1));
//				
//				if(visited.contains(sibling))
//					continue;
//				
//				//if the sibling is already in the queue is removed and reinserted to update the distance
//				if(q.contains(sibling))
//					q.remove(sibling);
//					
//				if(dist < distances[(int) Array.getDouble(conn.getConnectionAt(i), 1)]){
//					distances[(int) Array.getDouble(conn.getConnectionAt(i), 1)] = dist;
//					previous[(int) Array.getDouble(conn.getConnectionAt(i), 1)] = current;
//				}
//				else
//					dist = distances[(int) Array.getDouble(conn.getConnectionAt(i), 1)];
//				
//				//setting the updated sibling and inserting it to the queue
//				sibling.setTotalCost(dist);
//				if(!visited.contains(sibling) && previous[n.getID()] != sibling.getID())
//					q.add(sibling);
//				System.out.println("New Distance to sibling "+ (int) Array.getDouble(conn.getConnectionAt(i), 1) + ": " + dist);
			}
			
			//adding the path to the list
			resultingPath.add(n.getID());
			System.out.println("##########################");
		}
		
		pathAlreadyDone.add(resultingPath.toArray(new Integer[resultingPath.size()]));
		System.out.println("##########################\n");
		
		return resultingPath;		
	}
	
	public int generateRandomNumbers(){
		Random rm = new Random();
		int number = rm.nextInt(21048);
		System.out.printf("Random number: %d\n", number);
		return number;		
	}
	
	public void printPath(Vector<Integer> toPrint){
		System.out.printf("PATH: ");
		for(Integer i: toPrint.toArray(new Integer[toPrint.size()])){
			System.out.printf("%d", i);
			System.out.printf(" --> ");
		}
		System.out.printf("END\n\n");
	}
	
	public PriorityQueue<Node> updateQueue(PriorityQueue<Node> q, Node n){
		q.remove(n);
		q.add(n);
		return q;
	}

}
