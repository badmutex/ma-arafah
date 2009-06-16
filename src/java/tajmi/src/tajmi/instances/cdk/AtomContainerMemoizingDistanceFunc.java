
package tajmi.instances.cdk;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.Universe;
import tajmi.Util;
import tajmi.abstracts.DistanceFunc;

/**
 *
 * @author badi
 */
public class AtomContainerMemoizingDistanceFunc extends DistanceFunc<IAtomContainer,IAtomContainer> {

    public Double call () {
        IAtomContainer g_1 = getFirst(),
                g_2 = getSecond();

        System.out.println(g_1.getID() + " <-> " + g_2.getID());

        Universe state = Universe.getInstance();
        String n1 = g_1.getID(),
                n2 = g_2.getID();
        if (state.distance_exists(n1, n2)) {
            System.out.println("\tskipping");
            return state.get_distance(n1, n2);
        } else {
            System.out.println("\tRunning mcss algorithm");

            IAtomContainer mcss = Util.mcss(g_1, g_2);

        if (mcss == null) {
            return Double.NEGATIVE_INFINITY;
        } else {
            IAtomContainer
                    g1 = AtomContainerManipulator.removeHydrogens(g_1),
                    g2 = AtomContainerManipulator.removeHydrogens(g_2);
            double distance = 1.0 - (mcss.getAtomCount() + 0.0) / Math.max(g1.getAtomCount(), g2.getAtomCount());

            state.add_distance(n1, n2, distance);
            return distance;
        }
        }
    }
}
