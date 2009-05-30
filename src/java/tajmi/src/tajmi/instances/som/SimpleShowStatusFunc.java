
package tajmi.instances.som;

import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.som.SOMParams;

/**
 *
 * @author badi
 */
public class SimpleShowStatusFunc extends ShowStatusFunc {

    public Void call() {

        SOMParams state = getState();
        
        int i = state.iterations;
        int p = state.projections;

        System.out.println("[" + i + "]\t[" + p + "]");

        return null;
    }

}
