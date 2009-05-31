
package tajmi.instances.vectorial.som;

import tajmi.instances.vectorial.Vector;
import tajmi.abstracts.som.StopFunc;
import tajmi.som.SOMParams;

/**
 * Stops after 50 SOMParams.iterations
 * @author badi
 */
public class VectorStopFunc extends StopFunc {

    public Boolean call() {
        SOMParams state = getState();

        if (state.iterations < 100)
            return false;
        else return true;
    }

}
