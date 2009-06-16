
package tajmi.functional.instances.seq;

import java.util.Iterator;
import tajmi.functional.interfaces.Fun;

/**
 * Returns the first element in the iterable sequence <code>[a]</code><br><br>
 *
 * <code>Head :: [a] -> a</code>
 * @author badi
 */
public class Head<T> implements Fun {

    Iterable<T> seq;

    public Fun copy() {
        return new Head().curry(seq);
    }

    public Fun curry(Object arg) {
        if (seq == null)
            seq = (Iterable<T>) arg;

        return this;
    }

    public T call()  {
        Iterator<T> itr = seq.iterator();
        return itr.next();
    }

}
