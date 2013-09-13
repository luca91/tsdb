package landmark;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Collections;

public class SelectLandmark {
	
	public void bestCoverage() {	
		System.out.println("Calclulation the landmarks.");
		Vector<Vector> allPaths = new Vector<Vector>();
			try {
				for (int i=0; i<4; i++){
					FileReader fr;
					//fr = new FileReader("C:\\Users\\Simone\\Dropbox\\Università\\Third Year\\Second Semester\\Temporal and Spatial Database\\Project\\paths\\path_nr_"+i+".txt.txt");
					fr = new FileReader("C:\\Users\\Simone\\Desktop\\path\\path_nr_"+i+".txt.txt");
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
						System.out.println("Path too short. It will be dropped!");
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
				List<Landmark> landmarkList = new ArrayList<Landmark>();
 				for (int k =0; k<landmarks.length; k++) {
					System.out.println("Vertex "+k+" compares "+landmarks[k]+" times.");
					Landmark l = new Landmark(k, landmarks[k]);
					landmarkList.add(l);
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
		}
		
	}

