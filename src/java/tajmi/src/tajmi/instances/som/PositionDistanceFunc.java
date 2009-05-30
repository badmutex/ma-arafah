
package tajmi.instances.som;

import tajmi.abstracts.DistanceFunc;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class PositionDistanceFunc extends DistanceFunc<Position> {

    public Double call() {
        Position first = getFirst(),
                second = getSecond();
        
        return Math.sqrt(Math.pow(first.x() - second.x(), 2) + Math.pow(first.y() - second.y(), 2));
    }
}
