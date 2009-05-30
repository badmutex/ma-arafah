
package tajmi.instances.vectorial.som;

import tajmi.instances.vectorial.Vector;
import tajmi.interfaces.som.StopFunc;
import tajmi.som.SOMParams;

/**
 * Stops after 50 SOMParams.iterations
 * @author badi
 */
public class VectorStopFunc extends StopFunc<Vector> {

    public Boolean call() {
        SOMParams<Vector> state = getState();

        if (state.iterations < 100)
            return false;
        else return true;
    }

}
