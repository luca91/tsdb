package landmark;
import java.util.Comparator;

public class Landmark {
	int landmarkID;
	int counter;
	
	public Landmark (int id, int c)
	{
		landmarkID = id;
		counter = c;
	}
	
	/**public void incrementCounter() {
		counter++;
	}**/
	
	static class LadmarksComparator implements Comparator<Landmark> {

			@Override
			public int compare(Landmark l1, Landmark l2) {
				int result;
				result = l1.counter - l2.counter;
				return result;
			}
		}

}
