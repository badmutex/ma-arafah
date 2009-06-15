package tajmi;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.frontends.SOMMaker;
import tajmi.functional.instances.cdk.ReadMolecule;
import tajmi.instances.cdk.som.FieldModel;
import tajmi.som.SOM;
import tajmi.som.SOMParams;

/**
 *
 * @author badi
 */
public class TestSOM {

    public static void main (String[] args) throws Exception{
        new Test().TestIAtomContainerSOM();
    }

    private static class Test {

        final static String TEST_CONFIG_FILE = "test-data" + File.separator + "test.config";

        public void TestIAtomContainerSOM() throws Exception {
            SOM som = moleculeSOM();
            System.out.println(som.call());

            System.out.println("TestIAtomContainerSOM");
        }

        public SOM<IAtomContainer, FieldModel<IAtomContainer>> moleculeSOM() throws Exception {
            SOMMaker sommaker = new SOMMaker();
            sommaker.field_size(10, 10);
            sommaker.setMaxSOMIterations(2);
            sommaker.setShow_status_func(new ShowStatusFunc() {

                @Override
                public Void call() {
                    SOMParams state = getState();
                    System.out.println(state.iterations + "\t" + state.projections);
                    return null;
                }
            });

            List<IMolecule> data = readMolecules();
            SOM som = sommaker.makeIAtomContainerSOM(data);

            return som;
        }

        public List<IMolecule> readMolecules() throws Exception {

            Settings settings = new Settings(TEST_CONFIG_FILE);
            List<String> files = (List<String>) settings.get_configuration().get(Settings.Variables.molecule_names);

            List<IMolecule> ms = new LinkedList<IMolecule>();
            for (String path : files) {
                File f = new File(path);
                IMolecule m = (IMolecule) new ReadMolecule().curry(f.getAbsolutePath()).call();
                AtomContainerManipulator.removeHydrogens(m);
                String n = f.getName().replace(".mol2", "");
                m.setID(n);
                ms.add(m);
            }


            return ms;
        }
    }
}
