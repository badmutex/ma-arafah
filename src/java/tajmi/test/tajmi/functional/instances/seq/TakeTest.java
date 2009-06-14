
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author badi
 */
public class TakeTest {

    @Test
    public void takeTest() throws Exception {
        List l1 = new LinkedList();
        for (int i = 0; i < 1000; i++)
            l1.add(i);

        System.out.println(new Take().curry(10).curry(l1).call());
    }

    

}