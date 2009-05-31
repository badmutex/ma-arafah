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
public class AtomContainerUpdateFunc extends UpdateFunc {

    @Override
    public Field call() {
        Field field = getField();
        IAtomContainer datum = (IAtomContainer) getDatum();
        Position bmu_pos = getBestMatchingUnitPosition();

        ( (List) field.get(bmu_pos) ).add(datum);

        return field;
    }






}
