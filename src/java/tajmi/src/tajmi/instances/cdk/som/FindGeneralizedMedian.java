
package tajmi.instances.cdk.som;

import java.util.concurrent.Callable;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class FindGeneralizedMedian implements Callable<IAtomContainer> {

    Iterable<Tuple2<Double, IAtomContainer>> molecule_set;

    public FindGeneralizedMedian params (Iterable<Tuple2<Double, IAtomContainer>> molecule_set) {
        this.molecule_set = molecule_set;
        return this;
    }

    public IAtomContainer call() {
        return null;
    }

}
