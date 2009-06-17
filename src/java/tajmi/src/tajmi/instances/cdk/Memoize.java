
package tajmi.instances.cdk;

import java.util.HashMap;

/**
 * Used to hold global state in a singleton.
 * 
 * @author badi
 */
public class Memoize {


    /* Single code here */
    private static Memoize INSTANCE = null;

    public static synchronized Memoize getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Memoize();
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

    private Memoize() {
        distances = new HashMap<String, Double>(100);
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
