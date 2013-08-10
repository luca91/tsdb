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
		boolean check = false;
		double[] connectionsData; 
		NodeConnections aConn;

		NodeConnections inverseConn;
		Vector<Integer> nodeChecked = new Vector<Integer>();
		System.out.println("Number of node to check is: "+roads.size());
		for(int i = 0; i < roads.size()-19000; i++){
			System.out.println();
			System.out.printf("Iteration nr: %d\n", i);
			currentRoad = roads.get(i);
			connectionsData = new double[3];
			
			currentStart = currentRoad.getStartNode();
			currentEnd = currentRoad.getEndNode();
			
			nodeChecked.add(currentStart);
			
			//Checking if the node 'currentEnd' is already visited or not
			if (nodeChecked.contains(currentEnd))
			{
				check = true;
				System.out.printf("Same node\n");
			}
			
			System.out.printf("CurrentStart %d\n", currentStart);
			System.out.printf("currentEnd %d\n", currentEnd);
			System.out.println("The number of node checked is: "+nodeChecked.size()+"/"+roads.size());
			System.out.printf("Record: %d, %d, %d, %f \n", currentRoad.edgeId, currentRoad.getStartNode(), currentRoad.getEndNode(), currentRoad.getDistance());
//			if (currentStart == old || currentEnd == old) {
//				check = true;
//				System.out.printf("Same node\n");
//			}
			if (check) {
				System.out.printf("IF entered.\n");	
				
//				connectionsData = new double[3];
				aConn = allConnections[currentRoad.endNodeId];
				aConn.setConnection(endNodeConnection(currentRoad));
				System.out.printf("StartNodeId: %d\n", currentRoad.startNodeId);
				System.out.println("Connections: "+aConn.getConnections().size());
				allConnections[currentRoad.endNodeId] = aConn;
				
				
				
				
				
				/*while (Array.getDouble(allConnections[old+1].getConnections(indexMatrix), 0) != 0) {
					indexMatrix++;
				}			
				System.out.printf("indexMatrix: %d\n", indexMatrix);*/

				//currentStart == old
//				if(currentStart == old){
//					setStartEqualOld(data, connectionsData, aConn, currentStart);
//					old = currentStart;					
//					connectionsData = new double[3];
//					inverseConn = new NodeConnections();
//					
//					if(allConnections[data.endNodeId] == null){
//						connectionsData[0] = data.getEdgeId();
//						connectionsData[1] = data.getStartNode();
//						connectionsData[2] = data.getDistance();
//						inverseConn.setConnection(connectionsData);
//						inverseConn.setStartNodeId(currentEnd);
//						allConnections[data.endNodeId] = inverseConn;
//						System.out.printf("StartNodeId: %d\n", data.startNodeId);
//						System.out.printf("Connections[%d][0]: %f\n", inverseConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
//						System.out.printf("Connections[%d][1]: %f\n", inverseConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
//						System.out.printf("Connections[%d][2]: %f\n", inverseConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));
//					}
//					else {
//						inverseConn = allConnections[currentEnd];
//						connectionsData[0] = data.getEdgeId();
//						connectionsData[1] = data.getStartNode();
//						connectionsData[2] = data.getDistance();
//						inverseConn.setConnection(connectionsData);
//						System.out.printf("StartNodeId: %d\n", data.startNodeId);
//						System.out.printf("Connections[%d][0]: %f\n", inverseConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
//						System.out.printf("Connections[%d][1]: %f\n", inverseConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
//						System.out.printf("Connections[%d][2]: %f\n", inverseConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));
//					}
//				}
//
//				//currentEnd == old
//				else if(currentEnd == old){
//					
//					//setting the connection based on the end node
//					System.out.printf("CurrentEnd IF entered.\n");
//					aConn = allConnections[currentEnd];
//					connectionsData[0] = data.getEdgeId();
//					connectionsData[1] = data.getStartNode();
//					connectionsData[2] = data.getDistance();
//					aConn.setConnection(connectionsData);
////					aConn.setStartNodeId(currentEnd);
////					allConnections[data.endNodeId] = aConn;
//					System.out.printf("StartNodeId: %d\n", data.startNodeId);
//					System.out.printf("Connections[%d][0]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
//					System.out.printf("Connections[%d][1]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
//					System.out.printf("Connections[%d][2]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));					
////					while (Array.getDouble(allConnections[old].getConnections(indexMatrix), 1) != 0) {
////						indexMatrix++;
////					}	
//					
//					//setting the connection of the same edge based on the start node
//					aConn = new NodeConnections();
//					aConn = allConnections[currentStart];
//					connectionsData[0] = data.getEdgeId();
//					connectionsData[1] = data.getEndNode();
//					connectionsData[2] = data.getDistance();
//					aConn.setConnection(connectionsData);
////					aConn.setStartNodeId(currentEnd);
////					allConnections[data.endNodeId] = aConn;
//					System.out.printf("StartNodeId: %d\n", data.startNodeId);
//					System.out.printf("Connections[%d][0]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
//					System.out.printf("Connections[%d][1]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
//					System.out.printf("Connections[%d][2]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));
//					old = currentEnd;
//				}

			} 
				
				//first connection found for a given node				

				//adding the same connections to the list of the current end node
//				connectionsData = new double[3];
				aConn = new NodeConnections();
				aConn.setConnection(startNodeConnection(currentRoad));
				System.out.printf("StartNodeId: %d\n", currentRoad.startNodeId);
				System.out.println("Connections: "+aConn.getConnections().size());
				allConnections[currentRoad.startNodeId] = aConn;
					
//				if(nodeChecked.contains(data.getEndNode())){
//					connectionsData[0] = data.getEdgeId();
//					connectionsData[1] = data.getStartNode();
//					connectionsData[2] = data.getDistance();
//					aConn.setConnection(connectionsData);
//					aConn.setStartNodeId(currentEnd);
//					allConnections[data.endNodeId] = aConn;
//					System.out.printf("StartNodeId: %d\n", data.endNodeId);
//					System.out.printf("Connections[%d][0]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
//					System.out.printf("Connections[%d][1]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
//					System.out.printf("Connections[%d][2]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));
//				}
//				else {
//					aConn = allConnections[currentEnd];
//					connectionsData[0] = data.getEdgeId();
//					connectionsData[1] = data.getStartNode();
//					connectionsData[2] = data.getDistance();
//					aConn.setConnection(connectionsData);
//					System.out.printf("StartNodeId: %d\n", data.startNodeId);
//					System.out.printf("Connections[%d][0]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
//					System.out.printf("Connections[%d][1]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
//					System.out.printf("Connections[%d][2]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));
//				}		
//				old = data.startNodeId;
			
			check = false;
		}
		System.out.println("AllConnections size: "+allConnections.length);
		System.out.println("Node checked size: "+nodeChecked.size());
		aConn = allConnections[0];
		System.out.println("Nodo : "+aConn.getConnections().get(0).startNodeId);
		//System.exit(0);
		for (int j=0; j<allConnections.length;j++){
			System.out.println("Ciclo for ESTERNO");
			aConn = allConnections[nodeChecked.get(j)];
			for (int h=0; h<aConn.getConnections().size(); h++){
				System.out.println("Ciclo for INTERNO");
				System.out.println("Nodo : "+aConn.getConnections().get(h).startNodeId+" size: "+aConn.getConnections().size());
			}
		}
	}
	
	public Road startNodeConnection(Road data) {
		System.out.printf("Else entered.\n");
		Road tmpRoad = new Road();
		tmpRoad.startNodeId = data.getStartNode();
		tmpRoad.edgeId = data.getEdgeId();
		tmpRoad.endNodeId = data.getEndNode(); 
		tmpRoad.distance = data.getDistance();
		
		return tmpRoad;
//		connectionsData[0] = data.getEdgeId();
//		connectionsData[1] = data.getEndNode();
//		connectionsData[2] = data.getDistance();
//		aConn.setConnection(connectionsData);
//		aConn.setStartNodeId(currentStart);
//		allConnections[currentStart] = aConn;
			
	}
	
	public Road endNodeConnection(Road data) {
		System.out.printf("Else entered.\n");
		Road tmpRoad = new Road();
		tmpRoad.startNodeId = data.getEndNode();
		tmpRoad.edgeId = data.getEdgeId();
		tmpRoad.endNodeId = data.getStartNode();
		tmpRoad.distance = data.getDistance();
		
		return tmpRoad;
//		connectionsData[0] = data.getEdgeId();
//		connectionsData[1] = data.getEndNode();
//		connectionsData[2] = data.getDistance();
//		aConn.setConnection(connectionsData);
//		aConn.setStartNodeId(currentStart);
//		allConnections[currentStart] = aConn;
			
	}
	
	public void setStartEqualOld(Road data, double[] connectionsData, NodeConnections aConn, int currentStart){
		System.out.printf("CurrentStart IF entered.\n");
		aConn = allConnections[currentStart];
		connectionsData[0] = data.getEdgeId();
		connectionsData[1] = data.getEndNode();
		connectionsData[2] = data.getDistance();
//		aConn.setConnection(connectionsData);
//		aConn.setStartNodeId(currentStart);
//		allConnections[data.startNodeId] = aConn;
		System.out.printf("StartNodeId: %d\n", data.startNodeId);
		System.out.printf("Connections[%d][0]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 0));
		System.out.printf("Connections[%d][1]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 1));
		System.out.printf("Connections[%d][2]: %f\n", aConn.getConnections().size()-1, Array.getDouble(aConn.getConnections().lastElement(), 2));
	}
	
	
	
	public NodeConnections[] getAllConnections(){
		return this.allConnections;
	}
}
