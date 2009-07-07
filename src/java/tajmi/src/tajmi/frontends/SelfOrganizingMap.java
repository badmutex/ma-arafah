
package tajmi.frontends;

import tajmi.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import tajmi.Settings.InvalidConfigurationType;
import tajmi.Settings.Variables;
import tajmi.frontends.SOMMaker;
import tajmi.som.SOM;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.abstracts.som.StopFunc;
import tajmi.abstracts.som.ViewField;
import tajmi.functional.instances.cdk.ReadMolecule;
import tajmi.functional.instances.cdk.WriteMolecule;
import tajmi.instances.cdk.som.OpenGLFieldView;
import tajmi.instances.cdk.som.WriteClusterCenters;
import tajmi.instances.som.IterationsStopFunc;
import tajmi.som.Field;
import tajmi.som.StatusUpdater;
import tajmi.som.StatusUpdater.Verbosity;

/**
 *
 * @author badi
 */
public class SelfOrganizingMap {
    public static void main (String[] args) throws FileNotFoundException, IOException, InvalidConfigurationType, Exception{
        // need path to config file
        if (args.length != 1) {
            System.out.println("Usage: cmd <path to config file>");
            System.exit(1);
        }

        Map<Settings.Variables, Object> config = new Settings(args[0]).get_configuration();
        SOM som = build_molecule_som (config);
        Field f = som.call();
        handle_results(f, config);
        
    }

    private static SOM build_molecule_som (Map<Settings.Variables, Object> config) {
        SOMMaker sommaker = new SOMMaker();

        // field dimensions
        int field_length = (Integer) config.get(Variables.field_length);
        int field_width = (Integer) config.get(Variables.field_width);
        sommaker.field_size(field_length, field_width);

        // verbosity
        Verbosity v = (Verbosity) config.get(Variables.verbosity);
        StatusUpdater.getInstance().set_verbosity_level(v);

        // stop after so many iterations
        StopFunc sf = new IterationsStopFunc().setIterations((Integer) config.get(Variables.max_iterations));
        sommaker.setStop_func(sf);

        // get the data to be projected
        List<IMolecule> molecules = read_molecules ((List<String>) config.get(Variables.molecule_names));

        return sommaker.makeIAtomContainerSOM(molecules);
    }

    private static List<IMolecule> read_molecules (List<String> filenames) {
        List<IMolecule> ms = new LinkedList<IMolecule>();
        for (String path : filenames) {
            File f = new File(path);
            IMolecule m = (IMolecule) new ReadMolecule().curry(f.getAbsolutePath()).call();
            AtomContainerManipulator.removeHydrogens(m);
            m.setID(f.getName());
            ms.add(m);
        }
        return ms;
    }

    private static void handle_results (Field f, Map<Variables, Object> config) {
        if ((Boolean) config.get(Variables.show_opengl_viewer)) {
            ViewField viewer = new OpenGLFieldView();
            viewer.params(f);
            viewer.call();
        }

        if ((Boolean) config.get(Settings.Variables.write_cluster_mcss)) {
            String output_dir = (String) config.get(Settings.Variables.cluster_mcss_directory);
            String prefix = (String) config.get(Variables.mcss_prefix);
            WriteClusterCenters write = new WriteClusterCenters();
            write.setOutput_directory(output_dir);
            write.setOutput_prefix(prefix);
            WriteMolecule w = (WriteMolecule) new WriteMolecule().curry(config.get(Variables.mcss_format));
            write.setMoleculeWriter(w);

            write.params(f);
            write.call();
        }
    }
}
