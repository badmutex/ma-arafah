
package tajmi.data.som;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import scala.Tuple2;

/**
 * Implements a static 2D field for the SOM projection
 * @author badi
 */
public class Field<T> {

    List<List<T>> field;


    public Field (int length, int width) {
        field = new ArrayList<List<T>>(length);
        for (List<T> d : field)
            d = new ArrayList<T>(width);
    }


    public Field<T> init (List<T> input_data, InitFunc initf) {
        Random rand = new Random(Config.getInstance().init_random_gen_seed());

        for (List<T> d2 : field) {
            for (T datum : d2) {
                Tuple2< T,Random > res = initf.params(input_data, rand).call();
                rand = res._2();
                datum = res._1();
            }
        }

        return this;
    }


    public Tuple2<Position, T> find_best_match (T datum, DistanceFunc<T> distance) {

        T best = field.get(0).get(0);
        Position best_position = new Position(0, 0);
        double best_distance = distance.params(datum, best).call();
        for (int x = 0; x < field.size(); x++) {
            for (int y = 0; y < field.get(x).size(); y++) {
                if (x == 0 && y == 0) continue;
                T current = field.get(x).get(y);
                double current_distance = distance.params(datum, current).call();
                if (current_distance < best_distance) {
                    best = current;
                    best_position = new Position(x, y);
                    best_distance = current_distance;
                }
            }
        }
        return new Tuple2<Position, T>(best_position, best);
    }

}
