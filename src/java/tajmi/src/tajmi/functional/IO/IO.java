
package tajmi.functional.IO;

import java.util.List;

/**
 *
 * @author badi
 */
public class IO {

    public static List<String> lines (String path) {
        return (List<String>) new Lines().curry(path).call();
    }
}
