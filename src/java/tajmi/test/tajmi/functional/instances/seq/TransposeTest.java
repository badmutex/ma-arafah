
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author badi
 */
public class TransposeTest {

    @Test
    public void TransposeTest() {
        List<List> l = new LinkedList();
        for (int i = 0; i < 5; i++) {
            l.add(new LinkedList());
            for (int j = 0; j < 5; j++)
                l.get(i).add(i * 5 + j);
        }

        List<List> l2 = Seq.transpose(l);

        System.out.println("TransposeTest");
        System.out.println(l);
        System.out.println(l2);
    }
}