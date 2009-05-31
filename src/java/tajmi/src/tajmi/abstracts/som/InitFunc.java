
package tajmi.abstracts.som;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Is used when initializing the tajmi.som.Field <br>
 * Is allowed to save state between calls
 * @author badi
 */
public abstract class InitFunc implements Callable<Object> {

    List seed;
    Random randgen;

    public Random getRandgen() {
        return randgen;
    }

    public List getSeed() {
        return seed;
    }

    public InitFunc params(List seed, Random randgen) {

        this.seed = seed;
        this.randgen = randgen;

        return this;
    }

    public abstract Object call ();

}
