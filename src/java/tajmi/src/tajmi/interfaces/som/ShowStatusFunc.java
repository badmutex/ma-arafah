
package tajmi.interfaces.som;

import java.util.concurrent.Callable;
import tajmi.som.SOMParams;

/**
 * Informs the user of the current state of the program
 * @author badi
 */
public interface ShowStatusFunc extends Callable<Void> {

    public ShowStatusFunc params (SOMParams state);

    public Void call () ;
}
