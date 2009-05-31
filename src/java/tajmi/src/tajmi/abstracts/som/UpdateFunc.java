
package tajmi.abstracts.som;

import java.util.concurrent.Callable;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 * Updates the field after determining the best matching unit (bmu) to a datum projected.
 * @author badi
 */
public abstract class UpdateFunc implements Callable<Field> {


    Field field;
    Object datum;
    Position bmu_pos;
    double learning_restraint;

    public Position getBestMatchingUnitPosition() {
        return bmu_pos;
    }

    public Object getDatum() {
        return datum;
    }

    public Field getField() {
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
    public UpdateFunc params(Field field, Object datum, Position bmu_pos, double learning_restraint) {
        this.field = field;
        this.datum = datum;
        this.bmu_pos = bmu_pos;
        this.learning_restraint = learning_restraint;

        return this;
    }


    public abstract Field call ();

}
