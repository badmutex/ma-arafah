
package tajmi.instances.cdk.som;

import tajmi.abstracts.CenterOfMassFunc;
import tajmi.functional.interfaces.Fun;
import tajmi.instances.cdk.AtomContainerCenterOfMassFunc;
import org.openscience.cdk.interfaces.IAtomContainer;
import tajmi.som.Field;
import tajmi.som.Position;
import scala.Tuple2;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import tajmi.abstracts.som.ShowStatusFunc;
import tajmi.som.StatusUpdater;
/**
 *
 * @author badi
 */
public class CollectClusterCenters implements Fun {

    Field field = null;

    public Fun copy() {
        return new CollectClusterCenters().curry(field);
    }

    public Fun curry(Object arg) {
        if (field == null)
            field = (Field) arg;

        return this;
    }

    public Iterable<IAtomContainer> call() {
        // 1) Put everything inte separate clusters
        Map<String, Collection<IAtomContainer>> clusters = (Map<String, Collection<IAtomContainer>>) new CollectClusters().curry(field).call();

        // 2) Find the cluster centers
        CenterOfMassFunc<IAtomContainer> centerf = new AtomContainerCenterOfMassFunc();
        List<IAtomContainer> centers = new LinkedList<IAtomContainer>();
        for (Collection<IAtomContainer> cluster : clusters.values()) {
            IAtomContainer center = (IAtomContainer) centerf.params(cluster).call();
            centers.add(center);
        }



        return centers;
    }
}
