package tajmi.som;

import org.junit.Test;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.frontends.SOMMaker;
import tajmi.instances.cdk.som.FieldModel;
import tajmi.instances.vectorial.Vector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.functional.instances.cdk.MoleculeReader;

/**
 *
 * @author badi
 */
public class SOMTest {

    final int POINTS = 190;
    final String DATA_DIR = "test-data";
    File in_dir = new File(DATA_DIR + File.separator + "hiv1-inhibitors");

    @Test
    public void TestIAtomContainerSOM() throws Exception {
        SOMMaker sommaker = new SOMMaker();
        sommaker.field_size(10, 10);
        sommaker.setMaxSOMIterations(2);
        sommaker.setShow_status_func(new ShowStatusFunc() {

            @Override
            public Void call() {
                SOMParams state = getState();
//                System.out.println(state.iterations + "\t" + state.projections);
                return null;
            }
        });

        List<IMolecule> data = readMolecules();
        SOM som = sommaker.makeIAtomContainerSOM(data);
        som.call();
        new LinkedList().add(null);
        
    }

    public List<IMolecule> readMolecules() throws Exception {
        File[] files = in_dir.listFiles(new FileFilter() {

            int chosen = 0;

            public boolean accept(File pathname) {
                if (chosen < POINTS) {
                    chosen++;
                    return pathname.getName().matches("\\w*\\.mol2");
                } else {
                    return false;
                }
            }
        });


        List<IMolecule> ms = new LinkedList<IMolecule>();
        for (File f : files) {
            IMolecule m = (IMolecule) new MoleculeReader().curry(f.getAbsolutePath()).call();
            AtomContainerManipulator.removeHydrogens(m);
            String n = f.getName().replace(".mol2", "");
            m.setID(n);
            ms.add(m);
        }


        return ms;
    }

    @Test
    public void TestVectorialSOM() throws FileNotFoundException, IOException {
        List<Vector> data = readVectorData("test-data" + File.separator + "yeast.data.short");
        System.out.println(data);

        SOMMaker<Vector, Vector> sommaker = new SOMMaker();
        sommaker.field_size(10, 10);
        sommaker.setShow_status_func(new ShowStatusFunc() {

            @Override
            public Void call() {
                SOMParams state = getState();
//                System.out.println(state.iterations + "\t" + state.projections);
                return null;
            }
        });
        sommaker.setMaxSOMIterations(1);

        SOM result = sommaker.makeVectorialSOM(data);

        result.call();
        System.out.println("TestVectorialSOM");
    }

    public List<Vector> readVectorData(String path) throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<Vector> vs = new LinkedList<Vector>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            Pattern p = Pattern.compile("\\s+"); // split on whitespace
            String[] data = p.split(line);
            Vector v = new Vector(data.length);
            for (String d : data) {
                v.add(Double.parseDouble(d));
            }
            vs.add(v);
        }
        return vs;
    }
}