
package tajmi.interfaces.som;

import java.util.concurrent.Callable;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 * Updates the field after determining the best matching unit (bmu) to a datum projected.
 * @author badi
 */
public abstract class UpdateFunc<T> implements Callable<Field<T>> {


    Field<T> field;
    T datum;
    Position bmu_pos;
    double learning_restraint;

    public Position getBestMatchingUnitPosition() {
        return bmu_pos;
    }

    public T getDatum() {
        return datum;
    }

    public Field<T> getField() {
        return field;
    }

    public double getLearningRestraint() {
        return learning_restraint;
    }



    /**
     * @param field
     * @param datum the input datum being projected onto the field
     * @param bmu the best matching unit
     * @param restraint determined by the neightborhood function
     * @return
     */
    public UpdateFunc<T> params(Field<T> field, T datum, Position bmu_pos, double learning_restraint) {
        this.field = field;
        this.datum = datum;
        this.bmu_pos = bmu_pos;
        this.learning_restraint = learning_restraint;

        return this;
    }


    public abstract Field<T> call ();

}
