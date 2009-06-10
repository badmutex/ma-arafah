package tajmi.instances.cdk.som;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import scala.Tuple2;
import tajmi.abstracts.som.InitFunc;
import tajmi.functional.instances.cdk.MoleculeReader;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class OpenGLFieldViewTest {

    @Test
    public void openGLFieldViewTest() throws Exception {
        String root = "test-data" + File.separator + "hiv1-inhibitors";
        File[] files = new File(root).listFiles(new FileFilter() {

            int count = 0;

            public boolean accept(File pathname) {
                return count++ < 50;
            }
        });
        List<IAtomContainer> molecules = new LinkedList();
        for (File f : files) {
            IMolecule m = (IMolecule) new MoleculeReader().curry(f.getAbsolutePath()).call();
            m.setID(f.getName());
            molecules.add(m);
        }

        InitFunc initf = new AtomContainerInitFunc().params(molecules, new Random(42));

        Field<FieldModel<IAtomContainer>> f = new Field(20, 20, initf);
        for (Tuple2<Position, FieldModel<IAtomContainer>> m : f) {
            m._2().setGeneralizeMedian(m._2().peek());
        }
        new OpenGLFieldView().params(f).call();
        Thread.sleep(5000);
    }
}