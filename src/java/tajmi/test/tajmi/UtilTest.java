
package tajmi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.Tuple2;
import static org.junit.Assert.*;

/**
 *
 * @author badi
 */
public class UtilTest {

    public UtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of zip method, of class Util.
     */
    @Test
    public void testZip() {
        System.out.println("testZip");
        
        List l1 = new LinkedList();
        for (int i = 0; i < 20; i++) {
            l1.add(i);
        }

        String hello = "hello world";
        List l2 = new LinkedList();
        for (int i = 0; i < hello.length(); i++) {
            l2.add(hello.charAt(i));
        }

        List<Tuple2> l_zipped = Util.zip(l1, l2);

        Iterator<Integer> itr1 = l1.iterator();
        Iterator<Character> itr2 = l2.iterator();
        List<Tuple2> l_expected = new LinkedList<Tuple2>();

        while (itr1.hasNext() && itr2.hasNext())
            l_expected.add(new Tuple2(itr1.next(), itr2.next()));

        assertEquals(l_expected, l_zipped);
    }

    /**
     * Test of identical method, of class Util.
     */
    @Test
    public void testIdentical() {
        System.out.println("testIdentical");
        
        List l1 = new LinkedList(),
                l2 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            l1.add(i);
            l2.add(i);
        }
        assertTrue(Util.identical(l1, l2));
        

        l2.clear();
        for (int i = 10; i < 20; i++) {
            l2.add(i);
        }
        assertFalse(Util.identical(l1, l2));
        
        l1.clear();
        l2.clear();

        for (int i = 0; i < 10; i++) {
            List l = new LinkedList();
            for (int j = 0; j < 10; j++) {
                l.add(j);
            }
            l1.add(l);
            l2.add(l);
        }
        assertTrue(Util.identical(l1, l2));

        l2.clear();
        for (int i = 0; i < 10; i++) {
            List l = new LinkedList();
            for (int j = 0; j < 10; j += 2) {
                l.add(j);
            }
            l2.add(l);
        }
        assertFalse(Util.identical(l1, l2));
    }

}