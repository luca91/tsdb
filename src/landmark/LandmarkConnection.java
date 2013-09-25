package landmark;

import java.util.Vector;

public class LandmarkConnection {
	
	private int from;
	private int to;
	private double distance;
	private Vector<Integer> nodesBetween;
	
	public LandmarkConnection(){}
	
	public LandmarkConnection(int from, int to, double distance, Vector<Integer> nodesBetween){
		this.from = from;
		this.to = to;
		this.distance = distance;
		this.nodesBetween = nodesBetween;
	}
	public LandmarkConnection(int from, int to, double distance){
		this.from = from;
		this.to = to;
		this.distance = distance;
	}
	
	public int getFrom(){
		return this.from;
	}
	
	public int getTo(){
		return this.to;
	}
	
	public double getDistance(){
		return this.distance;
	}
	
	public Vector<Integer> getNodesBetween(){
		return this.nodesBetween;
	}

}
