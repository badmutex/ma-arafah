
package tajmi.instances.cdk.som;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.functional.instances.cdk.MoleculeReader;
import tajmi.functional.instances.seq.Zip;

/**
 *
 * @author badi
 */
public class FindGeneralizedMedianTest {

    String root = "test-data/hiv1-inhibitors";
    int nmolecs = 9;

    @Test
    public void findGeneralizedMedianTest() throws Exception {
        List files = new LinkedList();
        File r = new File(root);
        File[] fs = r.listFiles(new FileFilter() {

            int count = 0;
            public boolean accept(File pathname) {
                count++;
                if (count < nmolecs)
                    return true;
                else return false;
            }
        });
        List<IAtomContainer> molecs = new LinkedList<IAtomContainer>();
        for (File f : fs) {
            String name = f.getAbsolutePath();
            IAtomContainer m = (IAtomContainer) new MoleculeReader().curry(name).call();
            molecs.add(m);
        }

        List<Double> distances = new LinkedList<Double>();
        Random rand = new Random(42);
        for (IAtomContainer m : molecs)
            distances.add(rand.nextDouble());

        List<Tuple2<Double, IAtomContainer>> pairs =
                (List<Tuple2<Double, IAtomContainer>>)
                new Zip<Double, IAtomContainer>().curry(distances).curry(molecs).call();
        Tuple2 min = new FindGeneralizedMedian().params(pairs).call();

        System.out.println("distances: " + distances);
        System.out.println("min: " + min);
        System.out.println("findGeneralizedMedianTest");

    }


}