
package tajmi.interfaces.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface NeighborhoodFunc extends Callable<Double> {

    public NeighborhoodFunc params (Position first, Position second);

    public Double call();

}
