
package tajmi.functional.instances.string;

import java.util.List;
import java.util.regex.Pattern;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class GetValueForKeyword implements Fun {

    String assignment_str;
    String kw;
    List<String> lines;

    public Fun copy() {
        return new GetValueForKeyword().curry(kw).curry(lines);
    }

    public Fun curry(Object arg) {
        if (assignment_str == null)
            assignment_str = (String) arg;
        else if (kw == null)
            kw = (String) arg;
        else if (lines == null)
            lines = (List<String>) arg;

        return this;
    }

    public String call() {
        String val = null;
        for (String line : lines) {
            if (line.startsWith(kw)) {
                Pattern pat = Pattern.compile("\\s*" + assignment_str + "\\s*");
                String[] bitsnpieces = pat.split(line);
                val = bitsnpieces[1];
                break;
            }
        }
        return val;
    }

}
