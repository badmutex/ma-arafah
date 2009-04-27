/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tajmi.data.clusterable.cdk;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.data.clusterable.DistanceAlgorithm;

/**
 *
 * @author badi
 */
public class AtomContainer_DistanceAlgorithm implements DistanceAlgorithm<IAtomContainer> {

    /**
     * Compares two IAtomContainer object from CDK using the maximum common subgraph between them. <br>
     * <code>
     * distance(g1,g2) = 1 - |V(mcss(g1,g2)| / max(|V(g1)|, |V(g2)|)
     * </code>
     * @param g_1 the first molecule
     * @param g_2 the second molecule
     * @return the distance in range [0,1] or -1 if the computation fails
     */
    public double distance(IAtomContainer g_1, IAtomContainer g_2) {
        try {
            IAtomContainer g1 = AtomContainerManipulator.removeHydrogens(g_1);
            IAtomContainer g2 = AtomContainerManipulator.removeHydrogens(g_2);
            List<IAtomContainer> mcss_list = UniversalIsomorphismTester.getOverlaps(g1, g2);
            int max_mcss = Integer.MIN_VALUE;
            IAtomContainer mcss = null;
            for (IAtomContainer cs : mcss_list) {
                if (cs.getAtomCount() > max_mcss) {
                    max_mcss = cs.getAtomCount();
                    mcss = cs;
                }
            }
            double distance = 1.0 - (mcss.getAtomCount() + 0.0) / Math.max(g1.getAtomCount(), g2.getAtomCount());

            return distance;
            
        } catch (CDKException ex) {
            Logger.getLogger(AtomContainer_DistanceAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }


}
