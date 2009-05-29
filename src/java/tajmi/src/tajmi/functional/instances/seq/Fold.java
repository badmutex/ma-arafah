
package tajmi.functional.instances.seq;

import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class Fold<T> implements Fun {

    Fun f;
    T init;
    Iterable l;

    public Fun copy() {
        return new Fold().curry(f).curry(l);
    }

    public Fun curry(Object arg) {
        if (f == null)
            f = (Fun) arg;
        else if (init == null)
            init = (T) arg;
        else if (l == null)
            l = (Iterable) arg;

        return this;
    }

    public Object call() throws Exception {
        T res = init;
        for (Object o : l) {
            Fun f2 = f.copy();
            res = (T) f2.curry(res).curry(o).call();
        }
        return res;
    }

}
