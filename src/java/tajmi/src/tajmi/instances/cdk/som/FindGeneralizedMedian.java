
package tajmi.instances.cdk.som;

import java.util.concurrent.Callable;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.functional.Ord;
import tajmi.functional.instances.seq.MinimumBy;
import tajmi.functional.interfaces.Fun;

/**
 *
 * @author badi
 */
public class FindGeneralizedMedian implements Callable<Tuple2<Double, IAtomContainer>> {

    Iterable<Tuple2<Double, IAtomContainer>> molecule_set;

    public FindGeneralizedMedian params (Iterable<Tuple2<Double, IAtomContainer>> molecule_set) {
        this.molecule_set = molecule_set;
        return this;
    }

    public Tuple2<Double, IAtomContainer> call() {
        return (Tuple2<Double, IAtomContainer>) new MinimumBy().curry(new Compare()).curry(molecule_set).call();
    }

    private class Compare implements Fun {

        Tuple2<Double, IAtomContainer> first, second;

        public Fun copy() {
            return new Compare().curry(first).curry(second);
        }

        public Fun curry(Object arg) {
            if (first == null)
                first = (Tuple2<Double, IAtomContainer>) arg;
            else if (second == null)
                second = (Tuple2<Double, IAtomContainer>) arg;

            return this;
        }

        public Ord call() {
            double d1 = first._1();
            double d2 = second._1();

            Ord cmp;
            if (d1 < d2) cmp = Ord.LT;
            else if (d1 == d2) cmp = Ord.EQ;
            else cmp = Ord.GT;

            return cmp;
        }

    }

}
