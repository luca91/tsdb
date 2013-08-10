package data;

import java.util.Vector;

public class NodeConnections {
	
	private int startNodeId;
	//Vector<double[]> connections = new Vector<double[]>(1,1);
	Vector<Road> connections = new Vector<Road>();

//	public double[] getConnectionAt(int indexMatrix) {
//		return connections.get(indexMatrix);
//	}
	
	public void setStartNodeId(int id){
		this.startNodeId = id;
	}
	
	public void setConnection(Road toSet){
		connections.add(toSet);
	}
	
	public Vector<Road> getConnections(){
		return connections;
	}
	
	public int getStartNodeId(){
		return this.startNodeId;
	}

//	public int getEndNodeId() {
//		return endNodeId;
//	}
//
//	public void setEndNodeId(int endNodeId) {
//		this.endNodeId = endNodeId;
//	}
}
