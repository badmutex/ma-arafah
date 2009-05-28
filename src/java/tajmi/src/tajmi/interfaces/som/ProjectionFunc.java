
package tajmi.interfaces.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 * Projects a datum onto the field, updates the field, and returns it.
 * @author badi
 */
public interface ProjectionFunc<T> extends Callable<SOMParams<T>> {

    public ProjectionFunc<T> params (T datum, SOMParams<T> state);

    public SOMParams<T> call();

}
