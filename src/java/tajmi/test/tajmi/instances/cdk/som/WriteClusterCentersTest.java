package tajmi.instances.cdk.som;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import tajmi.abstracts.som.InitFunc;
import tajmi.abstracts.som.ViewField;
import tajmi.functional.instances.cdk.WriteMolecule;
import tajmi.som.Field;
import org.openscience.cdk.io.formats.SMILESFormat;
import tajmi.som.Position;
import scala.Tuple2;
import tajmi.functional.instances.cdk.ReadMolecule;

/**
 *
 * @author badi
 */
public class WriteClusterCentersTest {

    String in_dir = "test-data" + File.separator + "hiv1-inhibitors";
    String out_dir = "test-data" + File.separator + "WriteClusterCentersTest";
    final int POINTS = 20;
    final int FIELD_WIDTH = 5, FIELD_LENGTH = 5;

    @Test
    public void writeClusterCentersTest() throws Exception {
        String out_file = out_dir + File.separator + "writeClusterCentersTest";

        List<IAtomContainer> molecs = readMolecules();
        Field<FieldModel<IAtomContainer>> field = genField(molecs);

        WriteMolecule mwriter = (WriteMolecule) new WriteMolecule().curry(SMILESFormat.getInstance());

        ViewField<FieldModel<IAtomContainer>> viewer = new WriteClusterCenters()
                .setMoleculeWriter(mwriter)
                .setOutput_directory(out_dir)
                .setOutput_prefix(out_file);


        viewer.params(field).call();

        System.out.println("writeClusterCentersTest");
    }

//    @Test
    public void writeField () throws Exception {
        WriteMolecule mwriter = (WriteMolecule) new WriteMolecule().curry(SMILESFormat.getInstance());

        if (! new File(out_dir).isDirectory())
            new File(out_dir).mkdir();

        String out_file = out_dir + File.separator + "writeFieldTest";
        int counter = 0;
        Field<FieldModel<IAtomContainer>> field = genField(readMolecules());
        for (Tuple2<Position, FieldModel<IAtomContainer>> m : field) {
            IAtomContainer median = m._2().getGeneralizeMedian();
            String out_file_2 = out_file + "-" + counter++;
            mwriter.copy().curry(out_file_2).curry(median).call();
            System.out.println("wrote " + out_file_2 + ".smi");
        }

        System.out.println("writeField");
    }

    private List<IAtomContainer> readMolecules() throws Exception {
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


        List<IAtomContainer> ms = new LinkedList<IAtomContainer>();
        for (File f : files) {
            IAtomContainer m = (IAtomContainer) new ReadMolecule().curry(f.getAbsolutePath()).call();
            AtomContainerManipulator.removeHydrogens(m);
            String n = f.getName().replace(".mol2", "");
            m.setID(n);
            ms.add(m);
        }


        return ms;
    }

    private Field<FieldModel<IAtomContainer>> genField(List<IAtomContainer> molecs) {

        InitFunc initf = new AtomContainerInitFunc();
        Random rand = new Random(42);
        initf.params(molecs, rand);
        Field<FieldModel<IAtomContainer>> field = new Field(FIELD_LENGTH, FIELD_WIDTH, initf);

        for (Tuple2<Position, FieldModel<IAtomContainer>> m : field)
            m._2().setGeneralizeMedian(m._2().get(0));

        return field;
    }

}