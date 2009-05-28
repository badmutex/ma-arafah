
package tajmi.interfaces;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface DistanceFunc<T> extends Callable<Double> {

    public DistanceFunc params(T first, T second);

    public Double call ();
}
