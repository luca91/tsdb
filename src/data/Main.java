package data;

import java.util.Random;
import java.util.Vector;

public class Main {
	
	static Vector<Vector<Integer>> allPath = new Vector<Vector<Integer>>();

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
		ShortestPath aPath = new ShortestPath();
		System.out.printf("\n###############################################################\n");
		System.out.printf("ALL PATH CALCULATION START\n");
		System.out.printf("###############################################################\n\n");
		for (int i = 0; i < 21694; i++){
			System.out.printf("##########################\n");
			System.out.printf("ITERATION NR. %d\n", i);
			System.out.printf("##########################\n\n");
			allPath.add(aPath.calculatePath(data.getAllConnections()));	
		}
		System.out.println();
		System.out.println();
		System.out.printf("###############################################################\n");
		System.out.printf("ALL PATH CALCULATION END\n");
		System.out.printf("###############################################################");
	}
	
	public int generateRandomNumbers(){
		Random rm = new Random();
		int number = rm.nextInt(21048);
		System.out.printf("Random number: %d\n", number);
		return number;		
	}

}
