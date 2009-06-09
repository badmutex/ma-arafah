
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import tajmi.functional.Ord;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class MinimumByTest {

    @Test
    public void MinimumByTest() throws Exception {
        List<Integer> l = new LinkedList<Integer>();
        Random rand = new Random(42);
        for (int i = 0; i < 10; i++)
            l.add(rand.nextInt(100));

        Integer min = (Integer) new MinimumBy().curry(new Compare()).curry(l).call();
        System.out.println("list: " + l);
        System.out.println("Min: " + min);

        System.out.println("MinimumByTest");
    }

    private class Compare implements Fun {

        Integer a, b;

        public Fun copy() {
            return new Compare().curry(a).curry(b);
        }

        public Fun curry(Object arg) {
            if (a == null)
                a = (Integer) arg;
            else if (b == null)
                b = (Integer) arg;

            return this;
        }

        public Ord call() throws Exception {
            if (a < b) return Ord.LT;
            else if (a == b) return Ord.EQ;
            else return Ord.GT;
        }

    }

    
}