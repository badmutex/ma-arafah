
package tajmi.data.som;

import java.util.Iterator;
import tajmi.data.clusterable.interfaces.som.InitFunc;
import java.util.ArrayList;
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
    int length, width;


    public Field (int length, int width, List<T> input_data, Random rand, InitFunc initf) {

        this.length = length;
        this.width = width;
        
        field = new ArrayList<List<T>>(length);
        for (int x = 0; x < length; x++){
            field.add(new ArrayList<T>(width));
            for (int y = 0; y < width; y++) {
                Tuple2<T, Random> res = initf.params(input_data, rand).call();
                field.get(x).add(res._1());
                rand = res._2();
            }
        }
    }

    public Field (Field<T> old_field) {
        this.length = old_field.length;
        this.width = old_field.width;

        this.field = new ArrayList<List<T>>(length);

        for (int i = 0; i < length; i++) {
            this.field.add(new ArrayList<T>(width));
            for (int j = 0; j < width; j++)
                this.field.get(i).add(old_field.get(new Position(i, j)));
        }
    }


//    public Field (Tuple2<Integer, Integer> dimensions, List<Tuple2<Position, T>> data) {
//        int length = dimensions._1(),
//                width = dimensions._2();
//
//        field = new ArrayList<List<T>>(length);
//        for (int x = 0; x < length; x++) {
//            field.add(new ArrayList<T>(width));
//
//            for (int y = 0; y < width; y++)
//                field.get(x).add(null);
//        }
//
//
//
//        for (Tuple2<Position, T> datum : data)
//            field = set(field, datum._1(), datum._2());
//
//    }
//
//


    public T get (Position pos) {
        return field.get(pos.x()).get(pos.y());
    }

    public void set (Position pos, T datum) {
        field.get(pos.x()).set(pos.y(), datum);
    }

    public Field<T> set (List<Tuple2<Position,T>> info) {
        for (Tuple2<Position, T> item : info) {
            Position pos = item._1();
            T datum = item._2();
            set( pos, datum );
        }
        return this;
    }

    public int size () {
        return field.size() * field.get(0).size();
    }

    public Tuple2<Integer, Integer> dimensions () {
        return new Tuple2<Integer, Integer>(length, width);
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
