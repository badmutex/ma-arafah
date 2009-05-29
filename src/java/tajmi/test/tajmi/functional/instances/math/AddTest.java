
package tajmi.functional.instances.math;

import org.junit.Test;
import tajmi.functional.interfaces.Fun;
import tajmi.functional.interfaces.Result;
import static org.junit.Assert.*;

/**
 *
 * @author badi
 */
public class AddTest {
    
    @Test
    public void testAdd() throws Exception {
        Add add = new Add();
        Fun c1 = add.curry(42);
        Result r = c1.curry(24);

        Double test = (Double) r.call();

        assertEquals(new Double(42 + 24), test);


        System.out.println("testAdd");
    }

}