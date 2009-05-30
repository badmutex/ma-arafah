
package tajmi.instances.cdk;

import java.util.Iterator;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.Util;
import tajmi.functional.interfaces.Fun;
import tajmi.interfaces.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class AtomContainerCenterOfMassFunc implements CenterOfMassFunc<IAtomContainer> {

    Iterable<IAtomContainer> cluster;

    public AtomContainerCenterOfMassFunc params(Iterable<IAtomContainer> cluster) {
        return (AtomContainerCenterOfMassFunc) this.curry(cluster);
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

    public Fun copy() {
        return new AtomContainerCenterOfMassFunc().curry(cluster);
    }

    public Fun curry(Object arg) {
        if (cluster == null)
            cluster = (Iterable<IAtomContainer>) arg;

        return this;
    }
}
