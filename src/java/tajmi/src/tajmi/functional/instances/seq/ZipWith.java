
package tajmi.functional.instances.seq;

import java.util.Iterator;
import tajmi.functional.interfaces.Fun;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * <code>ZipWith :: (a -> b -> c) -> [a] -> [b] -> [c]</code>
 * @author badi
 */
public class ZipWith<A,B,C> implements Fun {

    Fun f;
    Iterable<A> as;
    Iterable<B> bs;

    public Fun copy() {
        return new ZipWith().curry(f).curry(as).curry(bs);
    }

    public Fun curry(Object arg) {
        if (f == null)
            f = (Fun) arg;
        else if (as == null)
            as = (Iterable<A>) arg;
        else if (bs == null)
            bs = (Iterable<B>) arg;

        return this;
    }

    public Iterable<C> call()  {
        Iterator<A> itrA = as.iterator();
        Iterator<B> itrB = bs.iterator();
        List<C> result = new LinkedList<C>();
        while(itrA.hasNext() && itrB.hasNext()) {
            Fun f2 = f.copy();
            A a = itrA.next();
            B b = itrB.next();
            C c = (C) f2.curry(a).curry(b).call();
            result.add(c);
        }
        return result;
    }

}
