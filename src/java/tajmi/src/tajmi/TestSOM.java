package tajmi;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import scala.Tuple2;
import tajmi.Settings.Variables;
import tajmi.abstracts.som.ViewField;
import tajmi.frontends.SOMMaker;
import tajmi.functional.instances.cdk.ReadMolecule;
import tajmi.functional.instances.cdk.WriteMolecule;
import tajmi.instances.cdk.som.AtomContainerGrainyParallelListDistanceFunc;
import tajmi.instances.cdk.som.FieldModel;
import tajmi.instances.cdk.som.OpenGLFieldView;
import tajmi.instances.cdk.som.WriteClusterCenters;
import tajmi.som.Field;
import tajmi.som.SOM;
import tajmi.som.StatusUpdater;

/**
 *
 * @author badi
 */
public class TestSOM {

    public static void main (String[] args) throws Exception{
        new Test().TestIAtomContainerSOM();
    }

    private static class Test {

        final static String TEST_CONFIG_FILE = "test-data" + File.separator + "amino-acids.config";

        public void TestIAtomContainerSOM() throws Exception {
            SOM som = moleculeSOM();
            Field<FieldModel<IAtomContainer>> f = som.call();
            for (Tuple2 model : f) {
                FieldModel<IAtomContainer> m = (FieldModel<IAtomContainer>) model._2();
                if (m.getGeneralizeMedian() != null) System.out.println(m.getGeneralizeMedian().getID());
            }

            ViewField viewer = new OpenGLFieldView();
            viewer.params(f);
            viewer.call();
//            Object o = new CollectClusterCenters().curry(f).call();
            Map<Settings.Variables, Object> settings = new Settings(TEST_CONFIG_FILE).get_configuration();
            Boolean write_mcss = (Boolean) settings.get(Settings.Variables.write_cluster_mcss);
            if (write_mcss) {
                String output_dir = (String) settings.get(Settings.Variables.cluster_mcss_directory);
                String prefix = (String) settings.get(Variables.mcss_prefix);
                WriteClusterCenters write = new WriteClusterCenters();
                write.setOutput_directory(output_dir);
                write.setOutput_prefix(prefix);
                WriteMolecule w = (WriteMolecule) new WriteMolecule().curry(settings.get(Variables.mcss_format));
                write.setMoleculeWriter(w);

                write.params(f);
                write.call();
            }

            System.out.println("TestIAtomContainerSOM");
        }

        public SOM<IAtomContainer, FieldModel<IAtomContainer>> moleculeSOM() throws Exception {
            SOMMaker sommaker = new SOMMaker();
            sommaker.field_size(10, 10);
            sommaker.setMaxSOMIterations(1);
//            sommaker.setDistance_func(new AtomContainerGrainyParallelListDistanceFunc());
//            sommaker.setDistance_func(new AtomContainerParallelListDistanceFunc());
            StatusUpdater.getInstance().set_verbosity_level(StatusUpdater.Verbosity.Everything);

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
