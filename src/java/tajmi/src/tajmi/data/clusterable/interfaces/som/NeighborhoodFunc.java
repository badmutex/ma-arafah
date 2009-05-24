
package tajmi.data.clusterable.interfaces.som;

import tajmi.data.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface NeighborhoodFunc extends Callable<Double> {

    public NeighborhoodFunc params (Position first, Position second, double restraint);

    public Double call();

}
