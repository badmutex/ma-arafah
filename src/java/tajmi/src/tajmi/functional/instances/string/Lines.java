
package tajmi.functional.instances.string;

import java.util.List;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class Lines implements Fun {

    String str;

    public Fun copy() {
        return new Lines().curry(str);
    }

    public Fun curry(Object arg) {
        if (str == null)
            str = (String) arg;

        return this;
    }

    public List<String> call() {
        return StringFuns.break_by("$", str);
    }

}
