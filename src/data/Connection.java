package data;

public class Connection {
	
	private int edgeId;
	private int endNode;
	private double distance;
	
	public Connection(){ }
	
	public Connection(int edge, int end, double dist){
		edgeId = edge;
		endNode = end;
		distance = dist;
	}
	
	public void setEdge(int id){
		edgeId = id;
	}
	
	public int getEdge(){
		return edgeId;
	}
	
	public void setEnd(int end){
		endNode = end;
	}
	
	public int getEnd(){
		return endNode;
	}
	
	public void setDistance(double dist){
		distance = dist;
	}
	
	public double getDistance(){
		return distance;
	}

}
