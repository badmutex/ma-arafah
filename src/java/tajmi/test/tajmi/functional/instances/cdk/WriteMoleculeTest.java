
package tajmi.functional.instances.cdk;

import org.junit.Test;
import java.io.File;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.formats.IChemFormat;
import org.openscience.cdk.io.formats.SMILESFormat;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class WriteMoleculeTest {

    @Test
    public void moleculeWriterTest() throws Exception {
        String root = "test-data";
        String in_dir = root + File.separator + "hiv1-inhibitors";
        String test_molec = "AMP.mol2";
        IChemFormat format = (IChemFormat) SMILESFormat.getInstance();

        String input = in_dir + File.separator + test_molec;
        IMolecule molec = (IMolecule) new ReadMolecule().curry(input).call();

        String output = root + File.separator + "moleculeWriterTest";
        Fun mwriter = new WriteMolecule().curry(format).curry(output).curry(molec);
        mwriter.call();

        System.out.println("wrote " + output + format.getPreferredNameExtension());
        System.out.println("moleculeWriterTest");
    }
}