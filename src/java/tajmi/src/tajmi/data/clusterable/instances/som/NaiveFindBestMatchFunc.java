
package tajmi.data.clusterable.instances.som;

import scala.Tuple2;
import tajmi.data.clusterable.interfaces.DistanceFunc;
import tajmi.data.clusterable.interfaces.som.FindBestMatchFunc;
import tajmi.data.som.Field;
import tajmi.data.som.Position;

/**
 *
 * @author badi
 */
public class NaiveFindBestMatchFunc<T> implements FindBestMatchFunc<T> {

    Field<T> field;
    T datum;
    DistanceFunc<T> distancef;

    public FindBestMatchFunc params(Field<T> field, T datum, DistanceFunc<T> distancef) {
        this.field = field;
        this.datum = datum;
        this.distancef = distancef;

        return this;
    }

    public Tuple2<Position, T> call() {

        double best_distance = Double.POSITIVE_INFINITY,
                current_distance = Double.POSITIVE_INFINITY;
        Tuple2<Position, T> best = null;
        for (Tuple2<Position, T> elem : field) {
            if (best == null) {
                best = elem;
                continue;
            } else {
                current_distance = distancef.params(best._2(), elem._2()).call();
                if (current_distance < best_distance)
                    best = elem;
            }
        }
        return best;
    }

}
