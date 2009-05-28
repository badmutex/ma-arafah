
package tajmi.instances.vectorial.som;

import java.util.List;
import java.util.Random;
import scala.Tuple2;
import tajmi.instances.vectorial.Vector;
import tajmi.interfaces.som.InitFunc;

/**
 * Randomly generates a vector, ignoring the parameterized vector seed
 * @author badi
 */
public class VectorInitFunc implements InitFunc<Vector> {

    List<Vector> seed;
    Random randgen;

    int size;

    public InitFunc<Vector> params(List<Vector> seed, Random randgen) {

        this.seed = seed;
        this.randgen = randgen;

        this.size = this.seed.get(0).size();

        return this;
    }

    public Tuple2<Vector, Random> call() {

        Vector v = new Vector(size);

        for (int i = 0; i < size; i++) {
            double d = randgen.nextDouble();
            v.sum(d);
        }

        return new Tuple2<Vector, Random>(v, randgen);
    }

}
