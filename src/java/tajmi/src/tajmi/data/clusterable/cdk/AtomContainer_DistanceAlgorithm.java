/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tajmi.data.clusterable.cdk;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.Util;
import tajmi.data.clusterable.DistanceAlgorithm;

/**
 *
 * @author badi
 */
public class AtomContainer_DistanceAlgorithm implements DistanceAlgorithm<IAtomContainer> {

    IAtomContainer g_1, g_2;


    /**
     * Compares two IAtomContainer object from CDK using the maximum common subgraph between them. <br>
     * <code>
     * params(g1,g2) = 1 - |V(mcss(g1,g2)| / max(|V(g1)|, |V(g2)|)
     * </code>
     * @param g_1 the first molecule
     * @param g_2 the second molecule
     * @return the params in range [0,1] or -1 if the computation fails
     */
    public AtomContainer_DistanceAlgorithm params(IAtomContainer g_1, IAtomContainer g_2) {
        this.g_1 = g_1;
        this.g_2 = g_2;

        return this;
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
}
