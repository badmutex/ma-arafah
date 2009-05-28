
package tajmi.interfaces;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface CenterOfMassFunc<T> extends Callable<T> {

    public CenterOfMassFunc<T> params(Iterable<T> cluster);

    public T call ();
}
