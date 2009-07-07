
package tajmi.functional.instances.string;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import tajmi.functional.interfaces.Fun;

/**
 *<code> BreakBy :: regex : String > String -> [String]</code>
 * @author badi
 */
public class BreakBy implements Fun {

    String regex;
    String str;

    public Fun copy() {
        return new BreakBy().curry(str).curry(regex);
    }

    public Fun curry(Object arg) {
        if (regex == null)
            regex = (String) arg;
        else if (str == null)
            str = (String) arg;

        return this;
    }

    public List<String> call() {
        String[] bits = Pattern.compile(regex).split(str);
        List<String> res = new LinkedList<String>();
        for (String s : bits)
            res.add(s);

        return res;
    }

}
