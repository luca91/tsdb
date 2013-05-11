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
	
	int manageData(){
		int currentStart = 0, currentEnd = 0;
		int old = -1;
		Road data = new Road();
		boolean check = false;
		double[] connectionsData; 
		NodeConnections aConn;
		for(int i = 0; i < roads.size(); i++){
			System.out.printf("Iteration nr: %d\n", i);
			data = roads.get(i);
			connectionsData = new double[3];
			aConn = new NodeConnections();
			currentStart = data.getStartNode();
			currentEnd = data.getEndNode();
			System.out.printf("CurrentStart %d\n", currentStart);
			System.out.printf("currentEnd %d\n", currentEnd);
			System.out.printf("Record: %d, %d, %d, %f \n", data.edgeId, data.getStartNode(), data.getEndNode(), data.getDistance());
			if (currentStart == old || currentEnd == old) {
				check = true;
				System.out.printf("True. \n");
			}
			if (check) {
				System.out.printf("IF entered.\n");	
				/*while (Array.getDouble(allConnections[old+1].getConnections(indexMatrix), 0) != 0) {
					indexMatrix++;
				}			
				System.out.printf("indexMatrix: %d\n", indexMatrix);*/

				//currentStart == old
				if(currentStart == old){
					System.out.printf("CurrentStart IF entered.\n");
					aConn = allConnections[currentStart];
					connectionsData[0] = data.getEdgeId();
					connectionsData[1] = data.getEndNode();
					connectionsData[2] = data.getDistance();
					aConn.setConnections(connectionsData);
//					aConn.setStartNodeId(currentStart);
//					allConnections[data.startNodeId] = aConn;
					System.out.printf("StartNodeId: %d\n", data.startNodeId);
					System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
					System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
					System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
					old = currentStart;
					
					connectionsData = new double[3];
					aConn = new NodeConnections();
					if(allConnections[data.endNodeId] == null){
						connectionsData[0] = data.getEdgeId();
						connectionsData[1] = data.getStartNode();
						connectionsData[2] = data.getDistance();
						aConn.setConnections(connectionsData);
						aConn.setStartNodeId(currentEnd);
						allConnections[data.endNodeId] = aConn;
						System.out.printf("StartNodeId: %d\n", data.startNodeId);
						System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
						System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
						System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
					}
					else {
						aConn = allConnections[currentEnd];
						connectionsData[0] = data.getEdgeId();
						connectionsData[1] = data.getStartNode();
						connectionsData[2] = data.getDistance();
						aConn.setConnections(connectionsData);
						System.out.printf("StartNodeId: %d\n", data.startNodeId);
						System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
						System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
						System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
					}
				}

				//currentEnd == old
				else if(currentEnd == old){
					
					//setting the connection based on the end node
					System.out.printf("CurrentEnd IF entered.\n");
					aConn = allConnections[currentEnd];
					connectionsData[0] = data.getEdgeId();
					connectionsData[1] = data.getStartNode();
					connectionsData[2] = data.getDistance();
					aConn.setConnections(connectionsData);
//					aConn.setStartNodeId(currentEnd);
//					allConnections[data.endNodeId] = aConn;
					System.out.printf("StartNodeId: %d\n", data.startNodeId);
					System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
					System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
					System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));					
//					while (Array.getDouble(allConnections[old].getConnections(indexMatrix), 1) != 0) {
//						indexMatrix++;
//					}	
					
					//setting the connection of the same edge based on the start node
					aConn = new NodeConnections();
					aConn = allConnections[currentStart];
					connectionsData[0] = data.getEdgeId();
					connectionsData[1] = data.getEndNode();
					connectionsData[2] = data.getDistance();
					aConn.setConnections(connectionsData);
//					aConn.setStartNodeId(currentEnd);
//					allConnections[data.endNodeId] = aConn;
					System.out.printf("StartNodeId: %d\n", data.startNodeId);
					System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
					System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
					System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
					old = currentEnd;
				}

			} else {
				
				//first connection found for a given node
				System.out.printf("Else entered.\n");
				connectionsData[0] = data.getEdgeId();
				connectionsData[1] = data.getEndNode();
				connectionsData[2] = data.getDistance();
				aConn.setConnections(connectionsData);
				aConn.setStartNodeId(currentStart);
				allConnections[currentStart] = aConn;
				System.out.printf("StartNodeId: %d\n", data.startNodeId);
				System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
				System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
				System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
				
				
				
				connectionsData = new double[3];
				aConn = new NodeConnections();
				if(allConnections[data.endNodeId] == null){
					connectionsData[0] = data.getEdgeId();
					connectionsData[1] = data.getStartNode();
					connectionsData[2] = data.getDistance();
					aConn.setConnections(connectionsData);
					aConn.setStartNodeId(currentEnd);
					allConnections[data.endNodeId] = aConn;
					System.out.printf("StartNodeId: %d\n", data.startNodeId);
					System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
					System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
					System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
				}
				else {
					aConn = allConnections[currentEnd];
					connectionsData[0] = data.getEdgeId();
					connectionsData[1] = data.getStartNode();
					connectionsData[2] = data.getDistance();
					aConn.setConnections(connectionsData);
					System.out.printf("StartNodeId: %d\n", data.startNodeId);
					System.out.printf("Connections[%d][0]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 0));
					System.out.printf("Connections[%d][1]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 1));
					System.out.printf("Connections[%d][2]: %f\n", aConn.getListNode().size()-1, Array.getDouble(aConn.getListNode().lastElement(), 2));
				}
							
				old = data.startNodeId;
			}
			check = false;
		}
		//System.exit(0);
		return 0;

	}
	
	public NodeConnections[] getAllConnections(){
		return this.allConnections;
	}
}
