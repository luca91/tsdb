package main;

import java.util.Vector;

import data.DBConnection;
import data.RetrieveData;
import data.ShortestPath;

public class Main {
	
//	static Vector<Vector<Integer>> allPath = new Vector<Vector<Integer>>();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DBConnection conn = new DBConnection();
		if (conn.getDBConnection()==true)
			System.out.println("DB connected!");
		RetrieveData data = new RetrieveData();
		data.getDataFromDB(conn.getConn());
		data.manageData();
		System.out.println(data.nodeConnectionsToString());
		ShortestPath aPath = new ShortestPath();
		System.out.printf("\n###############################################################\n");
		System.out.printf("ALL PATH CALCULATION START\n");
		System.out.printf("###############################################################\n\n");
		for (int i = 0; i < 21694; i++){
			System.out.printf("##########################\n");
			System.out.printf("ITERATION NR. %d\n", i);
			System.out.printf("##########################\n");
			int source = -1, destination = -1;
			source = aPath.generateRandomNumbers();
			destination = aPath.generateRandomNumbers();
			if(!aPath.checkNodes(source, destination))
//				allPath.add(aPath.calculatePath(data.getAllConnections(), source, destination, "C:\\Users\\luca\\Desktop\\paths\\path_nr_"+i+".txt"));
				aPath.calculatePath(data.getAllConnections(), source, destination, "C:\\Users\\luca\\Desktop\\paths\\path_nr_"+i+".txt");
			System.gc();
		}
		System.out.println();
		System.out.println();
		System.out.printf("###############################################################\n");
		System.out.printf("ALL PATH CALCULATION END\n");
		System.out.printf("###############################################################");
	}

}