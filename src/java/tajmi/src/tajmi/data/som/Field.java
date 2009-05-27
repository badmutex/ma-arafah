
package tajmi.data.som;

import java.util.Iterator;
import tajmi.data.clusterable.interfaces.som.InitFunc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import scala.Tuple2;

/**
 * Implements a static 2D field for the SOM projection
 * @author badi
 */
public class Field<T> implements Iterable<Tuple2<Position, T>> {

    List<List<T>> field;


    public Field (int length, int width, List<T> input_data, Random rand, InitFunc initf) {

        field = new ArrayList<List<T>>(length);
        for (int x = 0; x < length; x++){
            field.set(x, new ArrayList<T>(width));
            for (int y = 0; y < width; y++) {
                Tuple2<T, Random> res = initf.params(input_data, rand).call();
                field = set(field, new Position(x, y), res._1());
                rand = res._2();
            }
        }
    }

    /** @deprecated */
    public Field (int length, int width, List<Tuple2<Position, T>> data) {
        field = new ArrayList<List<T>>(length);
        for (int x = 0; x < length; x++) {
            field.set(x, new ArrayList<T>(width));
        }

        for (Tuple2<Position, T> datum : data)
            field = set (field, datum._1(), datum._2());
    }

    public Field (Field old_field, List<Tuple2<Position, T>> data) {

        List<List<T>> new_field = new ArrayList<List<T>>();

        for (Tuple2<Position, T> datum : data) {
            Position pos = datum._1();
            T info = datum._2();

            if( new_field.get(pos.x()) == null )
                new_field.add(new ArrayList<T>(100));
            else
                new_field = set(new_field, pos, info);
        }

        field = new_field;

    }


    private List<List<T>> set (List<List<T>> field, Position pos, T datum) {
        field.get(pos.x()).set(pos.y(), datum);
        return field;
    }

    public T get (Position pos) {
        return field.get(pos.x()).get(pos.y());
    }

    public int size () {
        return field.size() * field.get(0).size();
    }

    /**
     * @return an interator over a linked list of the elements in the field.
     */
    public Iterator<Tuple2<Position, T>> iterator() {
        // this is ugly: Java needs lazy generators
        List<Tuple2<Position, T>> l = new LinkedList<Tuple2<Position, T>>();
        for (int x = 0; x < field.size(); x++)
            for (int y = 0; y < field.get(x).size(); y++)
                l.add(
                        new Tuple2<Position, T>(new Position(x, y)
                        , field.get(x).get(y)));
        return l.iterator();
    }

}
