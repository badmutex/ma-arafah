
package tajmi;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.IChemObjectWriter;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.io.WriterFactory;
import org.openscience.cdk.io.formats.IChemFormat;
import org.openscience.cdk.io.formats.SMILESFormat;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import scala.Tuple2;

/**
 *
 * @author badi
 */
public class Util {

    public static List<Tuple2> zip(Iterable<?> a, Iterable<?> b) {

        List<Tuple2> result = new LinkedList<Tuple2>();

        Iterator itr_a = a.iterator(),
                itr_b = b.iterator();
        while (itr_a.hasNext() && itr_b.hasNext()) {
            result.add(new Tuple2(itr_a.next(), itr_b.next()));
        }

        return result;
    }

    public static boolean identical(Iterable<?> a, Iterable<?> b) {

        Iterator<Tuple2> itr = zip(a, b).iterator();
        while (itr.hasNext()) {
            Tuple2 cmp = itr.next();
            if (cmp._1() instanceof Iterable && cmp._2() instanceof Iterable) {
                return identical((Iterable) cmp._1(), (Iterable) cmp._2());
            } else {
                if (!cmp._1().equals(cmp._2())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds the maximum common subgraph between tow IAtomContainer graphs.
     * @param g_1 the first molecule
     * @param g_2 the second molecule
     * @return null if the computation fails, otherwise the mcss
     */
    public static IAtomContainer mcss(final IAtomContainer g_1, IAtomContainer g_2) {
        try {

            IAtomContainer g1 = AtomContainerManipulator.removeHydrogens(g_1);
            IAtomContainer g2 = AtomContainerManipulator.removeHydrogens(g_2);
            List<IAtomContainer> mcss_list = UniversalIsomorphismTester.getOverlaps(g1, g2);
            int max_mcss = Integer.MIN_VALUE;
            IAtomContainer mcss = null;
            for (IAtomContainer cs : mcss_list) {
                if (cs.getAtomCount() > max_mcss) {
                    max_mcss = cs.getAtomCount();
                    mcss = cs;
                }
            }

            return mcss;

        } catch (CDKException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }
}
