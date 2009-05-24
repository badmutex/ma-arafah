
package tajmi.data.som;

import java.util.Random;
import tajmi.data.clusterable.DistanceFunc;

/**
 *
 * @author badi
 */
public class SOMParams<T> {

    public Field<T> field;

    public DistanceFunc<T> distance_func;
    public NeighborhoodFunc<T> neighborhood_func;
    public ProjectionFunc<T> project_func;
    public StopFunc<T> stop_func;

    public double restraint;

    public Random random_gen;

}
