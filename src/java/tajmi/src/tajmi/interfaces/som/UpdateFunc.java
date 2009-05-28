
package tajmi.interfaces.som;

import java.util.concurrent.Callable;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 * Updates the field after determining the best matching unit (bmu) to a datum projected.
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
