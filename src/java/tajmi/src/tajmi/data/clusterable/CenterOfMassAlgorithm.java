/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tajmi.data.clusterable;

import java.util.concurrent.Callable;

/**
 *
 * @author badi
 */
public interface CenterOfMassAlgorithm<T> extends Callable<T> {

    public CenterOfMassAlgorithm<T> params(Iterable<T> cluster);

    public T call ();
}
