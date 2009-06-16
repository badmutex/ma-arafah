
package tajmi.functional.instances.seq;

import tajmi.functional.Ord;
import tajmi.functional.interfaces.Fun;

/**
 * Finds the minimum element in an iterable <code>[a]</code>
 * using the specified function <code>(a -> a -> Ord)</code><br><br>
 * 
 * <code>MinimumBy :: (a -> a -> Ord) -> [a] -> a</code> <br><br>
 *
 * @see tajmi.functional.Ord
 * @author badi
 */
public class MinimumBy implements Fun {

    Fun compare;
    Iterable<?> seq;

    public Fun copy() {
        return new MinimumBy().curry(compare).curry(seq);
    }

    public Fun curry(Object arg) {
        if (compare == null)
            compare = (Fun) arg;
        else if (seq == null)
            seq = (Iterable<?>) arg;

        return this;
    }

    public Object call()  {
        Object best = new Head().curry(seq).call();
        Iterable rest = (Iterable) new Tail().curry(seq).call();
        for (Object o : rest) {
            Fun cmp2 = compare.copy();
            Ord ord = (Ord) cmp2.curry(best).curry(o).call();
            if (ord.equals(Ord.GT))
                best = o;
        }
        return best;
    }

}
