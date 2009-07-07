
package tajmi.functional.instances.seq;

import tajmi.functional.interfaces.Fun;

/**
 *<code> Elem :: a -> [a] -> Boolean</code>
 * @author badi
 */
public class Elem implements Fun {

    Object a;
    Iterable seq;

    public Fun copy() {
        return new Elem().curry(a).curry(seq);
    }

    public Fun curry(Object arg) {
        if (a == null)
            a = arg;
        else if (seq == null)
            seq = (Iterable) arg;
        
        return this;
    }

    public Boolean call() {
        for (Object o : seq)
            if (a.equals(o))
                return true;
        return false;
    }

}
