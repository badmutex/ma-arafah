
package tajmi.abstracts.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 * Determins if the SOM should stop training.
 * @author badi
 */
public abstract class StopFunc<T> implements Callable<Boolean> {

    SOMParams<T> state;

    public SOMParams<T> getState() {
        return state;
    }

    public StopFunc<T> params(SOMParams<T> state) {
        this.state = state;

        return this;
    }

    public abstract Boolean call ();

}
