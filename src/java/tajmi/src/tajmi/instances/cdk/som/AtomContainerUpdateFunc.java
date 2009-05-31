package tajmi.instances.cdk.som;

import org.openscience.cdk.interfaces.IAtomContainer;
import java.util.List;
import tajmi.abstracts.som.UpdateFunc;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class AtomContainerUpdateFunc extends UpdateFunc<List<IAtomContainer>, IAtomContainer> {

    @Override
    public Field<List<IAtomContainer>> call() {
        Field<List<IAtomContainer>> field = getField();
        IAtomContainer datum = getDatum();
        Position bmu_pos = getBestMatchingUnitPosition();

        field.get(bmu_pos).add(datum);

        return field;
    }






}
