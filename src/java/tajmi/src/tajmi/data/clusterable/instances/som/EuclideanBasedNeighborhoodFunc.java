
package tajmi.data.clusterable.instances.som;

import tajmi.data.clusterable.interfaces.som.NeighborhoodFunc;
import tajmi.data.som.Position;

/**
 *
 * @author badi
 */
public class EuclideanBasedNeighborhoodFunc implements NeighborhoodFunc {

    Position first, second;

    public NeighborhoodFunc params(Position first, Position second) {
        this.first = first;
        this.second = second;
        return this;
    }

    public Double call() {
        if (first.equals(second))
            return 1.0;
        else {
            double dist = new PositionDistanceFunc().params(first, second).call();
            return (1.0 / dist);
        }
    }

}
