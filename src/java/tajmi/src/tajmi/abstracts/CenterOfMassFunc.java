
package tajmi.abstracts;

import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public abstract class CenterOfMassFunc<T> implements Fun {

    Iterable<T> cluster;

    public Iterable<T> getCluster() {
        return cluster;
    }

    public CenterOfMassFunc params(Iterable<T> cluster) {
        this.cluster = cluster;
        return this;
    }

    public Fun copy() {
        try{
            return this.getClass().newInstance().curry(cluster);
        } catch (Exception ex) {
            throw new RuntimeException(this.getClass() + " failed to copy itself", ex);
        }
    }

    public Fun curry(Object arg) {
        if (cluster == null)
            cluster = (Iterable<T>) arg;

        return this;
    }


    public abstract T call ();
}
