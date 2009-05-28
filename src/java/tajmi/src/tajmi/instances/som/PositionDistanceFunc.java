
package tajmi.instances.som;

import tajmi.interfaces.DistanceFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class PositionDistanceFunc implements DistanceFunc<Position> {

    Position first, second;

    public DistanceFunc params(Position first, Position second) {
        this.first = first;
        this.second = second;
        return this;
    }

    public Double call() {
        return Math.sqrt(Math.pow(first.x() - second.x(), 2) + Math.pow(first.y() - second.y(), 2));
    }

}
