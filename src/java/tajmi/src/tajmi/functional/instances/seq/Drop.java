
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import tajmi.functional.interfaces.Fun;

/**
 *
 * <code>Drop :: Integer -> [a] -> [a]</code>
 * @author badi
 */
public class Drop implements Fun {

    Integer n;
    Iterable i;

    public Fun copy() {
        return new Drop().curry(n).curry(i);
    }

    public Fun curry(Object arg) {
        if (n == null)
            n = (Integer) arg;
        else if (i == null)
            i = (Iterable) arg;

        return this;
    }

    public Iterable call() {
        if (n > 0)
            return Seq.drop(n-1, Seq.tail(i));
        else return i;
    }

}
