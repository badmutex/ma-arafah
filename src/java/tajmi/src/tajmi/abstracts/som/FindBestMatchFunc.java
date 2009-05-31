
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
public abstract class FindBestMatchFunc implements Callable<Tuple2<Position,Object>> {

    Field field;
    Object datum;
    DistanceFunc distancef;

    public Object getDatum() {
        return datum;
    }

    public DistanceFunc getDistancef() {
        return distancef;
    }

    public Field getField() {
        return field;
    }

    public FindBestMatchFunc params(Field field, Object datum, DistanceFunc distancef) {
        this.field = field;
        this.datum = datum;
        this.distancef = distancef;

        return this;
    }

    public abstract Tuple2<Position, Object> call ();

}
