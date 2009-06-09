
package tajmi.instances.som;

import tajmi.abstracts.DistanceFunc;
import tajmi.abstracts.som.FindBestMatchFunc;
import tajmi.abstracts.som.ProjectionFunc;
import tajmi.abstracts.som.UpdateFunc;
import tajmi.som.Field;
import tajmi.som.Position;
import tajmi.som.SOMParams;
import scala.Tuple2;
/**
 *
 * @author badi
 */
public class GeneralProjectionFunc<F,D> extends ProjectionFunc<F,D> {

    @Override
    public SOMParams<F,D> call() throws Exception {
        SOMParams<F,D> state = getState();
        D datum = getDatum();

        FindBestMatchFunc<F,D> find_best_match = getFindBestMatchFunc();
        DistanceFunc distancef = getDistanceFunc();
        UpdateFunc<F,D> updatef = getUpdateFunc();


        /* implementation: */

        // 1) find the best matching unit
        Tuple2<Position, F> res = find_best_match.params(state.field, datum, distancef).call();
        Position best_pos = res._1();

        // 2) update the field using the restraint due to distance and time
        Field<F> new_field = updatef.params(
                  state.field
                , datum
                , best_pos
                , state.learning_restraint
                , getNeighborhoodFunc()
                ).call();

        double new_restraint = state.learning_restraint * state.restraint_modifier;

        // 3) done. copy into a new state to return
        SOMParams<F,D> new_state = state.copy();
        new_state.field = new_field;
        new_state.learning_restraint = new_restraint;

        // ok!
        return new_state;
    }

}
