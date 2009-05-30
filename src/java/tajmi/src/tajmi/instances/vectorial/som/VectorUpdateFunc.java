package tajmi.instances.vectorial.som;

import java.util.ArrayList;
import java.util.List;
import scala.Tuple2;
import tajmi.instances.vectorial.Vector;
import tajmi.instances.som.PositionDistanceFunc;
import tajmi.abstracts.som.UpdateFunc;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 * Wv(t + 1) = Wv(t) + Θ(t)α(t)(D(t) - Wv(t)) <br>
 * where <br>
 * t = current iteration <br>
 * λ = limit on time iteration <br>
 * Wv = current weight vector <br>
 * D = target input <br>
 * Θ(t) = learning_restraint due to distance from BMU - usually called the neighbourhood function <br>
 * α(t) = learning learning_restraint due to time <br>
 * @author badi
 */
public class VectorUpdateFunc extends UpdateFunc<Vector> {

    @Override
    public Field<Vector> call() {
        Field<Vector> field = getField();
        Vector datum = getDatum();
        Position bmu_pos = getBestMatchingUnitPosition();
        double learning_restraint = getLearningRestraint();

        List<Tuple2<Position, Vector>> field_info = new ArrayList<Tuple2<Position, Vector>>(field.size());
        for (Tuple2<Position, Vector> item : field) {
            Position pos = item._1();

            // Wv(t)
            Vector v1 = item._2();

            // D(t) - Wv(t)
            Vector v_ = datum.subtract(v1);

            // Θ(t)
            double bmu_restraint = new PositionDistanceFunc().params(pos, bmu_pos).call();

            // Θ(t)α(t)
            double restraint = bmu_restraint * learning_restraint;

            // Θ(t)α(t)(D(t) - Wv(t))
            Vector v__ = v_.mult(restraint);

            // Wv(t + 1)
            Vector v2 = v1.add(v__);


            Tuple2<Position, Vector> new_item = new Tuple2<Position, Vector>(pos, v2);
            field_info.add(new_item);
        }

        Field<Vector> new_field = new Field<Vector>(field);
        new_field.set(field_info);
        return new_field;
    }
}
