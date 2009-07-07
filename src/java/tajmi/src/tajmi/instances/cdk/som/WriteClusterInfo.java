package tajmi.instances.cdk.som;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.functional.interfaces.Fun;
import tajmi.instances.cdk.ReadAllMoleculeDescriptors;
import tajmi.som.Field;

/**
 *
 * @author badi
 */
public class WriteClusterInfo implements Fun {

    String comment_str;
    String assignment_str;
    Iterable<String> molecule_names;
    String cluster_prefix;
    Field f;

    public Fun copy() {
        return new WriteClusterInfo().curry(comment_str).curry(assignment_str).curry(molecule_names).curry(cluster_prefix).curry(f);
    }

    public Fun curry(Object arg) {
        if (comment_str == null) {
            comment_str = (String) arg;
        } else if (assignment_str == null) {
            assignment_str = (String) arg;
        } else if (molecule_names == null) {
            molecule_names = (Iterable<String>) arg;
        } else if (cluster_prefix == null) {
            cluster_prefix = (String) arg;
        } else if (f == null) {
            f = (Field) arg;
        }

        return this;
    }

    public Void call() {
        // 1st we need to read in the descriptors
        Map<String, List<Tuple2<String, Double>>> descriptors =
                (Map<String, List<Tuple2<String, Double>>>) new ReadAllMoleculeDescriptors().curry(comment_str).curry(assignment_str).curry(molecule_names).call();

        // 2nd get the clusters
        Map<String, Collection<IAtomContainer>> clusters =
                (Map<String, Collection<IAtomContainer>>) new CollectClusters().curry(f).call();

        // 3rd for each cluster write that section to the file
        int cluster_counter = 1;
        for (Collection<IAtomContainer> ms : clusters.values()) {
            PrintStream out = null;
            try {
                String formatted;
                String file = cluster_prefix + cluster_counter + ".txt";
                out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
                for (IAtomContainer m : ms) {
                    formatted = format(m.getID(), descriptors);
                    out.append(formatted + "\n");
                }
                cluster_counter++;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WriteClusterInfo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
        }

        return null;
    }

    private String format(String name, Map<String, List<Tuple2<String, Double>>> descriptors) {
        String fmt = name + "\n";
        for (Tuple2<String, Double> desc : descriptors.get(name)) {
            fmt += String.format("%s = %.4f\n", desc._1(), desc._2());
        }
        return fmt;
    }
}
