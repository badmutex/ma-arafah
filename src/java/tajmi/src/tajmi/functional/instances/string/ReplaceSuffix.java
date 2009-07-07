
package tajmi.functional.instances.string;

import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class ReplaceSuffix implements Fun {

    String str;
    String replacement;

    public Fun copy() {
        return new ReplaceSuffix().curry(str).curry(replacement);
    }

    public Fun curry(Object arg) {
        if (str == null)
            str = (String) arg;
        else if (replacement == null)
            replacement = (String) arg;

        return this;
    }

    public String call() {
        String base = str.split("\\.\\w+$")[0];
        return base + replacement;
    }

}
