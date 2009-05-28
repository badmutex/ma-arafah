
package tajmi.instances.vectorial.som;

import tajmi.instances.vectorial.Vector;
import tajmi.interfaces.som.StopFunc;
import tajmi.som.SOMParams;

/**
 * Stops after 50 SOMParams.iterations
 * @author badi
 */
public class VectorStopFunc implements StopFunc<Vector> {

    SOMParams<Vector> state;

    public StopFunc<Vector> params(SOMParams<Vector> state) {
        this.state = state;

        return this;
    }

    public Boolean call() {
        if (state.iterations < 50)
            return false;
        else return true;
    }

}
