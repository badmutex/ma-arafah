
package tajmi.interfaces.som;

import java.util.concurrent.Callable;
import scala.Tuple2;
import tajmi.interfaces.DistanceFunc;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public interface FindBestMatchFunc<T> extends Callable<Tuple2<Position,T>> {

    public FindBestMatchFunc params (Field<T> field, T datum, DistanceFunc<T> distancef);

    public Tuple2<Position, T> call ();

}
