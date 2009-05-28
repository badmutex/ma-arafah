
package tajmi.data.clusterable.interfaces.som;

import java.util.concurrent.Callable;
import tajmi.data.som.SOMParams;

/**
 *
 * @author badi
 */
public interface ShowStatusFunc extends Callable<Void> {

    public ShowStatusFunc params (SOMParams state);

    public Void call () ;
}
