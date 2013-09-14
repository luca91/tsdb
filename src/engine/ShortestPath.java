package engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import model.Node;
import model.NodeConnections;

public class ShortestPath {
	
	public static final double INFINITY = 9999.0;
	public static final int ARRAY_LENGTH = 11;
	private Vector<Integer[]> pathAlreadyDone = new Vector<Integer[]>(1,1);
	

	public ShortestPath() {	}
	
	public boolean checkNodes(int source, int destination){
		int[] aux = {source, destination};
		if(pathAlreadyDone.contains(aux))
			return true;
		return false;
	}
		
	public void calculatePath(NodeConnections[] connections, int source, int destination, String fileName) throws IOException{
		
//		File filePath = new File(fileName);
//		FileWriter fwPath = new FileWriter(filePath+"_resultingPath.txt");
		
//		System.out.println("##########################");
				
		//resulting path
		Vector<Integer> resultingPath = new Vector<Integer>();
		
		//visited nodes
		Vector<Integer> visited = new Vector<Integer>(1,1);
		
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
		double totalCost = INFINITY;
		int prev = source;
		int current = source;
		
		//adding source to the queue
		n = new Node(0, prev, source);
		q.add(n);
		
		for(int i = 0; i < ARRAY_LENGTH; i++){
			if(i != source){
				n = new Node(totalCost, -1, i);
				q.add(n);
			}
		}
		
//		System.out.println("Source: " + source);
//		System.out.println("Destination: " + destination);
		
		//all distances from the current source
		double[] distances = new double[ARRAY_LENGTH];
		int[] previous = new int[ARRAY_LENGTH];
		
		//set all the distances to infinity
		for(int i = 0; i < ARRAY_LENGTH; i++){
			distances[i] = INFINITY;
			previous[i] = -1;
		}
//		System.out.println("All distances to infinity.");
//		System.out.println("All previous to -1.");
		
		//distance of the source set to 0
		distances[source] = 0;
//		System.out.println("Source distance set to 0.");
		
		totalCost = 0;
		int count = 0;
		
		while(!q.isEmpty() && count < ARRAY_LENGTH){
			
			//extracting the next current node
			n = q.poll();
			visited.add(n.getID());
			current = n.getID();
			if(current == destination){
				break;
			}
			
//			System.out.println("Current node (removed): " + current);	
						
			//source and destination (to remember)
//			System.out.println("Source: " + source);
//			System.out.println("Destination: " + destination);
			
			//the connections for the current node
			NodeConnections conn = connections[current];
			if(conn == null)
				break;

			//set the distance of the current node's siblings
			for(int i = 0; i < conn.getConnections().size(); i++){
				if(!visited.contains(conn.getConnectionAt(i).getEnd())){
//					System.out.println("Number of siblings: " + conn.getConnections().size());
//					System.out.println("Sibling nr. " + i);
					double dist = n.getTotalCost() + conn.getConnectionAt(i).getDistance();
//					System.out.println("Distance to sibling "+ (int) conn.getConnectionAt(i).getEnd() + ": " + dist);
					
					Node sibling = new Node(dist,
							current,
							(int) conn.getConnectionAt(i).getEnd());
					
					//updating the distance of the sibling
					if(dist < distances[(int) conn.getConnectionAt(i).getEnd()]){
						distances[(int) conn.getConnectionAt(i).getEnd()] = dist;
						previous[(int) conn.getConnectionAt(i).getEnd()] = current;
//						System.out.println("Previous node of " + (int) conn.getConnectionAt(i).getEnd() + " is " + current);
					}
					else {
						dist = distances[(int) conn.getConnectionAt(i).getEnd()];
//						System.out.println("Previous node of " + (int) conn.getConnectionAt(i).getEnd() + " is " + previous[(int) conn.getConnectionAt(i).getEnd()]);
					}
					
//					System.out.println("Visited contains the sibling: " + visited.contains(sibling));
					
					//if the sibling have been remove, that is it has been not visited yet, then it is added with the update distance
					if(q.contains(sibling)){
						q.remove(sibling);
						sibling.setTotalCost(dist);
						q.add(sibling);
					}
					else{
						sibling.setTotalCost(dist);
						q.add(sibling);
					}
					
//					System.out.println("New Distance to sibling "+ (int) conn.getConnectionAt(i).getEnd() + ": " + sibling.getTotalCost());
				}
				else
					continue;
			}
			
			//adding the path to the list
			resultingPath.add(n.getID());
//			System.out.println("Node select as next: " + q.peek().getID());
//			System.out.println("##########################");
			count++;
		}
		
//		System.out.println("Visited nodes: " + visited.size());
		
		int cur = destination;
//		System.out.println("Current Dest: "+cur+"\n");
//
//		System.out.println("Finding the path...");
		
			
		//the file where to save the path
		File aFile = new File(fileName+".txt");
		FileWriter fw = new FileWriter(aFile);
	
		//building the final path
		while(cur != source){
				//adding the path to the list
	//			System.out.println("Current: " + cur);
//					resultingPath.add((int) cur);
				fw.append(String.valueOf(cur)+"\n");	
//				System.out.println("Current: "+cur+"\n");
//				System.out.println("Previous: "+previous[cur]+"\n");
				if (previous[cur] == -1)
					break;
				cur = (int) previous[cur];
		}
		fw.append(String.valueOf(source));
		fw.flush();
		fw.close();
		
//		for (int i=0; i<resultingPath.size(); i++)
//		{
//			fwPath.write((resultingPath.toArray().toString()));
//		}
//		fwPath.close();
		resultingPath.clear();
//		System.out.println("Path founded!");
		
		pathAlreadyDone.add(resultingPath.toArray(new Integer[resultingPath.size()]));
//		System.out.println("##########################\n");
		
		//return resultingPath;		
	}
	
	public int generateRandomNumbers(){
		Random rm = new Random();
		int number = rm.nextInt(ARRAY_LENGTH);
//		System.out.printf("Random number: %d\n", number);
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
}
