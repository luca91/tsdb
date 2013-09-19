package main;

import landmark.SelectLandmark;
import parser.RetrieveData;
import database.DBConnection;
import engine.ShortestPath;

public class Main {
	
//	static Vector<Vector<Integer>> allPath = new Vector<Vector<Integer>>();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DBConnection conn = new DBConnection();
		int source = -1, destination = -1;
		if (conn.getDBConnection()==true)
			System.out.println("DB connected!");
		RetrieveData data = new RetrieveData();
		data.getDataFromDB(conn.getConn());
		data.manageData();
//		System.out.println(data.nodeConnectionsToString());
		ShortestPath aPath = new ShortestPath();
		System.out.println("###############################################################");
		System.out.println("ALL PATH CALCULATION START");
		System.out.println("###############################################################");
		System.out.println("Searching for paths...");
		for (int i = 0; i < 11; i++){
//			System.out.printf("##########################\n");
//			System.out.printf("ITERATION NR. %d\n", i);
//			System.out.printf("##########################\n");
			
//			source = aPath.generateRandomNumbers();
//			destination = aPath.generateRandomNumbers();
			do 
			{
				source = aPath.generateRandomNumbers();
				destination = aPath.generateRandomNumbers();
			} while (destination == -1 || source == -1);
			if(!aPath.checkNodes(source, destination))
//				allPath.add(aPath.calculatePath(data.getAllConnections(), source, destination, "C:\\Users\\Simone\\Desktop\\paths\\path_nr_"+i+".txt"));
//				aPath.calculatePath(data.getAllConnections(), source, destination, "C:\\Users\\luca\\Desktop\\paths\\path_nr_"+i+".txt");
				aPath.calculatePath(data.getAllConnections(), source, destination, "C:\\Users\\Simone\\Desktop\\paths\\path_nr_"+i);
			System.gc();
		}
		System.out.println();
		System.out.println();
		System.out.println("###############################################################");
		System.out.println("ALL PATH CALCULATION END");
		System.out.println("###############################################################");
		SelectLandmark landmark = new SelectLandmark(conn.getConn());
//		landmark.bestCoverage();
		System.out.println("###############################################################");
		System.out.println("LANMARK PART");
		System.out.println("###############################################################");
		landmark.landmarkBFS(data.getAllConnections());
		System.out.println("###############################################################");
		System.out.println("LANDMARK BFS END");
		System.out.println("###############################################################");
	}

}
