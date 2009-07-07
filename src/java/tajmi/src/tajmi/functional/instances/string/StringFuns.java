
package tajmi.functional.instances.string;

import java.util.List;
import scala.Tuple2;

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

    public static String get_value_for_kw(String assignment_str, String keyword, List<String> lines) {
        return (String) new GetValueForKeyword().curry(assignment_str).curry(keyword).curry(lines).call();
    }

    public static Tuple2<String, Double> parse_molecular_descriptor (String assignment_str, String descriptor_line) {
        return (Tuple2<String, Double>) new ParseMolecularDescriptor().curry(assignment_str).curry(descriptor_line).call();
    }

    public static String replace_suffix (String str, String replacement) {
        return (String) new ReplaceSuffix().curry(str).curry(replacement).call();
    }
}
