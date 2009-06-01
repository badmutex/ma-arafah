package tajmi.instances.cdk.som;

import java.util.LinkedList;
import java.util.List;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.abstracts.som.NeighborhoodFunc;
import tajmi.abstracts.som.UpdateFunc;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class AtomContainerUpdateFunc extends UpdateFunc<FieldModel<IAtomContainer>, IAtomContainer> {

    double distance_cutoff = 0.5;

    public void setDistance_cutoff(double distance_cutoff) {
        this.distance_cutoff = distance_cutoff;
    }

    @Override // TODO: finish AtomContainerUpdateFunc
    public Field<FieldModel<IAtomContainer>> call() {
        Field<FieldModel<IAtomContainer>> field = getField();
        IAtomContainer datum = getDatum();
        Position bmu_pos = getBestMatchingUnitPosition();
        NeighborhoodFunc neighborhoodf = getNeighborhoodFunc();

        // 1) add datum to field under best matching unit (BMU)
        field.get(bmu_pos).add(datum);

        // 2) find the models within the neighborhood of the BMU
        List<FieldModel<IAtomContainer>> would_you_be = new LinkedList<FieldModel<IAtomContainer>>();
        for (Tuple2<Position, FieldModel<IAtomContainer>> model : field) {
            Position pos = model._1();
            FieldModel<IAtomContainer> my_neighbor = model._2();
            double distance = neighborhoodf.params(bmu_pos, pos).call();

            if( distance >= distance_cutoff)
                would_you_be.add(my_neighbor);
        }

        return field;
    }
}
