
package tajmi.instances.cdk.som;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.*;
//import org.junit.Test;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.som.InitFunc;
import tajmi.functional.instances.cdk.ReadMolecule;
import tajmi.som.Field;

/**
 *
 * @author badi
 */
public class AtomContainerInitFuncTest {

    @Test
    public void AtomContainerInitFuncTest() throws FileNotFoundException, IOException, CDKException, Exception {

        final int POINTS = 9;
        final String DATA_DIR = "test-data";
        File dir = new File(DATA_DIR + File.separator + "hiv1-inhibitors");
        final int LENGTH = 7, WIDTH = 7;


        File[] files = dir.listFiles(new FileFilter() {

            int chosen = 0;
            public boolean accept(File pathname) {
                if(chosen < POINTS){
                    chosen++;
                    return pathname.getName().matches("\\w*\\.mol2");
                } else return false;
            }
        });

        List<IAtomContainer> molecs = new LinkedList<IAtomContainer>();
        for (File f : files) {
            IAtomContainer c = (IAtomContainer) new ReadMolecule().curry(f.getAbsolutePath()).call();
            c.setID(f.getName().split(".mol2")[0]);
            molecs.add(c);
        }

        System.out.print("read in:\t");
        for (IAtomContainer c : molecs)
            System.out.print(c.getID() + " ");
        System.out.println();


        Random r = new Random(42);
        InitFunc initf = new AtomContainerInitFunc().params(molecs, r);

        Field<FieldModel<IAtomContainer>> field = new Field<FieldModel<IAtomContainer>>(LENGTH, WIDTH, initf);

        System.out.println("Field:\n" + field);
        System.out.println();
        System.out.println("AtomContainerInitFuncTest");
    }


}