
package tajmi.som;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import scala.Tuple2;
import tajmi.abstracts.som.InitFunc;
import tajmi.abstracts.som.NeighborhoodFunc;
import tajmi.instances.som.Gaussian2DNeighborhoodFunc;

/**
 *
 * @author badi
 */
public class FieldTest {

    public Field<Integer> initField () {
        InitFunc<Integer, Integer> initf = new InitFunc<Integer, Integer>() {

            int num = 0;
            @Override
            public Integer call() {
                return num++;
            }
        };
        return new Field<Integer>(10, 10, initf);
    }

    @Test
    public void testDistance () {
        Field<Integer> f = initField();
        List<Tuple2<Position, Integer>> elems = new ArrayList<Tuple2<Position, Integer>>(f.size());
        for (Tuple2<Position, Integer> e : f)
            elems.add(e);

        NeighborhoodFunc d = new Gaussian2DNeighborhoodFunc();

        Position middle = elems.get(0)._1();
        System.out.println(middle);
        System.out.println("Position | distance to " + middle);
        System.out.println("----------------------------");
        for(Tuple2<Position, ?> p : elems)
            System.out.println(p._1() + "    | " + d.params(middle, p._1()).call());
        
        

    }

    @Test
    public void testField () {
        Field<Integer> field = initField();
        System.out.println(field);
    }

//    @Test
    public void testIterableness () {
        Field<Integer> field = initField();
        for (Tuple2<Position, Integer> f : field)
            System.out.print(f._2() + " ");
    }

}