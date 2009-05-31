
package tajmi.abstracts.som;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import scala.Tuple2;

/**
 * Is used when initializing the tajmi.som.Field <br>
 * Is allowed to save state between calls
 * @author badi
 */
public abstract class InitFunc implements Callable< Tuple2< Object,Random >> {

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

    public abstract Tuple2< Object,Random > call ();

}
