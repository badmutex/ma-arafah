
package tajmi.instances.cdk.som;

import java.util.List;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.DistanceFunc;
import tajmi.instances.cdk.AtomContainerMemoizingDistanceFunc;

/**
 * Given an IAtomContainer <code>M</code> and a list of IAtomContainers <code>L</code>,
 * return the sum of the distances between <code>M</code> and each <code>m</code> in <code>L</code>.
 * @author badi
 */
public class AtomContainerListDistanceFunc extends DistanceFunc<IAtomContainer, List<IAtomContainer>> {

    @Override
    public Double call() {
        IAtomContainer molecule = getFirst();
        List<IAtomContainer> others = getSecond();

        DistanceFunc<IAtomContainer, IAtomContainer> distancef = new AtomContainerMemoizingDistanceFunc();

        double distance = 0.0;
        for (IAtomContainer other : others)
            distance += distancef.params(molecule, other).call();

        return distance;
    }

}
