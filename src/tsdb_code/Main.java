package tsdb_code;

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
		for (int i = 0; i < 21694; i++){
			allPath.add(aPath.calculatePath(data.getAllConnections()));
			
		}
	}

}
