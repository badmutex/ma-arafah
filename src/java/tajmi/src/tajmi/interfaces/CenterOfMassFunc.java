
package tajmi.interfaces;

import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public interface CenterOfMassFunc<T> extends  Fun {

    public CenterOfMassFunc<T> params(Iterable<T> cluster);

    public T call ();
}
