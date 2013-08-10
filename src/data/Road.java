package data;

public class Road {

	int edgeId;
	int startNodeId;
	int endNodeId;
	double distance;
	
	public Road(){}
	
	
	public Road(int edgeId, int startNodeId, int endNodeId, double distance) {
		this.edgeId = edgeId;
		this.startNodeId = startNodeId;
		this.endNodeId = endNodeId;
		this.distance = distance;
	}
	
	public void printRoad() {
		System.out.println("Edge id\tStart node\tEnd node\tDistance");
		System.out.println(edgeId+"\t"+startNodeId+"\t\t"+endNodeId+"\t\t"+distance);
	}

	public int getEndNode() {
		return endNodeId;
	}

	public int getStartNode() {
		return startNodeId;
	}

	public double getDistance() {
		return distance;
	}

	public int getEdgeId() {
		return edgeId;
	}

}
