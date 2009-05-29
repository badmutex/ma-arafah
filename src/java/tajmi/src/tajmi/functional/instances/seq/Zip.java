
package tajmi.functional.instances.seq;

import tajmi.functional.interfaces.Fun;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import scala.Tuple2;

/**
 *
 * @author badi
 */
public class Zip implements Fun {

    Iterable i1, i2;

    public Fun copy() {
        return new Zip().curry(i1).curry(i2);
    }

    public Fun curry(Object arg) {
        if (i1 == null)
            i1 = (Iterable) arg;
        else if (i2 == null)
            i2 = (Iterable) arg;

        return this;
    }

    public Object call() throws Exception {
        List res = new LinkedList();

        Iterator itr1 = i1.iterator(),
                itr2 = i2.iterator();
        while (itr1.hasNext() && itr2.hasNext())
            res.add(new Tuple2(itr1.next(), itr2.next()));

        return res;
    }

}
