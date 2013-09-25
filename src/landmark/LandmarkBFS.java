package landmark;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

import model.Node;
import model.NodeConnections;
import engine.ShortestPath;

public class LandmarkBFS {
	
	public static final double INFINITY = 9999.0;
	public static final int ARRAY_LENGTH = 11;
	private Vector<LandmarkConnection> subgraphSrc;
	private Vector<LandmarkConnection> subgraphDest;

	public void landmarkBFS(NodeConnections[] connections, List<Landmark> landmarkList) throws Exception{
		LandmarkConnection lc;
		int source = 5;
		int destination = 8;
		System.out.println("###############################################################");
		System.out.println("LANDMARK BREADTH FIRST SEARCH (DIJKSTRA'S ALGORITHM)");
		System.out.println("###############################################################");
		ShortestPath sp = new ShortestPath();
		subgraphSrc = new Vector<LandmarkConnection>();
		subgraphDest = new Vector<LandmarkConnection>();
		System.out.println("landmarklist size: "+landmarkList.size());
		for (int i=0; i<landmarkList.size(); i++){
			lc = null;
			int curLandmark = landmarkList.get(i).landmarkID;
			System.out.println("Current ath size: "+landmarkList.get(i).path.size());
			System.out.println("Creation of new files");
			sp.calculatePath(connections, source, curLandmark, "", false);
			lc = new LandmarkConnection(source, curLandmark, sp.getDistance());
			subgraphSrc.add(lc);
			lc = null;
			sp.calculatePath(connections, destination, curLandmark, "", false);
			lc = new LandmarkConnection(destination, curLandmark, sp.getDistance());
			subgraphDest.add(lc);
		}
		LandmarkConnection srcLand = minDistance(subgraphSrc);
		LandmarkConnection destLand = minDistance(subgraphDest);
		double landDist = 0;
		if (srcLand.getTo() != destLand.getTo()) {
			sp.calculatePath(connections, srcLand.getTo(), destLand.getTo(), "", false);
			landDist = sp.getDistance();
		}
		double totalCost = srcLand.getDistance() + destLand.getDistance() + landDist;
		System.out.println("The total cost of the path ("+source+","+destination+") is " +totalCost);
	}
	
	
	public Vector<Integer> getLandmarksList(List<Landmark> landmarks){
		Vector<Integer> result = new Vector<Integer>();
		for(int i = 0; i < landmarks.size(); i++)
			result.add(landmarks.get(i).getID());
		return result;
	}
	
	public LandmarkConnection calculatePathBetweenLandmarks(NodeConnections[] connections, int sourceLandmark, int destinationLandmark, List<Landmark> landmarks) throws IOException{
		
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
				int prev = sourceLandmark;
				int current = sourceLandmark;
				
				//adding source to the queue
				n = new Node(0, prev, sourceLandmark);
				q.add(n);
				
				for(int i = 0; i < ARRAY_LENGTH; i++){
					if(i != sourceLandmark){
						n = new Node(totalCost, -1, i);
						q.add(n);
					}
				}
				
//				System.out.println("Source: " + source);
//				System.out.println("Destination: " + destination);
				
				//all distances from the current source
				double[] distances = new double[ARRAY_LENGTH];
				int[] previous = new int[ARRAY_LENGTH];
				
				//set all the distances to infinity
				for(int i = 0; i < ARRAY_LENGTH; i++){
					distances[i] = INFINITY;
					previous[i] = -1;
				}
//				System.out.println("All distances to infinity.");
//				System.out.println("All previous to -1.");
				
				//distance of the source set to 0
				distances[sourceLandmark] = 0;
//				System.out.println("Source distance set to 0.");
				
				totalCost = 0;
				int count = 0;
				
				while(!q.isEmpty() && count < ARRAY_LENGTH){
					
					//extracting the next current node
					n = q.poll();
					visited.add(n.getID());
					current = n.getID();
					if(current == destinationLandmark){
						break;
					}

					//the connections for the current node
					NodeConnections conn = connections[current];
					if(conn == null)
						break;

					//set the distance of the current node's siblings
					for(int i = 0; i < conn.getConnections().size(); i++){
						if(!visited.contains(conn.getConnectionAt(i).getEnd())){
							double dist = n.getTotalCost() + conn.getConnectionAt(i).getDistance();

							
							Node sibling = new Node(dist,
									current,
									(int) conn.getConnectionAt(i).getEnd());
							
							if(getLandmarksList(landmarks).contains((int) conn.getConnectionAt(i).getEnd())){
								destinationLandmark = (int) conn.getConnectionAt(i).getEnd();
								count = ARRAY_LENGTH;
								break;
							}								
							
							//updating the distance of the sibling
							if(dist < distances[(int) conn.getConnectionAt(i).getEnd()]){
								distances[(int) conn.getConnectionAt(i).getEnd()] = dist;
								previous[(int) conn.getConnectionAt(i).getEnd()] = current;

							}
							else {
								dist = distances[(int) conn.getConnectionAt(i).getEnd()];
							}
							
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
						}
						else
							continue;
					}
					
					count++;
				}
				
				int cur = -1;
				Vector<Integer> nodesBetween = new Vector<Integer>();
				nodesBetween.add(destinationLandmark);
				while(cur != sourceLandmark){
					cur = previous[destinationLandmark];
					nodesBetween.add(cur);
					cur = previous[cur];
				}
				nodesBetween.add(sourceLandmark);
				
				LandmarkConnection aLND = new LandmarkConnection(sourceLandmark, destinationLandmark, distances[destinationLandmark], nodesBetween);
				return aLND;
	}
	
	public void obtainFinalPath(int source, int destination){
		
	}
	
	public LandmarkConnection getLandmarkConnection(Vector<LandmarkConnection> conn ){
		return null;
	}
	
	public LandmarkConnection minDistance(Vector<LandmarkConnection> subgraph) {
		LandmarkConnection lc = null;
		double minDist = 0, tmp;
		for (int i=0; i<subgraph.size();i++) {
			tmp = subgraph.get(i).getDistance();
			if (tmp < minDist) {
				minDist = tmp;
				lc = subgraph.get(i);
			}
		}
		return lc;
	}
}
