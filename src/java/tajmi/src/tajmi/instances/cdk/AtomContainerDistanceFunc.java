
package tajmi.instances.cdk;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.Util;
import tajmi.functional.interfaces.Fun;
import tajmi.interfaces.DistanceFunc;

/**
 *
 * @author badi
 */
public class AtomContainerDistanceFunc implements DistanceFunc<IAtomContainer> {

    IAtomContainer g_1, g_2;


    public AtomContainerDistanceFunc params(IAtomContainer g_1, IAtomContainer g_2) {
        return (AtomContainerDistanceFunc) this.curry(g_1).curry(g_2);
    }

    public Double call () {
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

    public Fun copy() {
        return new AtomContainerDistanceFunc().curry(g_1).curry(g_2);
    }

    public Fun curry(Object arg) {
        if (g_1 == null)
            g_1 = (IAtomContainer) arg;
        else if (g_2 == null)
            g_2 = (IAtomContainer) arg;

        return this;
    }
}
