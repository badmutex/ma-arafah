
package tajmi.data.clusterable.instances.som;

import tajmi.data.clusterable.interfaces.som.ShowStatusFunc;
import tajmi.data.som.SOMParams;

/**
 *
 * @author badi
 */
public class SimpleShowStatusFunc implements ShowStatusFunc {

    SOMParams state;

    public ShowStatusFunc params(SOMParams state) {
        this.state = state;

        return this;
    }

    public Void call() {

        int i = state.iterations;
        int p = state.projections;

        System.out.println("[" + i + "]\t[" + p + "]");

        return null;
    }


}
