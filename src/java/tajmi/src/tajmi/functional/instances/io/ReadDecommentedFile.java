
package tajmi.functional.instances.io;

import java.util.List;
import tajmi.functional.instances.string.StringFuns;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class ReadDecommentedFile implements Fun {

    String comment_str;
    String path;

    public Fun copy() {
        return new ReadDecommentedFile().curry(comment_str).curry(path);
    }

    public Fun curry(Object arg) {
        if (comment_str == null)
            comment_str = (String) arg;
        else if (path == null)
            path = (String) arg;

        return this;
    }

    public List<String> call() {
        List<String> lines = IO.read_file(path);
        List<String> uncommented_lines = StringFuns.remove_commented_lines(comment_str, lines);

        return uncommented_lines;
    }

}
