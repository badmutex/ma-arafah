
package tajmi.data.clusterable.instances.som;

import tajmi.data.clusterable.interfaces.som.NeighborhoodFunc;
import tajmi.data.som.Position;

/**
 *
 * @author badi
 */
public class EuclideanBasedNeighborhoodFunc implements NeighborhoodFunc {

    Position first, second;
    double restraint;

    public NeighborhoodFunc params(Position first, Position second, double restraint) {
        this.first = first;
        this.second = second;
        this.restraint = restraint;
        return this;
    }

    public Double call() {
        if (first.equals(second))
            return 1.0;
        else {
            double dist = new PositionDistanceFunc().params(first, second).call();
            return (1.0 / dist) * restraint;
        }
    }

}
