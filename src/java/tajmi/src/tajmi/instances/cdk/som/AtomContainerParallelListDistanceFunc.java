
package tajmi.instances.cdk.som;

import java.util.List;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.DistanceFunc;
import tajmi.functional.instances.seq.Seq;

/**
 *
 * @author badi
 */
public class AtomContainerParallelListDistanceFunc extends AtomContainerGrainyParallelListDistanceFunc {

    @Override
    List<Double> parallel_calculation(DistanceFunc df, List<IAtomContainer> others) {
        return Seq.parallel_map(df, others);
    }



}
