
package tajmi.interfaces;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public abstract class CenterOfMassFunc<T> implements Callable<T> {

    Iterable<T> cluster;

    public Iterable<T> getCluster() {
        return cluster;
    }

    public CenterOfMassFunc params(Iterable<T> cluster) {
        this.cluster = cluster;
        return this;
    }

    public abstract T call ();
}
