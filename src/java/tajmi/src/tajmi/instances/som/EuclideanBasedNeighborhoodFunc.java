
package tajmi.instances.som;

import tajmi.interfaces.som.NeighborhoodFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class EuclideanBasedNeighborhoodFunc extends NeighborhoodFunc {

    public Double call() {
        Position first = getFirst(),
                second = getSecond();
        
        if (first.equals(second))
            return 1.0;
        else {
            double dist = new PositionDistanceFunc().params(first, second).call();
            return (1.0 / dist);
        }
    }

}
