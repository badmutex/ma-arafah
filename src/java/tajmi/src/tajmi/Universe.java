
package tajmi;

import java.util.HashMap;
import java.util.Hashtable;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * Used to hold global state in a singleton.
 * 
 * @author badi
 */
public class Universe {


    /* Single code here */
    private static Universe INSTANCE = null;

    public static synchronized Universe getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Universe();
            return INSTANCE;
        } else {
            return INSTANCE;
        }
    }
    /* usual class code here */
    /**
     * saves the results of potentially expensive distance computations between datapoints
     */
    HashMap<String, Double> distances;
    /**
     * saves the maximum common subgraphs between two atom containers
     */
    HashMap<String, IAtomContainer> mcsss;

    private Universe() {
        distances = new HashMap<String, Double>(100);
        mcsss = new HashMap<String, IAtomContainer>(100);
    }

    public String hashPair(String origin, String target) {
        return origin + target;
    }

    public void add_distance (String first, String second, double distance) {
        if(first == null || second == null)
            throw new RuntimeException("ID cannot be null");
        distances.put(hashPair(first, second), distance);
    }

    public double get_distance (String first, String second) {
        return distances.get(hashPair(first, second));
    }

    public boolean distance_exists (String first, String second) {
        return distances.containsKey(hashPair(first, second));
    }

}
