
package tajmi.functional.instances.string;

import java.util.regex.Pattern;
import scala.Tuple2;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class ParseMolecularDescriptor implements Fun {

    String assignment_str;
    String descriptor_line;

    public Fun copy() {
        return new ParseMolecularDescriptor().curry(assignment_str).curry(descriptor_line);
    }

    public Fun curry(Object arg) {
        if (assignment_str == null)
            assignment_str = (String) arg;
        else if (descriptor_line == null)
            descriptor_line = (String) arg;

        return this;
    }

    public Tuple2<String, Double> call() {
        String[] pair = Pattern.compile(assignment_str).split(descriptor_line);
        return new Tuple2<String, Double>(pair[0].trim(), Double.parseDouble(pair[1]));
    }

}
