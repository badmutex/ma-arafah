
package tajmi.functional.instances.seq;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author badi
 */
public class DropTest {

    @Test
    public void dropTest() throws Exception {
        List l = new LinkedList();
        for (int i = 0; i < 20; i++)
            l.add(i);
        System.out.println(Seq.drop(10, l));
    }

}