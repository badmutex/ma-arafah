
package tajmi.data.clusterable.interfaces.som;

import java.util.concurrent.Callable;
import tajmi.data.som.Field;
import tajmi.data.som.Position;

/**
 *
 * @author badi
 */
public interface UpdateFunc<T> extends Callable<Field<T>> {

    /**
     * @param field
     * @param datum the input datum being projected onto the field
     * @param bmu the best matching unit
     * @param restraint determined by the neightborhood function
     * @return
     */
    public UpdateFunc<T> params (Field<T> field, T datum, Position bmu_pos, double restraint);


    public Field<T> call ();

}
