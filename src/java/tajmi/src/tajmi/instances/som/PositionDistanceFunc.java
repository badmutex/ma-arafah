
package tajmi.instances.som;

import tajmi.functional.interfaces.Fun;
import tajmi.interfaces.DistanceFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class PositionDistanceFunc implements DistanceFunc<Position> {

    Position first, second;

    public DistanceFunc params(Position first, Position second) {
        return (DistanceFunc) this.curry(first).curry(second);
    }

    public Double call() {
        return Math.sqrt(Math.pow(first.x() - second.x(), 2) + Math.pow(first.y() - second.y(), 2));
    }

    public Fun copy() {
        return new PositionDistanceFunc().curry(first).curry(second);
    }

    public Fun curry(Object arg) {
        if (first == null)
            first = (Position) arg;
        else if (second == null)
            second = (Position) arg;

        return this;
    }

}
