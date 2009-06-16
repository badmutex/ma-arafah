
package tajmi.functional.instances.seq;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import tajmi.functional.interfaces.Fun;

/**
 *
 * <code>Take :: Integer -> [a] -> [a]</code>
 * @author badi
 */
public class Take implements Fun {

    Integer n;
    Iterable i;

    public Fun copy() {
        return new Take().curry(n).curry(i);
    }

    public Fun curry(Object arg) {
        if (n == null)
            n = (Integer) arg;
        else if ( i == null)
            i = (Iterable) arg;

        return this;
    }

    public List call()  {
        List l = new LinkedList();
        if (n > 0) {
            Object head = Seq.head(i);
            Iterable tail = Seq.tail(i);
            l.add(head);
            l.addAll((Collection) new Take().curry(n - 1).curry(tail).call());
            return l;
        } else return l;
    }

}
