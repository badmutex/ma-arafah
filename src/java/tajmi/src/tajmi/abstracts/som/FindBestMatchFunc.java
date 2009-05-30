
package tajmi.abstracts.som;

import java.util.concurrent.Callable;
import scala.Tuple2;
import tajmi.abstracts.DistanceFunc;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public abstract class FindBestMatchFunc<T> implements Callable<Tuple2<Position,T>> {

    Field<T> field;
    T datum;
    DistanceFunc<T> distancef;

    public T getDatum() {
        return datum;
    }

    public DistanceFunc<T> getDistancef() {
        return distancef;
    }

    public Field<T> getField() {
        return field;
    }

    public FindBestMatchFunc params(Field<T> field, T datum, DistanceFunc<T> distancef) {
        this.field = field;
        this.datum = datum;
        this.distancef = distancef;

        return this;
    }

    public abstract Tuple2<Position, T> call ();

}
