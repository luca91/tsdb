package data;

import java.util.Vector;

public class NodeConnections {
	
	private int startNodeId;
	Vector<double[]> connections = new Vector<double[]>(1,1); 

	public double[] getConnectionAt(int indexMatrix) {
		return connections.get(indexMatrix);
	}
	
	public void setStartNodeId(int id){
		this.startNodeId = id;
	}
	
	public void setConnections(double[] toSet){
		connections.add(toSet);
	}
	
	public Vector<double[]> getConnections(){
		return connections;
	}
	
	public int getStartNodeId(){
		return this.startNodeId;
	}
}
