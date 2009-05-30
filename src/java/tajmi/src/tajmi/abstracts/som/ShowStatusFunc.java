
package tajmi.abstracts.som;

import java.util.concurrent.Callable;
import tajmi.som.SOMParams;

/**
 * Informs the user of the current state of the program
 * @author badi
 */
public abstract class ShowStatusFunc implements Callable<Void> {

    SOMParams state;

    public SOMParams getState() {
        return state;
    }

    public ShowStatusFunc params(SOMParams state) {
        this.state = state;

        return this;
    }

    public abstract Void call () ;
}
