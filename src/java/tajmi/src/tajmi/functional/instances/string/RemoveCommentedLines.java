
package tajmi.functional.instances.string;

import java.util.LinkedList;
import java.util.List;
import tajmi.functional.instances.seq.Seq;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class RemoveCommentedLines implements Fun {

    String comment_str;
    Iterable<String> lines;

    public Fun copy() {
        return new RemoveCommentedLines().curry(comment_str).curry(lines);
    }

    public Fun curry(Object arg) {
        if (comment_str == null)
            comment_str = (String) arg;
        else if (lines == null)
            lines = (Iterable<String>) arg;

        return this;
    }

    public List<String> call() {
        class NoComments implements Fun {

            String comment_str;
            List<String> lines;
            String line;

            public Fun copy() {
                return new NoComments().curry(comment_str).curry(lines).curry(line);
            }

            public Fun curry(Object arg) {
                if (comment_str == null)
                    comment_str = (String) arg;
                else if (lines == null) {
                    lines = (List<String>) arg;
                } else if (line == null) {
                    line = (String) arg;
                }

                return this;
            }

            public List<String> call()  {
                String l = line;
                if (line.contains(comment_str)) {
                    l = line.substring(0, l.indexOf(comment_str));
                    l = l.trim();
                }
                if (l.length() > 0)
                    lines.add(l);
                return lines;
            }
        }

        return (List<String>) Seq.fold(new NoComments().curry(comment_str), new LinkedList(), lines);
    }

}
