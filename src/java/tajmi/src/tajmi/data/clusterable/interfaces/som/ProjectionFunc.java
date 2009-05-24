
package tajmi.data.clusterable.interfaces.som;

import tajmi.data.som.*;
import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface ProjectionFunc<T> extends Callable<Field<T>> {

    public ProjectionFunc<T> params (T datum, Field<T> field);

    public Field<T> call();

}
