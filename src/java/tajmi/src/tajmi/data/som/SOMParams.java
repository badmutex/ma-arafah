
package tajmi.data.som;

import tajmi.data.clusterable.interfaces.som.ProjectionFunc;
import tajmi.data.clusterable.interfaces.som.StopFunc;
import java.util.Random;

/**
 *
 * @author badi
 */
public class SOMParams<T> {

    public Field<T> field;

    public ProjectionFunc<T> project_func;
    public StopFunc<T> stop_func;

    public double restraint;

    public Random random_gen;

}
