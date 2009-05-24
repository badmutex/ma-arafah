
package tajmi.data.clusterable.cdk;

import java.util.Iterator;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.Util;
import tajmi.data.clusterable.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class AtomContainer_CenterOfMassAlgorithm implements CenterOfMassFunc<IAtomContainer> {

    Iterable<IAtomContainer> cluster;

    public AtomContainer_CenterOfMassAlgorithm params(Iterable<IAtomContainer> cluster) {

        this.cluster = cluster;
        return this;
    }

    public IAtomContainer call () {
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
