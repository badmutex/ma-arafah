
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tajmi.functional.instances.math.Add;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class SeqTest {

    List l1, l2;
    Add add10;

    public SeqTest() {
        l1 = new LinkedList<Integer>();
        for (int i = 0; i < 10; i++)
            l1.add(i);

        l2 = new LinkedList<Integer>();
        for (int i = 10; i < 20; i++)
            l2.add(i);

        add10 = (Add) new Add().curry(10);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testFilter() throws Exception {
        System.out.println("********** testFilter ***********");

        List exp = new LinkedList<Integer>();
        for (int i = 0; i < 5; i++)
            exp.add(i);

        List res = Seq.filter(new LessThan5(), l1);

        System.out.println("filtered for < 5: " + l1);
        System.out.println("result          : " + res);
        System.out.println("expected        : " + exp);

        assertEquals(exp, res);
    }

    private class LessThan5 implements Fun {

        Integer num;

        public Fun copy() {
            return new LessThan5().curry(num);
        }

        public Fun curry(Object arg) {
            if (num == null)
                num = (Integer) arg;

            return this;
        }

        public Object call() throws Exception {
            return num < 5;
        }

    }

    @Test
    public void testFold() throws Exception {
        System.out.println("********** testFold ***********");

        double sum = 0;
        for (Integer i : (List<Integer>) l1)
            sum += i;

        double res = (Double) Seq.fold(new Add(), 0, l1);

        System.out.println("summing : " + l1);
        System.out.println("expected: " + sum);
        System.out.println("result  : " + res);

        assertEquals(sum, res, 0);
    }

    @Test
    public void testMap() throws Exception {
        System.out.println("********** testMap ***********");

        List res = Seq.map(add10, l1);

        List exp = new LinkedList();
        for (Integer i : (List<Integer>) l1)
            exp.add(i + 10.0);

        System.out.println("original: " + l1);
        System.out.println("expected: " + exp);
        System.out.println("result  : " + res);

        assertEquals(exp, res);

    }

    @Test
    public void testGrainy_parallel_map() throws Exception {
    }

    @Test
    public void testParallel_map() throws Exception {
        System.out.println("********** testParallel_map ***********");


    }

    @Test
    public void testHead() throws Exception {
    }

    @Test
    public void testTail() throws Exception {
    }

    @Test
    public void testMinimum_by() throws Exception {
    }

    @Test
    public void testZip() throws Exception {
    }

    @Test
    public void testZip_with() throws Exception {
    }


}