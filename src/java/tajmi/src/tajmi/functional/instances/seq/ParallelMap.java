
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.functional.interfaces.Fun;


/**
 * Maps the function <code>(a -> b)</code> over the iterable sequence
 * <code>[a]</code> in which each function application is executed in parallel.
 * Results are collected in the iterable sequence <code>[b]</code><br><br>
 *
 * <code>ParallelMap :: (a -> b) -> [a] -> [b]</code>
 * @author badi
 */
public class ParallelMap extends Map {

    @Override
    public List call () {
        List res = new LinkedList();
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future> futures = new LinkedList<Future>();

        for (Object o : l) {
            Fun f2 = f.copy();
            Callable c = f2.curry(o);
            futures.add(pool.submit(c));
        }

        for (Future f : futures)
            try {
            res.add(f.get());
        } catch (InterruptedException ex) {
            Logger.getLogger(ParallelMap.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ParallelMap.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

        return res;
    }
}
