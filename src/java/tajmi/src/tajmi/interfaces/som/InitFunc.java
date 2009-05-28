
package tajmi.interfaces.som;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import scala.Tuple2;

/**
 * Is used when initializing the tajmi.som.Field <br>
 * Is allowed to save state between calls
 * @author badi
 */
public interface InitFunc<T> extends Callable< Tuple2< T,Random >> {

    public InitFunc<T> params (List<T> seed, Random randgen);

    public Tuple2< T,Random > call ();

}
