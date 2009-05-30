
package tajmi.instances.vectorial.som;

import scala.Tuple2;
import tajmi.instances.vectorial.Vector;
import tajmi.abstracts.DistanceFunc;
import tajmi.abstracts.som.FindBestMatchFunc;
import tajmi.abstracts.som.ProjectionFunc;
import tajmi.abstracts.som.UpdateFunc;
import tajmi.som.Field;
import tajmi.som.Position;
import tajmi.som.SOMParams;

/**
 *
 * @author badi
 */
public class VectorProjectionFunc extends ProjectionFunc<Vector> {

    public SOMParams<Vector> call() {

        SOMParams<Vector> state = getState();
        Vector datum = getDatum();

        FindBestMatchFunc<Vector> find_best_match = getFindBestMatchFunc();
        DistanceFunc<Vector> distancef = getDistanceFunc();
        UpdateFunc<Vector> updatef = getUpdateFunc();


        /* implementation: */

        // 1) find the best matching unit
        Tuple2<Position, Vector> res = find_best_match.params(state.field, datum, distancef).call();
        Position best_pos = res._1();

        // 2) update the field weights using the restraint due to distance and time
        Field<Vector> new_field = updatef.params(state.field, datum, best_pos, state.learning_restraint).call();

        double new_restraint = state.learning_restraint * state.restraint_modifier;

        // 3) done. copy into a new state to return
        SOMParams<Vector> new_state = state.copy();
        new_state.field = new_field;
        new_state.learning_restraint = new_restraint;

        // ok!
        return new_state;
    }

}
