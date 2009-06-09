
package tajmi.functional.instances.seq;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import tajmi.functional.interfaces.Fun;

/**
 * Returns an iteration over the initial sequence minus it's first element<br><br>
 *
 * <code>Tail :: [a] -> [a]</code>
 * @author badi
 */
public class Tail implements Fun {

    Iterable seq;

    public Fun copy() {
        return new Tail().curry(seq);
    }

    public Fun curry(Object arg) {
        if (seq == null)
            seq = (Iterable) arg;

        return this;
    }

    public Iterable call() throws Exception {
        Iterator itr = seq.iterator();
        List l = new LinkedList();
        itr.next();
        while (itr.hasNext())
            l.add(itr.next());
        return l;
    }

}
