package data;

import java.util.Vector;

public class NodeConnections {
	
	private int startNodeId;
	Vector<Connection> connections = new Vector<Connection>();

	public Connection getConnectionAt(int indexMatrix) {
		return connections.get(indexMatrix);
	}
	
	public void setStartNodeId(int id){
		this.startNodeId = id;
	}
	
	public void setConnection(Connection toSet){
		connections.add(toSet);
	}
	
	public Vector<Connection> getConnections(){
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
