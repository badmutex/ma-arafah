
package tajmi.instances.cdk.som;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.openscience.cdk.interfaces.IAtomContainer;
import scala.Tuple2;
import tajmi.functional.interfaces.Fun;
import tajmi.som.Field;
import tajmi.som.Position;

/**
 *
 * @author badi
 */
public class CollectClusters implements Fun {

    Field f;

    public Fun copy() {
        return new CollectClusters().curry(f);
    }

    public Fun curry(Object arg) {
        if (f == null)
            f = (Field) arg;

        return this;
    }

    public Map<String, Collection<IAtomContainer>> call() {
        Field<FieldModel<IAtomContainer>> field = this.f;
        Map<String, Collection<IAtomContainer>> clusters = new HashMap<String, Collection<IAtomContainer>>(field.size());
        for (Tuple2<Position, FieldModel<IAtomContainer>> median : field) {

            FieldModel<IAtomContainer> clust = median._2();
            IAtomContainer m = clust.getGeneralizeMedian();

            if (m != null) {
                String id = m.getID();

                if (clusters.containsKey(id)) {
                    clusters.get(id).addAll(clust);
                } else {
                    clusters.put(id, clust);
                }
            }
        }

        return clusters;
    }

}
