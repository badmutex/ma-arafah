
package tajmi.instances.vectorial;

import java.util.Iterator;
import tajmi.functional.interfaces.Fun;
import tajmi.interfaces.CenterOfMassFunc;

/**
 *
 * @author badi
 */
public class VectorCenterOfMassFunc implements CenterOfMassFunc<Vector> {

    Iterable<Vector> cluster;

    public VectorCenterOfMassFunc params(Iterable<Vector> cluster) {
        return (VectorCenterOfMassFunc) this.curry(cluster);
    }

    public Vector call () {
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

    public Fun copy() {
        return new VectorCenterOfMassFunc().curry(cluster);
    }

    /** arg should be an Iterable over Vectors */
    public Fun curry(Object arg) {

        if (cluster == null)
            cluster = (Iterable<Vector>) arg;

        return this;
    }

}
