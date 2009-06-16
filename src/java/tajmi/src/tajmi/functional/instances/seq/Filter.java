
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import tajmi.functional.interfaces.Fun;
import tajmi.functional.interfaces.Result;

/**
 *
 * <code>Filter :: (a -> Boolean) -> [a] -> [a]</code>
 * @author badi
 */
public class Filter<A> implements Fun {

    Fun f;
    Iterable<A> as;

    public Fun copy() {
        return new Filter<A>().curry(f).curry(as);
    }

    public Fun curry(Object arg) {
        if (f == null)
            f = (Fun) arg;
        else if (as == null)
            as = (Iterable<A>) arg;

        return this;
    }

    public List<A> call()  {
        List<A> acceptable = new LinkedList<A>();
        for (A a : as) {
            Boolean accept = (Boolean) f.copy().curry(a).call();
            if (accept)
                acceptable.add(a);
        }

        return acceptable;
    }

}
