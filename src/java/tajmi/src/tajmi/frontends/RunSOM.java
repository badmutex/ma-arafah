package tajmi.frontends;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.omegahat.Environment.IO.ExtensionFilenameFilter;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.functional.instances.cdk.ReadMolecule;
import tajmi.functional.instances.seq.Seq;
//import tajmi.instances.cdk.som.OpenGLFieldView;
import tajmi.som.Field;
import tajmi.som.SOM;

/**
 *
 * @author badi
 */
public class RunSOM {

    static final String DATA_DIR = "test-data";
    static File in_dir = new File(DATA_DIR + File.separator + "hiv1-inhibitors");

    public static void main(String[] args) throws Exception {
        int length = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        int max_iterations = Integer.parseInt(args[2]);


        SOMMaker sommaker = new SOMMaker();
        sommaker.field_size(length, width);
        sommaker.setMaxSOMIterations(max_iterations);
        SOM som = sommaker.makeIAtomContainerSOM(readMolecules());
        Field f = som.call();
//        new OpenGLFieldView().params(f).call();
    }

    public static List<IMolecule> readMolecules() throws Exception {
        File[] files = in_dir.listFiles(new ExtensionFilenameFilter(".mol2"));


        List<IMolecule> ms = new LinkedList<IMolecule>();
        for (File f : files) {
            IMolecule m = (IMolecule) new ReadMolecule().curry(f.getAbsolutePath()).call();
            AtomContainerManipulator.removeHydrogens(m);
            String n = f.getName().replace(".mol2", "");
            m.setID(n);
            ms.add(m);
        }

        Collections.shuffle(ms);


        return (List<IMolecule>) Seq.drop(150, ms);


//        return ms;
    }
}
