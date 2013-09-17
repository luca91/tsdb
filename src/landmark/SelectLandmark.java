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

import database.DBConnection;

import model.Road;

public class SelectLandmark {
	
	Vector<Integer> vertices;
	Vector<Vector> allPaths;
	
	public SelectLandmark() {
		vertices = new Vector<Integer>();
		allPaths = new Vector<Vector>();
	}
	
	public List<Landmark> recurrenceVertices() throws Exception {	
		getVerticesFromDB();
		List<Landmark> landmarkList = new ArrayList<Landmark>();
//		System.out.println();
		System.out.println("All vertices: "+vertices);
		System.out.println("Calculation the landmarks.");		
		int nOF = new File("C:\\Users\\Simone\\Desktop\\paths\\").list().length;
		System.out.println("Number of Files: "+nOF);
			try {
				for (int i=0; i<nOF; i++){
					FileReader fr;
					//fr = new FileReader("C:\\Users\\Simone\\Dropbox\\Università\\Third Year\\Second Semester\\Temporal and Spatial Database\\Project\\paths\\path_nr_"+i+".txt.txt");
//					fr = new FileReader("C:\\Users\\Simone\\Desktop\\path\\path_nr_"+i+".txt.txt");
					fr = new FileReader("C:\\Users\\Simone\\Desktop\\paths\\path_nr_"+i+".txt");
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(fr);
					Vector<Integer> path = new Vector<Integer>();
					String tmp = br.readLine();
					while (tmp!= null)
					{						
						//System.out.println(tmp);
						if (tmp != null) {
							int id = Integer.parseInt(tmp);
							path.add(id);
						}
						//System.out.println(path);
						tmp = br.readLine();
					}
					if (path.size()<=2)
					{
						br.close();
						fr.close();
						System.out.println("Path too short. It will be dropped!");
						File fileToDelete = new File("C:\\Users\\Simone\\Desktop\\paths\\path_nr_"+i+".txt");			
						System.out.println("File to delete: "+fileToDelete.getName());
						boolean ok = fileToDelete.delete();
						if (ok == true)
							System.out.println("File deleted!");
					} else {
						System.out.println("Path OK! It will be saved!");
						System.out.println("Path: "+path);
						allPaths.add(path);
					}
				}
				System.out.println("Total paths are: "+allPaths.size());
				System.out.println("All paths: "+allPaths);
				//Landmark l;
				//Vector<Integer> landmarks = new Vector<Integer>();
				int landmarks[] = new int[9];
				for (int j=0; j<allPaths.size(); j++) {
					@SuppressWarnings("unchecked")
					Vector<Integer> tmpPath = allPaths.get(j);
					System.out.println("Current path size: "+tmpPath.size());
					for (int h=0; h<tmpPath.size(); h++) {
						int curVertex = tmpPath.get(h);
						int counter;
						System.out.println("Path size: "+tmpPath.size());
						System.out.println("Currente vertex: "+curVertex);
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
				System.out.println("Printing landmarks...");
 				for (int k =0; k<landmarks.length; k++) {
					System.out.println("Vertex "+k+" compares "+landmarks[k]+" times.");
					Landmark l = new Landmark(k, landmarks[k]);
					landmarkList.add(l);
				}
 				System.out.println("Removing 0's landmark...");
 				for (int m=0; m<landmarkList.size(); m++){
 					if (landmarkList.get(m).counter==0)
 						landmarkList.remove(m);
 				}
				System.out.println("Landmark sorted by conter presence in descending order:");
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
		try {
			DBConnection conn = new DBConnection();
    		Statement stmt = conn.getConn().createStatement();            
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
//            	aRoad.printRoad();
            }
        } catch (SQLException e) {
        	System.out.println(e);
        	}
	}
	
	public void bestCoverage() throws Exception{
		List<Landmark> landmarkList = recurrenceVertices();
		int j=0;
		System.out.println("Number of landmarks: "+landmarkList.size());
		while (j < landmarkList.size()){
			for (int i=0; i<allPaths.size(); i++){
				@SuppressWarnings("unchecked")
				Vector<Integer> path = allPaths.get(i);
				System.out.println("Current path: "+path);
				if (path.contains(landmarkList.get(j).landmarkID)){
					System.out.println("Landmark contained!");
					Collections.reverse(path);
					landmarkList.get(j).path.add(path);
					allPaths.remove(i);
				}
			}			
			j++;
		}
		System.out.println("Final size allPaths: "+allPaths.size());
		for (int h=0; h<landmarkList.size(); h++){
			System.out.println("Landmark: "+landmarkList.get(h).landmarkID);
			System.out.println("Landmark: "+landmarkList.get(h).path);
		}
		System.out.println("--- BEST COVERAGE TERMINATED! ---");
	}
	
		
	}

