
package tajmi.data.som;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface DistanceFunc<T> extends Callable<Double> {

    public DistanceFunc<T> params (T first, T second);

    public Double call ();

}
