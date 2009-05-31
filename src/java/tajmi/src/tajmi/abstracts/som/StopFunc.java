
package tajmi.abstracts.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 * Determins if the SOM should stop training.
 * @author badi
 */
public abstract class StopFunc implements Callable<Boolean> {

    SOMParams state;

    public SOMParams getState() {
        return state;
    }

    public StopFunc params(SOMParams state) {
        this.state = state;

        return this;
    }

    public abstract Boolean call ();

}
