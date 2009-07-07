
package tajmi.functional.instances.seq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import tajmi.functional.interfaces.Fun;

/**
 *<code> Transpose :: [[a]] -> [[a]]</code>
 * @author badi
 */
public class Transpose implements Fun {

    List<List> seq;

    public Fun copy() {
        return new Transpose().curry(seq);
    }

    public Fun curry(Object arg) {
        if (seq == null)
            seq = (List<List>) arg;

        return this;
    }

    public List<List> call() {
        List<List> transposed = new ArrayList<List>(seq.get(0).size());
        for (List l : seq) {
            for (int i = 0; i < l.size(); i++) {
                if (transposed.size() <= i)
                    transposed.add(new LinkedList());
                transposed.get(i).add(l.get(i));
            }
        }
        return transposed;
    }

}
