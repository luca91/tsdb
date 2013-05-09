package tsdb_code;

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
		// TODO Auto-generated method stub
		return endNodeId;
	}

	public int getStartNode() {
		// TODO Auto-generated method stub
		return startNodeId;
	}

	public double getDistance() {
		// TODO Auto-generated method stub
		return distance;
	}

	public double getEdgeId() {
		// TODO Auto-generated method stub
		return edgeId;
	}

}
