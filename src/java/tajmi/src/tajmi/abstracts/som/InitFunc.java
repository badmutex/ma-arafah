
package tajmi.abstracts.som;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Is used when initializing the tajmi.som.Field <br>
 * Is allowed to save state between calls
 * @author badi
 */
public abstract class InitFunc<F,D> implements Callable<F> {

    List<D> seed;
    Random randgen;

    public Random getRandgen() {
        return randgen;
    }

    public List<D> getSeed() {
        return seed;
    }

    public InitFunc params(List<D> seed, Random randgen) {

        this.seed = new LinkedList<D>(seed);
        this.randgen = randgen;

        return this;
    }

    public abstract F call ();

}
