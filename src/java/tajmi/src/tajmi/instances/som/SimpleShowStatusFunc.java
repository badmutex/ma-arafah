
package tajmi.instances.som;

import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.som.SOMParams;

/**
 *
 * @author badi
 */
public class SimpleShowStatusFunc extends ShowStatusFunc {

    @Override
    public String verbose() {
        SOMParams state = getState();

        int i = state.iterations;
        int p = state.projections;

        return "[" + i + "]\t[" + p + "]\n";
    }

    @Override
    public String very_verbose() {
        return "";
    }

    @Override
    public String everything() {
        return "";
    }

}
