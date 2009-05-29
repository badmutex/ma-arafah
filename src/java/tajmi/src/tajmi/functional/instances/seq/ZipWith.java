
package tajmi.functional.instances.seq;

import tajmi.functional.interfaces.Fun;
import java.util.LinkedList;
import java.util.List;
import scala.Tuple2;

/**
 *
 * @author badi
 */
public class ZipWith implements Fun {

    Fun f;
    Iterable i1, i2;

    public Fun copy() {
        return new ZipWith().curry(f).curry(i1).curry(i2);
    }

    public Fun curry(Object arg) {
        if (f == null)
            f = (Fun) arg;
        else if (i1 == null)
            i1 = (Iterable) arg;
        else if (i2 == null)
            i2 = (Iterable) arg;

        return this;
    }

    public Object call() throws Exception {
        Iterable<Tuple2> tups = (Iterable<Tuple2>) new Zip().curry(i1).curry(i2).call();

        List res = new LinkedList();
        for (Tuple2 t : tups) {
            Fun f2 = f.copy();
            Object r = f2.curry(t._1()).curry(t._2()).call();
            res.add(r);
        }
        return res;
    }

}
