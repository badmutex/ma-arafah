package tajmi.instances.cdk.som;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import tajmi.functional.instances.cdk.MoleculeReader;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.abstracts.som.InitFunc;
import tajmi.abstracts.som.ViewField;
import tajmi.functional.instances.cdk.MoleculeWriter;
import tajmi.som.Field;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.formats.IChemFormat;
import org.openscience.cdk.io.formats.SMILESFormat;
import tajmi.som.Position;
import scala.Tuple2;

/**
 *
 * @author badi
 */
public class WriteClusterCentersTest {

    String in_dir = "test-data" + File.separator + "hiv1-inhibitors";
    String out_dir = "test-data" + File.separator + "WriteClusterCentersTest";
    String out_file = out_dir + File.separator + "test";
    final int POINTS = 20;
    final int FIELD_WIDTH = 10, FIELD_LENGTH = 10;

    @Test
    public void WriteClusterCentersTest() throws Exception {
        List<IMolecule> molecs = readMolecules();
        Field<FieldModel<IAtomContainer>> field = genField(molecs);

        MoleculeWriter mwriter = (MoleculeWriter) new MoleculeWriter().curry(SMILESFormat.getInstance());

        ViewField<FieldModel<IAtomContainer>> viewer = new WriteClusterCenters()
                .setMoleculeWriter(mwriter)
                .setOutput_directory(out_dir)
                .setOutput_prefix(out_file);


        viewer.params(field).call();
    }

    private List<IMolecule> readMolecules() throws Exception {
        File dir = new File(in_dir);
        File[] files = dir.listFiles(new FileFilter() {

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

    private Field<FieldModel<IAtomContainer>> genField(List<IMolecule> molecs) {

        InitFunc initf = new AtomContainerInitFunc();
        Random rand = new Random(42);
        initf.params(molecs, rand);
        Field<FieldModel<IAtomContainer>> field = new Field(FIELD_LENGTH, FIELD_WIDTH, initf);

        for (Tuple2<Position, FieldModel<IAtomContainer>> m : field)
            m._2().setGeneralizeMedian(m._2().get(0));

        return field;
    }
}