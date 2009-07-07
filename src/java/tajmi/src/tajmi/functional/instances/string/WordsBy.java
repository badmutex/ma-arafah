
package tajmi.functional.instances.string;

import tajmi.functional.instances.seq.*;
import java.util.List;
import tajmi.functional.interfaces.Fun;

/**
 *<code> WordsBy :: [String] -> regex : String -> [[String]]</code>
 * @author badi
 */
public class WordsBy implements Fun {

    Iterable<String> seq;
    String regex;

    public Fun copy() {
        return new WordsBy().curry(seq).curry(regex);
    }

    public Fun curry(Object arg) {
        if (seq == null)
            seq = (Iterable<String>) arg;
        else if (regex == null)
            regex = (String) arg;

        return this;
    }

    public List<List<String>> call() {
        return Seq.map(new BreakBy().curry(regex), seq);
    }

}
