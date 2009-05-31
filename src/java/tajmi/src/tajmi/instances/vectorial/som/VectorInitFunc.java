
package tajmi.instances.vectorial.som;

import java.util.List;
import java.util.Random;
import scala.Tuple2;
import tajmi.instances.vectorial.Vector;
import tajmi.abstracts.som.InitFunc;

/**
 * Randomly generates a vector, ignoring the parameterized vector seed
 * @author badi
 */
public class VectorInitFunc extends InitFunc {

    public Tuple2<Object, Random> call() {

        Random randgen = getRandgen();
        List<Vector> seed = getSeed();
        int size = seed.get(0).size();



        Vector v = new Vector(size);

        for (int i = 0; i < size; i++) {
            double d = randgen.nextDouble();
            v.sum(d);
        }

        return new Tuple2<Object, Random>(v, randgen);
    }

}
