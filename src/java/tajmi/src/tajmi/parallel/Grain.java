
package tajmi.parallel;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface Grain<T> extends Callable<T> {

    public T call ();

    /** Injects the grain of computation into a callable process */
    public void ingrain (Object grain);

}
