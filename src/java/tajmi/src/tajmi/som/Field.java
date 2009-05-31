
package tajmi.som;

import java.util.Iterator;
import tajmi.abstracts.som.InitFunc;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import scala.Tuple2;

/**
 * Implements a static 2D field for the SOM projection
 * @author badi
 */
public class Field implements Iterable<Tuple2<Position, Object>> {

    List<List> field;
    int length, width;


    public Field (int length, int width, List input_data, Random rand, InitFunc initf) {

        this.length = length;
        this.width = width;
        
        field = new ArrayList<List>(length);
        for (int x = 0; x < length; x++){
            field.add(new ArrayList(width));
            for (int y = 0; y < width; y++) {
                Tuple2<Object, Random> res = initf.params(input_data, rand).call();
                field.get(x).add(res._1());
                rand = res._2();
            }
        }
    }

    public Field (Field old_field) {
        this.length = old_field.length;
        this.width = old_field.width;

        this.field = new ArrayList<List>(length);

        for (int i = 0; i < length; i++) {
            this.field.add(new ArrayList(width));
            for (int j = 0; j < width; j++)
                this.field.get(i).add(old_field.get(new Position(i, j)));
        }
    }

    public Object get (Position pos) {
        return field.get(pos.x()).get(pos.y());
    }

    public void set (Position pos, Object datum) {
        field.get(pos.x()).set(pos.y(), datum);
    }

    public Field set (List<Tuple2<Position,Object>> info) {
        for (Tuple2<Position, Object> item : info) {
            Position pos = item._1();
            Object datum = item._2();
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
    public Iterator<Tuple2<Position, Object>> iterator() {
        // this is ugly: Java needs lazy generators
        List<Tuple2<Position, Object>> l = new LinkedList<Tuple2<Position, Object>>();
        for (int x = 0; x < field.size(); x++)
            for (int y = 0; y < field.get(x).size(); y++)
                l.add(
                        new Tuple2<Position, Object>(new Position(x, y)
                        , field.get(x).get(y)));
        return l.iterator();
    }

}
