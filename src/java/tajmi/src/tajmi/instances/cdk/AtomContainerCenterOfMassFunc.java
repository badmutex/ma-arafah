
package tajmi.instances.cdk;

import java.util.Iterator;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.Util;
import tajmi.interfaces.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class AtomContainerCenterOfMassFunc implements CenterOfMassFunc<IAtomContainer> {

    Iterable<IAtomContainer> cluster;

    public AtomContainerCenterOfMassFunc params(Iterable<IAtomContainer> cluster) {

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
