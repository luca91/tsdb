package data;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Vector;

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
	
	public int generateRandomNumbers(){
		Random rm = new Random();
		int number = rm.nextInt(21048);
		System.out.printf("Random number: %d\n", number);
		return number;		
	}
	
	public Vector<Integer> calculatePath(NodeConnections[] connections){
		
		//method start
		System.out.printf("####################\n");
		System.out.printf("CALCULATING THE PATH\n");
		System.out.printf("####################\n");
		
		//the path to return
		Vector<Integer> resultingPath = new Vector<Integer>(1,1);
		
		//generating random source and destination
		int source = generateRandomNumbers();
		int destination = generateRandomNumbers();
		
		//checking if the path exist already
		boolean exist = false;
		if(checkNodes(source, destination)){
			System.out.printf("PATH ALREADY EXISTING\n");
			exist = true;
		}
		else
			System.out.printf("PATH NOT YET EXISTING\n");
		
		//printing the value of the source and the destination
		System.out.printf("Source: \t%d\n", source);
		System.out.printf("Destination: %d\n", destination);
		
		
		//the distance of each node from the source (the source has 0)
		double[] distances = new double[ARRAY_LENGTH];
		for(int i = 0; i < ARRAY_LENGTH; i++)
			distances[i]= INFINITY;
		distances[source] = 0;
		
		//all node are unvisited, except the source
		boolean[] visited = new boolean[ARRAY_LENGTH];
		for(int i = 0; i < ARRAY_LENGTH; i++)
			visited[i] = false;
		
		//the next node to visit
		int nextSource = -1;
		int tmpSource = source;
		
		//the total cost of the path
		double totalPathCost = 0;
		
		//adding the source to the resulting path
		resultingPath.add(source);
		
		//auxiliary counter for the steps
		int counter = 0;
		
		//loop for searching the path
		while(!visited[tmpSource] && tmpSource != destination && !exist){
			System.out.printf("####################\n");
			
			//storing temporarily the neighbors of the current node
			NodeConnections currentConnection = connections[tmpSource];
			int[] currentNeighbors = new int[currentConnection.getListNode().size()];
			for(int i = 0; i < currentNeighbors.length; i++){
				currentNeighbors[i] = (int) Array.getDouble(currentConnection.getListNode().get(i), 1);
				System.out.printf("Neighbor nr. %d: %d\n", i, currentNeighbors[i]);
			}
			
			//calculate the distance from the source to each of the neighbor
			double tmpDistance = INFINITY;
			for(int i = 0; i < currentNeighbors.length; i++){
				double currentNeighborDistance = distances[currentNeighbors[i]];
				
				System.out.printf("Current neighbor distance (ID = %d): %f\n", currentNeighbors[i], currentNeighborDistance);
				
				//updating the distances of the neighbors
				if(currentNeighborDistance > (Array.getDouble(currentConnection.getListNode().get(i), 2) + totalPathCost)){
					distances[currentNeighbors[i]] = (Array.getDouble(currentConnection.getListNode().get(i), 2) + totalPathCost);
					System.out.printf("New neighbor distance (ID = %d): %f\n", currentNeighbors[i], distances[currentNeighbors[i]]);
				}
				
				//finding the neighbor with smallest distance
				if(distances[currentNeighbors[i]] < tmpDistance && !visited[currentNeighbors[i]]){
					nextSource = currentNeighbors[i];
					tmpDistance = distances[currentNeighbors[i]];
					System.out.printf("Next node to visit (temporarily): %d\n", nextSource);
					System.out.printf("Distance to the next node (temporarily): %f\n", tmpDistance);
				}
			}
			
			//marking the current node as visited
			visited[tmpSource] = true;
			
			//updating the total cost
			totalPathCost = tmpDistance;
			System.out.printf("Total cost (temporarily): %f\n", totalPathCost);
					
			//adding the visited node to the final path
			if(tmpSource != source)
				resultingPath.add(tmpSource);
			
			//setting the next node to visit
			tmpSource = nextSource;
			
			counter++;
			System.out.printf("####################\n");
		}
		
		System.out.printf("Steps for the calculation (visited nodes): %d\n\n\n", counter+1);
		
		//add the path as already done to avoid duplicates
		int[] pathDone = {source, destination};
		pathAlreadyDone.add(pathDone);
		
		//print the path just found
		printPath(resultingPath);
		
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
