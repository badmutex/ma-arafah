
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author badi
 */
public class ZipTest {

    @Test
    public void zipTest() throws Exception {
        List<Integer> l1 = new LinkedList<Integer>();
        for (int i = 0; i < 10; i++)
            l1.add(i);

        List<Integer> l2 = new LinkedList<Integer>();
        for (int i = 100; i < 110; i++)
            l2.add(i);

        List res = (List) new Zip().curry(l1).curry(l2).call();
        System.out.println(res);
        System.out.println("zipTest");
    }
}