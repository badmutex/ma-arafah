
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import tajmi.functional.interfaces.Fun;


/**
 *
 * @author badi
 */
public class ParallelMap extends Map {

    @Override
    public Object call () throws ExecutionException, InterruptedException {
        List res = new LinkedList();
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future> futures = new LinkedList<Future>();

        for (Object o : l) {
            Fun f2 = f.copy();
            Callable c = f2.curry(o);
            futures.add(pool.submit(c));
        }

        for (Future f : futures)
            res.add(f.get());

        return res;
    }
}
