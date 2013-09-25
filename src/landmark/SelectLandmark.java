package landmark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Collections;
import engine.ShortestPath;
import model.NodeConnections;

public class SelectLandmark {
	
	Vector<Integer> vertices;
	@SuppressWarnings("rawtypes")
	Vector<Vector> allPaths;
	List<Landmark> landmarkList;
	Connection conn;
	public static int NUMBER_VERTICES = 9;
	
	@SuppressWarnings("rawtypes")
	public SelectLandmark(Connection conn) {
		vertices = new Vector<Integer>();
		allPaths = new Vector<Vector>();
		landmarkList = new ArrayList<Landmark>();
		this.conn = conn;
	}
	
	public List<Landmark> recurrenceVertices() throws Exception {	
		System.out.println("###############################################################");
		System.out.println("Compute recurrences of vertices in paths");
		System.out.println("###############################################################");
		getVerticesFromDB();		
		System.out.println("All vertices stored in a Vector<Integer> --> "+vertices);
		System.out.println("Calculation the landmarks...");		
		int nOF = new File("C:\\Users\\Simone\\Desktop\\paths\\").list().length-1;
//		int nOF = new File("C:\\Users\\luca\\Desktop\\paths\\").list().length-1;
		System.out.println("Number of files: "+nOF+", needed to know how many paths we have to verify (The measure differs of 2 because there is a folder!)");
		System.out.println("All the paths with length less or equal two will be deleted!");
			try {
				for (int i=0; i<nOF; i++){
					FileReader fr;
					//fr = new FileReader("C:\\Users\\Simone\\Dropbox\\Università\\Third Year\\Second Semester\\Temporal and Spatial Database\\Project\\paths\\path_nr_"+i+".txt.txt");
//					fr = new FileReader("C:\\Users\\Simone\\Desktop\\path\\path_nr_"+i+".txt.txt");
					fr = new FileReader("C:\\Users\\Simone\\Desktop\\paths\\path_nr_"+i+".txt");
					BufferedReader br = new BufferedReader(fr);
					Vector<Integer> path = new Vector<Integer>();
					String tmp = br.readLine();
					while (tmp!= null)
					{						
						if (tmp != null) {
							int id = Integer.parseInt(tmp);
							path.add(id);
						}
						tmp = br.readLine();
					}
					if (path.size()<=2)
					{
						br.close();
						fr.close();
						File fileToDelete = new File("C:\\Users\\Simone\\Desktop\\paths\\path_nr_"+i+".txt");	
//						File fileToDelete = new File("C:\\Users\\luca\\Desktop\\paths\\path_nr_"+i+".txt");
						boolean ok = fileToDelete.delete();
						if (ok == true)
							System.out.println("The path of file "+fileToDelete.getName()+" is too short,\nso it is deleted!");
					} else {
						allPaths.add(path);
						System.out.println("The path of file path_nr_"+i+".txt is perfect ("+path+"),\nso it is stored!");
					}
				}
				System.out.println("The total number of paths filtered is: "+allPaths.size());
				System.out.println("These are all paths: "+allPaths);
				System.out.println("Computing recurrece...");
				int landmarks[] = new int[NUMBER_VERTICES];
				for (int j=0; j<allPaths.size(); j++) {
					@SuppressWarnings("unchecked")
					Vector<Integer> tmpPath = allPaths.get(j);
					System.out.println("Current path size: "+tmpPath.size());
					System.out.println("Current path: "+tmpPath);
					for (int h=0; h<tmpPath.size(); h++) {
						int curVertex = tmpPath.get(h);
						int counter;
						System.out.println("Current vertex: "+curVertex);
						counter = landmarks[curVertex];
						landmarks[curVertex] = counter+1;
						/**if (landmarks.size() == 0 || landmarks.indexOf(curVertex) == -1) {
							System.out.println("New landmark.");
							landmarks.add(curVertex,counter+1);
						} else {			
							System.out.println("Vertex already present.");
							counter = landmarks.get(curVertex);
							System.out.println("Counter: "+counter);
							landmarks.add(curVertex,counter+1);
						}**/
					}
				}
				System.out.println("Storing landmarks in a list...");
 				for (int k =0; k<NUMBER_VERTICES; k++) {
					Landmark l = new Landmark(k, landmarks[k]);
					landmarkList.add(l);
				}
 				System.out.println("Removing 0's landmarks...");
 				System.out.println("Landmark list size: "+landmarkList.size());
 				for (int m=0; m<landmarkList.size(); m++){
 					System.out.println("Index --> "+m);
 					System.out.println(landmarkList.get(m).landmarkID+" -- "+landmarkList.get(m).counter);
 					if (landmarkList.get(m).counter==0) { 						
 						landmarkList.remove(m);
 					}
 				}
				System.out.println("Landmark sorted by counter presence in descending order:");
				Collections.sort(landmarkList,Collections.reverseOrder(new Landmark.LadmarksComparator()));
				for (Landmark item: landmarkList) {
					System.out.println("Vertex "+item.landmarkID+" compares "+item.counter+" times.");
				}
				
				
					/**for (int h=0; h<tmpPath.size(); h++) {
						boolean goAhead = true;
						int curVertex = tmpPath.get(h);
						System.out.println("Vertex taken: "+curVertex);
						if (landmarks.size()==0) {
							l = new Landmark(curVertex);
							l.incrementCounter();
							landmarks.add(l);
							goAhead = false;
						}
						System.out.println("Landmarks size: "+landmarks.size());
						int y = 0;						
						while (goAhead == true || y<landmarks.size()) {
						//for (int y=0; y<landmarks.size(); y++){	
							System.out.println("Counter Y: "+y);
							System.out.println("Curr land: "+curVertex);
							if (landmarks.get(y).landmarkID != curVertex ) {
								System.out.println("Creation new landmark.");
								l = new Landmark(curVertex);
								l.incrementCounter();
								landmarks.add(l);
								goAhead = false;
							} else if (y!=0){
								System.out.println("Landmark already present.");
								landmarks.get(y).incrementCounter();
								goAhead = false;
							}
							y++;
						}
					}
				}
				System.out.println("All landmarks: "+landmarks);
				for (int k=0; k<landmarks.size(); k++) {
					System.out.println("Landmark --> "+landmarks.get(k).landmarkID+" Counter --> "+landmarks.get(k).counter);
				}**/
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return landmarkList;
		}
	
	public void getVerticesFromDB() throws Exception{
		System.out.println("###############################################################");
		System.out.println("Get vertices from database");
		System.out.println("###############################################################");
		try {			
    		Statement stmt = conn.createStatement();            
            //ResultSet rs = stmt.executeQuery("SELECT * FROM roads");
    		ResultSet rs = stmt.executeQuery("SELECT start_node_id, end_node_id FROM testRoad");
            while (rs.next()){
            	int start = rs.getInt(1);
            	int end = rs.getInt(2);
            	if (vertices.size() == 0)
            		vertices.add(start);
            	if (!vertices.contains(start))
            		vertices.add(start);
            	if (!vertices.contains(end))
            		vertices.add(end);
            }
        } catch (SQLException e) {
        	System.out.println(e);
        	}
	}
	
	public void bestCoverage() throws Exception{
		List<Landmark> landmarkList = recurrenceVertices();
		System.out.println("###############################################################");
		System.out.println("BEST COVERAGE");
		System.out.println("###############################################################");
		int j=0;
		System.out.println("Number of all landmarks: "+landmarkList.size());
		while (j < landmarkList.size()){
			for (int i=0; i<allPaths.size(); i++){
				@SuppressWarnings("unchecked")
				Vector<Integer> path = allPaths.get(i);
				System.out.println("Current path: "+path);
				System.out.println("Control which path contains a specific landmark.");
				if (path.contains(landmarkList.get(j).landmarkID)){
					System.out.println("Landmark contained! The path will be stored to landmark's object.");
					Collections.reverse(path);
					landmarkList.get(j).path.add(path);
					allPaths.remove(i);		
					System.out.println("Path removed from allPaths vector.");
				}
			}			
			j++;
		}
		System.out.println("Control that the final size of allPaths is '0' --> "+allPaths.size()+" <--");
		for (int h=0; h<landmarkList.size(); h++){
			System.out.println("Landmark: "+landmarkList.get(h).landmarkID);
			System.out.println("Path associated: "+landmarkList.get(h).path);
		}
		System.out.println("--- BEST COVERAGE TERMINATED! ---");
	}
	
	public List<Landmark> getLandmarkList(){
		return this.landmarkList;
	}
}

