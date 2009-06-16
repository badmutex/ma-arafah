package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        for (int i = 0; i < 10; i++) {
            l1.add(i);
        }

        l2 = new LinkedList<Integer>();
        for (int i = 10; i < 20; i++) {
            l2.add(i);
        }

        add10 = (Add) new Add().curry(10);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testFilter() {
        System.out.println("********** testFilter ***********");

        List exp = new LinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            exp.add(i);
        }

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
            if (num == null) {
                num = (Integer) arg;
            }

            return this;
        }

        public Object call() {
            return num < 5;
        }
    }

    @Test
    public void testFold() {
        System.out.println("********** testFold ***********");

        double sum = 0;
        for (Integer i : (List<Integer>) l1) {
            sum += i;
        }

        double res = (Double) Seq.fold(new Add(), 0, l1);

        System.out.println("summing : " + l1);
        System.out.println("expected: " + sum);
        System.out.println("result  : " + res);

        assertEquals(sum, res, 0);
    }

    @Test
    public void testMap() {
        System.out.println("********** testMap ***********");

        List res = Seq.map(new Sleeper(), l1);

        List exp = new LinkedList();
        for (Integer i : (List<Integer>) l1) {
            exp.add(i + 10.0);
        }

        System.out.println("original: " + l1);
        System.out.println("expected: " + exp);
        System.out.println("result  : " + res);

        assertEquals(l1, res);

    }

    @Test
    public void testGrainy_parallel_map() {
        System.out.println("********** testGrainy_parallel_map ***********");

        List<Double> l = new LinkedList<Double>();
        double max = 10.0;
        for (double i = 0.0; i < max; i++) {
            l.add(i);
        }

        List test = (List) new GrainyParallelMap().curry(new Sleeper()).curry(l).curry((int)max / Runtime.getRuntime().availableProcessors()).call();

        List expected = new LinkedList();
        for (Double d : l) {
            expected.add(d + 100);
        }

        System.out.println("T: " + test);
        System.out.println("E: " + expected);
        assertEquals(l, test);
    }

    @Test
    public void testParallel_map() {
        System.out.println("********** testParallel_map ***********");

        List<Double> l = new LinkedList<Double>();
        for (double i = 0.0; i < 10.0; i++) {
            l.add(i);
        }

        List test = (List) new ParallelMap().curry(new Sleeper()).curry(l).call();

        List expected = new LinkedList();
        for (Double d : l) {
            expected.add(d + 100);
        }

        assertEquals(l, test);
    }

    @Test
    public void testHead() {
        System.out.println("********** testHead ***********");

        int zero = (Integer) Seq.head(l1);
        assertEquals(0, zero);
    }

    @Test
    public void testTail() {
    }

    @Test
    public void testMinimum_by() {
    }

    @Test
    public void testZip() {
    }

    @Test
    public void testZip_with() {
    }

    class Sleeper implements Fun {

        Object foo;

        public Fun copy() {
            return new Sleeper().curry(foo);
        }

        public Fun curry(Object arg) {
            if (foo == null) {
                foo = arg;
            }

            return this;
        }

        public Object call() throws RuntimeException {
            try {
                System.out.print("sleepring for 1 second");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SeqTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("...awake!");
            return foo;
        }
    }
}