
package tajmi.functional.instances.seq;

import tajmi.functional.interfaces.Fun;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import scala.Tuple2;

/**
 *
 * <code>Zip :: [a] -> [b] -> [(a,b)]</code>
 * @author badi
 */
public class Zip<A,B> implements Fun {

    Iterable<A> as;
    Iterable<B> bs;

    public Fun copy() {
        return new Zip().curry(as).curry(bs);
    }

    public Fun curry(Object arg) {
        if (as == null)
            as = (Iterable) arg;
        else if (bs == null)
            bs = (Iterable) arg;

        return this;
    }

    public List<Tuple2<A,B>> call() throws Exception {
        return (List<Tuple2<A, B>>) new ZipWith().curry(new Assoc()).curry(as).curry(bs).call();
    }

    private class Assoc<A,B> implements Fun {

        A a;
        B b;

        public Fun copy() {
            return new Assoc().curry(a).curry(b);
        }

        public Fun curry(Object arg) {
            if (a == null)
                a = (A) arg;
            else if (b == null)
                b = (B) arg;

            return this;
        }

        public Tuple2<A,B> call() throws Exception {
            return new Tuple2<A, B>(a, b);
        }
        
    }

}
