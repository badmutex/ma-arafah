
package tajmi.instances.cdk.som;

import java.util.List;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.DistanceFunc;
import tajmi.functional.instances.seq.Seq;

/**
 *
 * @author badi
 */
public class AtomContainerGrainyParallelListDistanceFunc extends AtomContainerListDistanceFunc {

    @Override
    double calculate_overall_distance(DistanceFunc df, List<IAtomContainer> others) {
        List<Double> distances =
                Seq.grainy_parallel_map(df, others, others.size() / 100);// Runtime.getRuntime().availableProcessors());

        double distance = 0.0;
        for(Double d : distances)
            distance += d;

        return distance;
    }


}
