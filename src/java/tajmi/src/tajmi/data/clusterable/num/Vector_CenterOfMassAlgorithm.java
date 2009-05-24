
package tajmi.data.clusterable.num;

import java.util.Iterator;
import tajmi.data.clusterable.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class Vector_CenterOfMassAlgorithm implements CenterOfMassFunc<Vector> {

    Iterable<Vector> cluster;

    public Vector_CenterOfMassAlgorithm params(Iterable<Vector> cluster) {
        this.cluster = cluster;
        return this;
    }

    public Vector call () {
        int size = 0;
        Iterator<Vector> itr = cluster.iterator();
        Vector accum = itr.next();
        size++;

        // SUM_{x in C} x
        while(itr.hasNext()){
            size++;
            accum = accum.sum(itr.next());
        }

        // result = map (* (1/|C_i|)) accum
        Vector result = new Vector(accum.size());
        for(int i = 0; i < accum.size(); i++){
            result.add((Double) accum.get(i) / size);
        }

        return result;
    }

}
