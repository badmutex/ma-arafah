package tajmi.instances.cdk.som;

import java.util.logging.Level;
import java.util.logging.Logger;
import tajmi.abstracts.som.ViewField;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.CenterOfMassFunc;
import tajmi.instances.cdk.AtomContainerCenterOfMassFunc;
import tajmi.som.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.lang.Iterable;
import java.util.UUID;
import scala.Tuple2;
import tajmi.som.Position;
import java.io.*;
import tajmi.functional.instances.cdk.WriteMolecule;

/**
 *
 * @author badi
 */
public class WriteClusterCenters extends ViewField<FieldModel<IAtomContainer>> {

    String output_directory;
    WriteMolecule writer;

    public WriteClusterCenters setOutput_directory(String output_directory) {
        this.output_directory = output_directory;
        return this;
    }

    public String getOutput_directory() {
        return output_directory;
    }
    String output_prefix;

    public String getOutput_prefix() {
        return output_prefix;
    }

    public WriteClusterCenters setOutput_prefix(String output_prefix) {
        this.output_prefix = output_prefix;
        return this;
    }

    public WriteMolecule getMoleculeWriter() {
        return writer;
    }

    public WriteClusterCenters setMoleculeWriter(WriteMolecule writer) {
        this.writer = writer;
        return this;
    }

    @Override
    public Void call() {

        // 1) Put everything inter separate clusters
        Field<FieldModel<IAtomContainer>> field = getField();
        Map<String, List<IAtomContainer>> clusters = new HashMap<String, List<IAtomContainer>>(field.size());
        for (Tuple2<Position, FieldModel<IAtomContainer>> median : field) {

            FieldModel<IAtomContainer> clust = median._2();
            IAtomContainer m = clust.getGeneralizeMedian();
            String id = m.getID();

            if (clusters.containsKey(id)) {
                clusters.get(id).addAll(clust);
            } else {
                clusters.put(id, clust);
            }

        }

        // 2) Find the cluster centers
        CenterOfMassFunc<IAtomContainer> centerf = new AtomContainerCenterOfMassFunc();
        List<IAtomContainer> centers = new LinkedList<IAtomContainer>();
        for (List<IAtomContainer> cluster : clusters.values()) {
            IAtomContainer center = (IAtomContainer) centerf.params(cluster).call();
            centers.add(center);
        }

        // 3) write out the cluster centers
        File outdir = new File(getOutput_directory());
        if (!outdir.exists()) {
            outdir.mkdir();
        }

        String prefix = getOutput_prefix();
        WriteMolecule writer = getMoleculeWriter();
        int counter = 1;
        for (IAtomContainer center : centers) {

            String fname = prefix + "-" + counter;
            try {
                writer.curry(fname).curry(center).call();
            } catch (Exception ex) {
                Logger.getLogger(WriteClusterCenters.class.getName()).log(Level.SEVERE, "Could not write " + fname, ex);
            }
            counter++;

        }


        // ....and we're done!
        return null;
    }
}
