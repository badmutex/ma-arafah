
package tajmi.instances.som;

import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.som.SOMParams;

/**
 *
 * @author badi
 */
public class SimpleShowStatusFunc extends ShowStatusFunc {

    @Override
    public void update_status_verbosly() {
        SOMParams state = getState();

        int i = state.iterations;
        int p = state.projections;

        System.out.println("[" + i + "]\t[" + p + "]");
    }

    @Override
    public void update_status_very_verbosly() {
    }

    @Override
    public void update_status_everything() {
    }

}
