
package tajmi.instances.cdk;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import scala.Tuple2;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class ReadAllMoleculeDescriptors implements Fun {
    String comment_str;
    String assignment_str;
    Iterable<String> descriptor_file_paths;

    public Fun copy() {
        return new ReadAllMoleculeDescriptors().curry(comment_str).curry(assignment_str).curry(descriptor_file_paths);
    }

    public Fun curry(Object arg) {
        if (comment_str == null)
            comment_str = (String) arg;
        else if (assignment_str == null)
            assignment_str = (String) arg;
        else if (descriptor_file_paths == null)
            descriptor_file_paths = (Iterable<String>) arg;

        return this;
    }

    public Map<String, List<Tuple2<String, Double>>> call() {
        Fun f = new ReadMolecularDescriptors().curry(comment_str).curry(assignment_str);

        Map<String, List<Tuple2<String, Double>>> descriptors = new Hashtable<String, List<Tuple2<String, Double>>>();
        for (String path : descriptor_file_paths) {
            Tuple2<String, List<Tuple2<String, Double>>>
                    descs = (Tuple2<String, List<Tuple2<String, Double>>>) f.copy().curry(path).call();
            descriptors.put(descs._1(), descs._2());
        }

        return descriptors;
    }
}
