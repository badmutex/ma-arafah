
package tajmi.functional.IO;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author badi
 */
public class LinesTest {

    @Test
    public void LinesTest() {
        String path = "test-data/amino-acids-names.txt";

        List<String> lines = IO.lines(path);
        System.out.println(lines);
    }
}