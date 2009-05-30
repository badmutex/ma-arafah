
package tajmi.instances.cdk;

import java.util.Iterator;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.Util;
import tajmi.abstracts.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class AtomContainerCenterOfMassFunc extends CenterOfMassFunc<IAtomContainer> {

    public IAtomContainer call () {
        Iterable<IAtomContainer> cluster = getCluster();

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
