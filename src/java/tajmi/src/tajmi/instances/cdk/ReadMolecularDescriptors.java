
package tajmi.instances.cdk;

import java.io.File;
import java.util.List;
import scala.Tuple2;
import tajmi.functional.instances.io.IO;
import tajmi.functional.instances.seq.Seq;
import tajmi.functional.instances.string.ParseMolecularDescriptor;
import tajmi.functional.instances.string.StringFuns;
import tajmi.functional.interfaces.Fun;

/**
 * for single molecule
 * @author badi
 */
public class ReadMolecularDescriptors implements Fun {

    String comment_str;
    String assignment_str;
    String descriptor_file_path;

    public Fun copy() {
        return new ReadMolecularDescriptors().curry(comment_str).curry(assignment_str).curry(descriptor_file_path);
    }

    public Fun curry(Object arg) {
        if (comment_str == null)
            comment_str = (String) arg;
        else if (assignment_str == null)
            assignment_str = (String) arg;
        else if (descriptor_file_path == null)
            descriptor_file_path = (String) arg;

        return this;
    }

    public Tuple2<String, List<Tuple2<String, Double>>> call() {
        String base = StringFuns.replace_suffix(descriptor_file_path, "");
        List<String> lines = IO.read_decommented_file(comment_str, base + ".desc");
        String name = StringFuns.replace_suffix(new File(descriptor_file_path).getName(), "");
        return new Tuple2(name, Seq.map(new ParseMolecularDescriptor().curry(assignment_str), lines));
    }

}
