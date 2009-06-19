
package tajmi.instances.cdk.som;

import java.util.Collection;
import java.util.List;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.abstracts.DistanceFunc;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.functional.instances.seq.Seq;
import tajmi.som.StatusUpdater;

/**
 *
 * @author badi
 */
public class AtomContainerGrainyParallelListDistanceFunc extends AtomContainerListDistanceFunc {

    @Override
    double calculate_overall_distance(DistanceFunc df, Collection<IAtomContainer> others) {
        List<Double> distances = parallel_calculation (df, others);
        return sum_distances(distances);
    }

    List<Double> parallel_calculation(DistanceFunc df, Collection<IAtomContainer> others) {
        return Seq.grainy_parallel_map(df, others, others.size() / 100);// Runtime.getRuntime().availableProcessors());
    }

    double sum_distances (List<Double> distances) {
        double distance = 0.0;
        for(Double d : distances)
            distance += d;

        return distance;
    }
}
