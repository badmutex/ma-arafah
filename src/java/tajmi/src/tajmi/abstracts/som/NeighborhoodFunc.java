
package tajmi.abstracts.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public abstract class NeighborhoodFunc implements Callable<Double> {

    Position origin, target;

    public Position getOrigin() {
        return origin;
    }

    public Position getTarget() {
        return target;
    }
    
    public NeighborhoodFunc params(Position origin, Position target) {
        this.origin = origin;
        this.target = target;
        return this;
    }

    public abstract Double call();

}
