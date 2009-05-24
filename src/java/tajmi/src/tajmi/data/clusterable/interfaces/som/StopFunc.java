
package tajmi.data.clusterable.interfaces.som;

import tajmi.data.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface StopFunc<T> extends Callable<Boolean> {

    public StopFunc<T> params (SOMParams<T> state);

    public Boolean call ();

}
