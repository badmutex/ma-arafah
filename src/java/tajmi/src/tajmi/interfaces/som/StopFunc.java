
package tajmi.interfaces.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 * Determins if the SOM should stop training.
 * @author badi
 */
public interface StopFunc<T> extends Callable<Boolean> {

    public StopFunc<T> params (SOMParams<T> state);

    public Boolean call ();

}
