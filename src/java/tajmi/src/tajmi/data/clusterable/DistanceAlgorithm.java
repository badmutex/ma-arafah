/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tajmi.data.clusterable;

/**
 *
 * @author badi
 */
public interface DistanceAlgorithm<T> {

    /**
     * Compute the distance between this object and the other.
     * @para me the first object
     * @param you the other object
     * @return a double representing the distance between the two objects
     */
    public double distance(T me, T you);
}
