
package tajmi.instances.som;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import scala.Tuple2;
import tajmi.abstracts.som.InitFunc;
import tajmi.abstracts.som.NeighborhoodFunc;
import tajmi.functional.instances.seq.Filter;
import tajmi.functional.interfaces.Fun;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class Gaussian2DNeighborhoodFuncTest {

    @Test
    public void GaussianNeighborhoodFuncTest() {
        Gaussian2DNeighborhoodFunc f = new Gaussian2DNeighborhoodFunc();

        Position ori = new Position(0, 0),
                target = new Position(1, 1);
        f.params(ori, target);
        f.setSIGMA(1);
        System.out.println("(x0,y0) = " + ori + " <-> " + target + " => " + f.call());

        System.out.println("Gaussian2DNeighborhoodFuncTest");
    }

    public Field<Integer> initField () {
        InitFunc<Integer, Integer> initf = new InitFunc<Integer, Integer>() {

            @Override
            public Integer call() {
                return 42;
            }
        };
        return new Field<Integer>(10, 10, initf);
    }
    
    @Test
    public void testDistance () throws Exception {
        Field<Integer> f = initField();
        List<Tuple2<Position, Integer>> elems = new ArrayList<Tuple2<Position, Integer>>(f.size());
        for (Tuple2<Position, Integer> e : f)
            elems.add(e);

        NeighborhoodFunc d = new Gaussian2DNeighborhoodFunc();

        Position middle = elems.get(0)._1();
        System.out.println(middle);
        System.out.println("Position | distance to " + middle);
        System.out.println("----------------------------");

        List<Tuple2<Position, Double>> distances = new LinkedList();
        for(Tuple2<Position, ?> p : elems) {
            double dist = d.params(middle, p._1()).call();
            distances.add(new Tuple2(p._1(), dist));
            System.out.println(p._1() + "    | " + dist);
        }


        List greater_than_0_5 = (List) new Filter().curry(new GreaterThan0_5()).curry(distances).call();
        System.out.println("number > 0.5: " + greater_than_0_5.size());



        System.out.println("testDistance");

    }

    private class GreaterThan0_5 implements Fun {

        Tuple2<Position, Double> distance;
        
        public Fun copy() {
            return new GreaterThan0_5().curry(distance);
        }

        public Fun curry(Object arg) {
            if (distance == null)
                distance = (Tuple2<Position, Double>) arg;

            return this;
        }

        public Boolean call() throws Exception {
            double d = distance._2();

            if (d > 0.5)
                return true;
            else return false;
        }

    }

}