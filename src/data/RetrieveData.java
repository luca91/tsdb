package data;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class RetrieveData {

	Vector<Road> roads = new Vector<Road>();
	private NodeConnections[] allConnections;
	
	
	public RetrieveData() {
		allConnections = new NodeConnections[21048];
	}
	
	public void getDataFromDB(Connection conn) {			
	    	try {
	    		Statement stmt = conn.createStatement();            
	            ResultSet rs = stmt.executeQuery("SELECT * FROM roads");
	            while (rs.next()){
	            	Road aRoad = new Road(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4));
	            	roads.add(aRoad);
	            	aRoad.printRoad();
	            }
	        } catch (SQLException e) {
	        	System.out.println(e);
	        	}
	}
	
	public void manageData(){
		int currentStart = 0, currentEnd = 0;
		int old = -1;
		Road currentRoad;
		boolean contained = false;
		data.Connection connectionsData; 
		NodeConnections aConn;
		Vector<Integer> nodeChecked = new Vector<Integer>();
		System.out.println("Number of node to check is: "+roads.size());
		
		//reading all the nodes in the query result
		for(int i = 0; i < roads.size(); i++){
			System.out.printf("Iteration nr: %d \n", i);
			currentRoad = roads.get(i);
			
			//setting the current start and end nodes
			currentStart = currentRoad.getStartNode();
			currentEnd = currentRoad.getEndNode();
			System.out.println("Current start node: " + currentStart);
			System.out.println("Current end node: " + currentEnd);
			
			//checking if the current start node has already been met
			if(!nodeChecked.contains(currentStart)){
			
				//adding the start as already met			
				nodeChecked.add(currentStart);
				
				//setting the connections for the new node
				aConn = new NodeConnections();
				aConn.setStartNodeId(currentStart);
				
				System.out.println("Current end has not met yet.");
				
			}
			
			//in case has not been met yet
			else {
				
				//getting the already existing connection (start node)
				aConn = allConnections[currentStart];
				
				System.out.println("Current end already met. Old connections retrieved.");
	
			}
				
			//setting the connection (start node)
			connectionsData = new data.Connection(currentRoad.getEdgeId(), currentEnd, currentRoad.getDistance());
			aConn.setConnection(connectionsData);
			allConnections[currentStart] = aConn;
			
			System.out.println("Connection set (start).");
			
			//the same thing for the current end
			if(!nodeChecked.contains(currentEnd)){
				
				//adding the end as already met
				nodeChecked.add(currentEnd);
				
				//setting the connections for the new node
				aConn = new NodeConnections();
				aConn.setStartNodeId(currentEnd);
				
				System.out.println("Current start has not met yet.");
				
			}
			
			else {
				
				//getting the already existing connection (end node)
				aConn = allConnections[currentEnd];
				
				System.out.println("Current start already met. Old connections retrieved.");
				
			}
			
			//setting the connection (end node)
			connectionsData = new data.Connection(currentRoad.getEdgeId(), currentStart, currentRoad.getDistance());
			aConn.setConnection(connectionsData);
			allConnections[currentEnd] = aConn;
			
			System.out.println("Connection set (start).");
			
			aConn = null;
			connectionsData = null;
			
			System.out.println("----> DONE!");
		}
	}
	

	
	
	
	public NodeConnections[] getAllConnections(){
		return this.allConnections;
	}
	
	public String nodeConnectionsToString(){
		String res = "";
		for(int i = 0; i < allConnections.length; i++){
			NodeConnections aConn = allConnections[i];
			res = "Connections for node " + aConn.getStartNodeId() + ": (edge, end, distance)\n\t";
			for(int j = 0; j < aConn.getConnections().size(); j++)
			res += "Connection nr." + j + ": " + aConn.getConnections().get(j).getEdge() + ", "
					+ aConn.getConnections().get(j).getEnd() + ", " +
					+ aConn.getConnections().get(j).getDistance() + ", "
					+ "\n\t";
			System.out.println(res);
			System.out.println();
		}
		
		return res;
	}
}
