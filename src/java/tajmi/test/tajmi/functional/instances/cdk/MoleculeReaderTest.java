
package tajmi.functional.instances.cdk;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import java.io.File;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;

/**
 *
 * @author badi
 */
public class MoleculeReaderTest {

    @Test
    public void MoleculeReaderTest() throws FileNotFoundException, CDKException, IOException {
        String in_dir = "test-data" + File.separator + "hiv1-inhibitors";
        String test_molec = "AMP.mol2";

        MoleculeReader mreader = (MoleculeReader) new MoleculeReader().curry(in_dir + File.separator + test_molec);
        IMolecule molec = mreader.call();
        System.out.println("read in " + test_molec);

        System.out.println("MoleculeReaderTest");
    }
}