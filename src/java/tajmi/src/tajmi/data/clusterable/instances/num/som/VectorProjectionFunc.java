
package tajmi.data.clusterable.instances.num.som;

import scala.Tuple2;
import tajmi.data.clusterable.instances.num.Vector;
import tajmi.data.clusterable.instances.num.Vector_DistanceAlgorithm;
import tajmi.data.clusterable.instances.som.NaiveFindBestMatchFunc;
import tajmi.data.clusterable.instances.som.PositionDistanceFunc;
import tajmi.data.clusterable.interfaces.DistanceFunc;
import tajmi.data.clusterable.interfaces.som.FindBestMatchFunc;
import tajmi.data.clusterable.interfaces.som.ProjectionFunc;
import tajmi.data.clusterable.interfaces.som.UpdateFunc;
import tajmi.data.som.Field;
import tajmi.data.som.Position;
import tajmi.data.som.SOMConfig;
import tajmi.data.som.SOMParams;

/**
 *
 * @author badi
 */
public class VectorProjectionFunc implements ProjectionFunc<Vector> {

    Vector datum;
    SOMParams<Vector> state;

    FindBestMatchFunc<Vector> find_best_match;
    DistanceFunc<Vector> distancef;
    UpdateFunc<Vector> updatef;

    public ProjectionFunc<Vector> params(Vector datum, SOMParams<Vector> state) {
        this.datum = datum;
        this.state = state;

        find_best_match = new NaiveFindBestMatchFunc<Vector>();
        distancef = new Vector_DistanceAlgorithm();
        updatef = new VectorUpdateFunc();

        return this;
    }

    public SOMParams<Vector> call() {
        Tuple2<Position, Vector> res = find_best_match.params(state.field, datum, distancef).call();
        Position best_pos = res._1();
        Vector best = res._2();

        Field<Vector> new_field = updatef.params(state.field, datum, best_pos, state.learning_restraint).call();

        double new_restraint = state.learning_restraint * SOMConfig.getInstance().restraint_modifier();

        SOMParams<Vector> new_state = state.copy();
        new_state.field = new_field;
        new_state.learning_restraint = new_restraint;

        return new_state;
    }

}
