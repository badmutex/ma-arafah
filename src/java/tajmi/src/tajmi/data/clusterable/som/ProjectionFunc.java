
package tajmi.data.clusterable.som;

import tajmi.data.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface ProjectionFunc<T> extends Callable<SOMParams<T>> {

    public ProjectionFunc<T> params (T datum, SOMParams<T> current_state);

    public SOMParams<T> call();

}
