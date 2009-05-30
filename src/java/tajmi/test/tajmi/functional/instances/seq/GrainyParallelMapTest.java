
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import tajmi.functional.instances.math.Add;
import static org.junit.Assert.*;

/**
 *
 * @author badi
 */
public class GrainyParallelMapTest {

    @Test
    public void GrainyParallelMap() throws Exception {
        List<Double> l = new LinkedList<Double>();
        for (double i = 0.0; i < 1000.0; i++)
            l.add(i);

        List test = (List) new GrainyParallelMap()
                .curry(new Add().curry(100))
                .curry(l)
                .curry(1000/Runtime.getRuntime().availableProcessors())
                .call();

        List expected = new LinkedList();
        for (Double d : l)
            expected.add(d + 100);

        System.out.println("T: " + test);
        System.out.println("E: " + expected);
        assertEquals(expected, test);


        System.out.println("GrainyParallelMap");
    }

    
}