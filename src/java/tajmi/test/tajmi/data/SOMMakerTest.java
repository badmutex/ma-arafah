
package tajmi.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tajmi.data.clusterable.instances.num.Vector;
import tajmi.data.som.SOM;

/**
 *
 * @author badi
 */
public class SOMMakerTest {

    public SOMMakerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of makeVectorialSOM method, of class SOMMaker.
     */
    @Test
    public void testMakeVectorialSOM() {
        try {
            System.out.println("makeVectorialSOM");
            List<Vector> data = readVectorData("test-data" + File.separator + "yeast.data");
            System.out.println(data);
            SOMMaker instance = new SOMMaker();
            SOM<Vector> expResult = null;
            SOM<Vector> result = instance.makeVectorialSOM(data);
            System.out.println("Running SOM");
            result.call();
//            assertEquals(expResult, result);
//            fail("The test case is a prototype.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SOMMakerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SOMMakerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Vector> readVectorData (String path) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<Vector> vs = new LinkedList<Vector>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()){
            Pattern p = Pattern.compile("\\s+"); // split on whitespace
            String[] data = p.split(line);
            Vector v = new Vector(data.length);
            for (String d : data)
                v.add( Double.parseDouble(d) );
            vs.add(v);
        }
        return vs;

    }

}