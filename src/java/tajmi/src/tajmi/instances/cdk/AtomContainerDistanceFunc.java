
package tajmi.instances.cdk;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.Util;
import tajmi.abstracts.DistanceFunc;

/**
 *
 * @author badi
 */
public class AtomContainerDistanceFunc extends DistanceFunc<IAtomContainer,IAtomContainer> {

    public Double call () {
        IAtomContainer g_1 = getFirst(),
                g_2 = getSecond();


        IAtomContainer mcss = Util.mcss(g_1, g_2);

        if (mcss == null) {
            return Double.NEGATIVE_INFINITY;
        } else {
            IAtomContainer
                    g1 = AtomContainerManipulator.removeHydrogens(g_1),
                    g2 = AtomContainerManipulator.removeHydrogens(g_2);
            double distance = 1.0 - (mcss.getAtomCount() + 0.0) / Math.max(g1.getAtomCount(), g2.getAtomCount());

            return distance;
        }
    }
}
