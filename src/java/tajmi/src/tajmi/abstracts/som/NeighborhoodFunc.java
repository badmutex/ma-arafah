
package tajmi.abstracts.som;

import tajmi.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public abstract class NeighborhoodFunc implements Callable<Double> {

    Position first, second;

    public Position getFirst() {
        return first;
    }

    public Position getSecond() {
        return second;
    }
    
    public NeighborhoodFunc params(Position first, Position second) {
        this.first = first;
        this.second = second;
        return this;
    }

    public abstract Double call();

}
