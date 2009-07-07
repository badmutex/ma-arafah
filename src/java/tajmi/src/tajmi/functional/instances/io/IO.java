
package tajmi.functional.instances.io;

import java.util.List;

/**
 *
 * @author badi
 */
public class IO {

    public static List<String> read_file (String path) {
        return (List<String>) new ReadFile().curry(path).call();
    }

    public static List<String> read_decommented_file (String comment_str, String path) {
        return (List<String>) new ReadDecommentedFile().curry(comment_str).curry(path).call();
    }
}
