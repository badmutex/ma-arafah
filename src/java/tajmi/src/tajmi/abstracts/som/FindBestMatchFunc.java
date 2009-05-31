
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
public abstract class FindBestMatchFunc<F,D> implements Callable<Tuple2<Position,F>> {

    Field<F> field;
    D datum;
    DistanceFunc distancef;

    public D getDatum() {
        return datum;
    }

    public DistanceFunc getDistancef() {
        return distancef;
    }

    public Field<F> getField() {
        return field;
    }

    public FindBestMatchFunc params(Field<F> field, D datum, DistanceFunc distancef) {
        this.field = field;
        this.datum = datum;
        this.distancef = distancef;

        return this;
    }

    public abstract Tuple2<Position, F> call ();

}
