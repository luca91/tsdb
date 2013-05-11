package data;

import java.util.Vector;

public class NodeConnections {
	
	private int startNodeId;
	Vector<double[]> connections = new Vector<double[]>(1,1); 

	public double[] getConnections(int indexMatrix) {
		// TODO Auto-generated method stub
		return connections.get(indexMatrix);
	}
	
	public void setStartNodeId(int id){
		this.startNodeId = id;
	}
	
	public void setConnections(double[] toSet){
		connections.add(toSet);
	}
	
	public Vector<double[]> getListNode(){
		return connections;
	}
	
	public int getStartNodeId(){
		return this.startNodeId;
	}
}
