
package tajmi.functional.instances.seq;

import tajmi.functional.interfaces.Fun;
import java.util.LinkedList;
import java.util.List;


/**
 * Maps the function <code>(a -> b)</code> over the iterable <code>[a]</code>
 * to produce a new iteration <code>[b]</code><br><br>
 * 
 * <code>Map :: (a -> b) -> [a] -> [b]<br>
 * @author badi
 */
public class Map implements Fun {

    /** First argument */
    Fun f;
    /** Second argument */
    Iterable l;

    public Fun curry(Object arg) {

        if (f == null) {
            f = (Fun) arg;
        }
        else if (l == null) {
            l = (Iterable) arg;
        }

        return this;
    }

    public List call()  {

        List res = new LinkedList();
        for (Object o : l) {
            Fun f2 = f.copy();
            Object r = f2.curry(o).call();
            res.add(r);
        }
        return res;
    }

    public Fun copy() {
        return new Map().curry(f).curry(l);
    }

  

}
