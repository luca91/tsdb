package tsdb_code;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Vector;

public class ShortestPath {
	
	public static final int INFINITY = 999;
	

	public ShortestPath() {	}
	
	public int generateRandomNumbers(){
		Random rm = new Random();
		int number = rm.nextInt(21048);
		System.out.printf("Random number: %d\n", number);
		return number;		
	}
	
	public Vector<Integer> calculatePath(NodeConnections[] connections){
		int source = generateRandomNumbers(); 
		int destination = generateRandomNumbers();
		System.out.printf("Source: %d\n", source);
		System.out.printf("Destination: %d\n", destination);
		Vector<Integer> path = new Vector<Integer>();
		int tmpSource = source;
		int toGo = 0;
		double tmpDistance = INFINITY;
		int totalCost = 0;
		int toExclude = -1;
		int noToGo = -1; 
		System.out.printf("Starting outer while...\n");
		while(toGo != destination) {
			
			NodeConnections aConn = new NodeConnections();
			aConn = connections[tmpSource];
			int j = 0;
			
			System.out.printf("Starting inner while...\n");
			
			//searching nearest sibling
			while(j < aConn.getListNode().size() && (int) Array.getDouble(aConn.getConnections(j), 1) != noToGo){
//				System.out.printf("Inner while\n");
				System.out.printf("NoToGo: %d\n", noToGo);
				System.out.printf("tmpDistance: %f\n", tmpDistance);
				System.out.printf("Sibling distance: %f\n", Array.getDouble(aConn.getConnections(j), 2));
				 if(tmpDistance > Array.getDouble(aConn.getConnections(j), 2)){
					 toGo = (int) Array.getDouble(aConn.getConnections(j), 1);
					 System.out.printf("ToGo: %d\n", toGo);
					 tmpDistance = Array.getDouble(aConn.getConnections(j), 2);
				 }
				 j++;
				 System.out.printf("Variable j: %d\n", j);
			}
			System.out.printf("Nearest sibling: %d\n", toGo);
			totalCost += tmpDistance;
			path.add(tmpSource);
			noToGo = tmpSource;
			tmpSource = toGo;
			tmpDistance = INFINITY;
		}
		System.out.printf("Path: ");
		
		//printing the current path found
		for(int i = 0; i < path.size(); i++){
			System.out.printf("%d --> ", path.get(i));
		}
		
		System.out.println();
		System.out.printf("Total path cost: %d", totalCost);
		
		return path;
	}
	
}
