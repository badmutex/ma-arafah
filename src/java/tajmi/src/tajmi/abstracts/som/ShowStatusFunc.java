
package tajmi.abstracts.som;

import tajmi.som.SOMParams;

/**
 * Informs the user of the current state of the program
 * @author badi
 */
public abstract class ShowStatusFunc  {

    SOMParams state;

    public SOMParams getState() {
        return state;
    }

    public ShowStatusFunc params(SOMParams state) {
        this.state = state;

        return this;
    }

    public abstract String verbose ();
    public abstract String very_verbose ();
    public abstract String everything ();

}
