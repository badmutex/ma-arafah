
package tajmi.instances.vectorial;

import java.util.Iterator;
import tajmi.interfaces.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class VectorCenterOfMassFunc extends CenterOfMassFunc<Vector> {

    public Vector call () {
        Iterable<Vector> cluster = getCluster();
        
        int size = 0;
        Iterator<Vector> itr = cluster.iterator();
        Vector accum = itr.next();
        size++;

        // SUM_{x in C} x
        while(itr.hasNext()){
            size++;
            accum = accum.add(itr.next());
        }

        // result = map (* (1/|C_i|)) accum
        Vector result = new Vector(accum.size());
        for(int i = 0; i < accum.size(); i++){
            result.sum((Double) accum.get(i) / size);
        }

        return result;
    }
}
