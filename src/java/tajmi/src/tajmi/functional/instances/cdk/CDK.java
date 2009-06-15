
package tajmi.functional.instances.cdk;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.formats.IChemFormat;

/**
 *
 * @author badi
 */
public class CDK {

    public static IMolecule read_molecule (String filename) throws Exception {
        return (IMolecule) new ReadMolecule().curry(filename).call();
    }

    public static void write_molecule (IChemFormat format, String filename, IAtomContainer molecule) throws Exception {
        new WriteMolecule().curry(format).curry(filename).curry(molecule).call();
    }

}
