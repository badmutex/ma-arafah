/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private final static Universe INSTANCE = null;

    public static synchronized Universe getInstance() {
        if (INSTANCE == null) {
            return new Universe();
        } else {
            return INSTANCE;
        }
    }
    /* usual class code here */
    /**
     * saves the results of potentially expensive distance computations between datapoints
     */
    HashMap<Integer, Double> distances;
    /**
     * saves the maximum common subgraphs between two atom containers
     */
    HashMap<Integer, IAtomContainer> mcsss;

    private Universe() {
        distances = new HashMap<Integer, Double>(100);
        mcsss = new HashMap<Integer, IAtomContainer>(100);
    }

    public int hashPair(String origin, String target) {
        return origin.hashCode() + target.hashCode();
    }

}
