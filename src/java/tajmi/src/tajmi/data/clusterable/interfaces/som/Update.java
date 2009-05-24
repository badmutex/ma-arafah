
package tajmi.data.clusterable.interfaces.som;

import java.util.concurrent.Callable;
import tajmi.data.som.Field;

/**
 *
 * @author badi
 */
public interface Update<T> extends Callable<Field<T>> {

    /**
     * @param field
     * @param datum the input datum being projected onto the field
     * @param bmu the best matching unit
     * @param restraint determined by the neightborhood function
     * @return
     */
    public Update<T> params (Field<T> field, T datum, T bmu, double restraint);


    public Field<T> call ();

}
