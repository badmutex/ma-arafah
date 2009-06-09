
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.functional.interfaces.Fun;
import tajmi.functional.interfaces.Result;
import tajmi.parallel.Grain;

/**
 * Maps the function <code>(a -> b)</code> over the iterable <code>[a]</code>
 * using threads running the specified number of sequential calculations.<br><br>
 * 
 * <code>GrainyParallelMap :: (a -> b) -> [a] -> Integer -> [b]<br>
 * @author badi
 */
public class GrainyParallelMap extends Map {

    int grain_size = Integer.MIN_VALUE;

    @Override
    public Fun curry (Object arg) {
        if (this.f == null || this.l == null)
            super.curry(arg);
        else if (grain_size == Integer.MIN_VALUE)
            grain_size = (Integer) arg;

        return this;
    }

    @Override
    public List call () throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future> futures = new LinkedList<Future>();

        // submit computations
        List grain = new LinkedList();
        for (Object o : l) {
            Fun f2 = f.copy();
            Object r = f2.curry(o);
            grain.add(r);

            if (grain.size() >= grain_size) {
                Future<List> sequential = submit(grain, pool);
                futures.add(sequential);
                grain = new LinkedList();
            }
        }
        if (grain.size() > 0) {
            Future<List> sequential = submit(grain, pool);
            futures.add(sequential);
        }


        // collect results
        List results = new LinkedList();
        for (Future<List> res : futures) {
            List l = res.get();
            for (Object r : l)
                results.add(r);
        }

        // aaaaand we're done!
        return results;
    }

    private Future<List> submit (List grain, ExecutorService pool) {
        Grain<List> granular_computation = new Grain<List>() {

                    List<Result> computations;

                    public List call() {
                        List results = new LinkedList();
                        for (Result c : computations)
                            try {
                            results.add(c.call());
                        } catch (Exception ex) {
                            Logger.getLogger(GrainyParallelMap.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        return results;
                    }

                    public void ingrain(Object grain) {
                        computations = (List) grain;
                    }
                };
        granular_computation.ingrain(grain);

        return pool.submit(granular_computation);
    }
}
