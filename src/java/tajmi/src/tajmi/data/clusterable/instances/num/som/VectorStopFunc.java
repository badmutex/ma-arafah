
package tajmi.data.clusterable.instances.num.som;

import tajmi.data.clusterable.instances.num.Vector;
import tajmi.data.clusterable.interfaces.som.StopFunc;
import tajmi.data.som.SOMParams;

/**
 *
 * @author badi
 */
public class VectorStopFunc implements StopFunc<Vector> {

    SOMParams<Vector> state;

    public StopFunc<Vector> params(SOMParams<Vector> state) {
        this.state = state;

        return this;
    }

    public Boolean call() {
        if (state.iteration < 50)
            return false;
        else return true;
    }

}
