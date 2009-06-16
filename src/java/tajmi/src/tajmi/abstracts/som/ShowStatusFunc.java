
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

    public abstract void update_status_verbosly ();
    public abstract void update_status_very_verbosly ();
    public abstract void update_status_everything ();

}
