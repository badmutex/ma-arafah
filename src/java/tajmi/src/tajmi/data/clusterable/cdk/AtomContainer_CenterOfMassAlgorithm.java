/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tajmi.data.clusterable.cdk;

import java.util.Iterator;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.Util;
import tajmi.data.clusterable.CenterOfMassAlgorithm;

/**
 *
 * @author badi
 */
public class AtomContainer_CenterOfMassAlgorithm implements CenterOfMassAlgorithm<IAtomContainer> {

    /**
     * Finds the center of mass of the cluster of IAtomContainer objects by folding
     * a maximum common subgraph computation through it sequence
     * @param cluster sequence of IAtomContainer instances. There should be at least one item in the sequence
     * @return result of the fold
     */
    public IAtomContainer center(Iterable<IAtomContainer> cluster) {

        Iterator<IAtomContainer> itr = cluster.iterator();
        IAtomContainer accum = itr.next();
        if (!itr.hasNext()) {
            return accum;
        } else {
            while (itr.hasNext()) {
                accum = Util.mcss(accum, itr.next());
            }
            return accum;
        }
    }
}
