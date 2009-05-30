
package tajmi.interfaces;

import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public interface DistanceFunc<T> extends Fun {

    public DistanceFunc params(T first, T second);

    public Double call ();
}
