
package tajmi.data.clusterable.som;

import tajmi.data.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface NeighborhoodFunc<T> extends Callable<Double> {

    public NeighborhoodFunc<T> params (T elem, T bmu, Field<T> f, double restraint);

    public Double call();

}
