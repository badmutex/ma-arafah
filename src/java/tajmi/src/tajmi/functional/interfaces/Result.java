
package tajmi.functional.interfaces;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface Result<R> extends Callable<R> {

    public R call ();
}
