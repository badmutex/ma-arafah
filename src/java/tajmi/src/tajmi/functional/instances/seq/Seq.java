package tajmi.functional.instances.seq;

import tajmi.functional.instances.string.BreakBy;
import tajmi.functional.instances.string.WordsBy;
import java.util.List;
import scala.Tuple2;
import tajmi.functional.interfaces.Fun;

/**
 * Provides wrapper functions for calling functions in tajmi.functions.instances.seq
 * @author badi
 */
public class Seq {

    public static List take(Integer n, Iterable sequence)  {
        return (List) new Take().curry(n).curry(sequence).call();
    }

    public static Iterable drop(Integer n, Iterable sequence) {
        return (Iterable) new Drop().curry(n).curry(sequence).call();
    }

    public static List filter(Fun filter_by, Iterable sequence)  {
        return (List) new Filter().curry(filter_by).curry(sequence).call();
    }

    public static Object fold(Fun fold_by, Object init, Iterable sequence)  {
        return new Fold().curry(fold_by).curry(init).curry(sequence).call();
    }

    public static List map(Fun f, Iterable sequence)  {
        return (List) new Map().curry(f).curry(sequence).call();
    }

    public static List grainy_parallel_map(Fun f, Iterable seq, int grain_size)  {
        return (List) new GrainyParallelMap().curry(f).curry(seq).curry(grain_size).call();
    }

    public static List parallel_map(Fun f, Iterable sequence)  {
        return (List) new ParallelMap().curry(f).curry(sequence).call();
    }

    public static Object head(Iterable sequence)  {
        return new Head().curry(sequence).call();
    }

    public static List tail(Iterable sequence)  {
        return (List) new Tail().curry(sequence).call();
    }

    public static Object minimum_by(Fun f, Iterable sequence)  {
        return new MinimumBy().curry(f).curry(sequence).call();
    }

    public static List<Tuple2> zip(Iterable itrA, Iterable itrB)  {
        return (List<Tuple2>) new Zip().curry(itrA).curry(itrB).call();
    }

    public static List zip_with(Fun f, Iterable itr)  {
        return (List) new ZipWith().curry(f).curry(itr).call();
    }

    public static List<List> transpose(List<List> seq) {
        return (List<List>) new Transpose().curry(seq).call();
    }

    public static Boolean elem (Object o, Iterable os) {
        return (Boolean) new Elem().curry(o).curry(os).call();
    }
}
