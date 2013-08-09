package data;

public class Node {
	
	//total cost till now
	private double totalCost = 0;
	
	//previous node
	private int previous;
	
	//this node is
	private int id;
	
	public Node(double totalCost, int previous, int id){
		this.totalCost = totalCost;
		this.previous = previous;
		this.id = id;
	}
	
	public double getTotalCost(){
		return this.totalCost;
	}
	
	public int getPrevious(){
		return this.previous;
	}
	
	public int getID(){
		return this.id;
	}
	
	public void setTotalCost(double cost){
		this.totalCost = cost;
	}

}
