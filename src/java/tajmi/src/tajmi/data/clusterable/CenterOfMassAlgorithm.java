/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tajmi.data.clusterable;

/**
 *
 * @author badi
 */
public interface CenterOfMassAlgorithm<T> {

    /**
     * Compute the center of mass for a cluster
     * @param cluster the points in a cluster
     * @return a single instance representing the center of mass
     */
    public T center(Iterable<T> cluster);
}
