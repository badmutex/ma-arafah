package tajmi.instances.cdk.som;

import org.openscience.cdk.interfaces.IAtomContainer;
import java.util.List;
import tajmi.abstracts.som.NeighborhoodFunc;
import tajmi.abstracts.som.UpdateFunc;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class AtomContainerUpdateFunc extends UpdateFunc<List<IAtomContainer>, IAtomContainer> {

    double distance_cutoff = 0.5;

    public void setDistance_cutoff(double distance_cutoff) {
        this.distance_cutoff = distance_cutoff;
    }

    @Override // TODO: finish AtomContainerUpdateFunc
    public Field<List<IAtomContainer>> call() {
        Field<List<IAtomContainer>> field = getField();
        IAtomContainer datum = getDatum();
        Position bmu_pos = getBestMatchingUnitPosition();
        NeighborhoodFunc neighbourhoodf = getNeighborhoodFunc();

        // 1) add datum to field under best matching unit
        field.get(bmu_pos).add(datum);

        

        return field;
    }
}
