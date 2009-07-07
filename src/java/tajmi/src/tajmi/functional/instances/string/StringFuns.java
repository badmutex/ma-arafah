
package tajmi.functional.instances.string;

import java.util.List;

/**
 *
 * @author badi
 */
public class StringFuns {

    public static List<String> break_by (String regex, String str) {
        return (List<String>) new BreakBy().curry(regex).curry(str).call();
    }

    public static List<List<String>> words_by (List<String> lines, String regex) {
        return (List<List<String>>) new WordsBy().curry(lines).curry(regex).call();
    }

    public static List<String> lines (String str) {
        return (List<String>) new Lines().curry(str).call();
    }

    public static List<String> remove_commented_lines (String comment_str, Iterable<String> lines) {
        return (List<String>) new RemoveCommentedLines().curry(comment_str).curry(lines).call();
    }
}
