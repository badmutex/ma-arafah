
package tajmi.data.clusterable.instances.num;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;
import tajmi.data.clusterable.interfaces.DistanceFunc;

/**
 * Vector of numerical data to be clustered
 * @author badi
 */
public class Vector extends ArrayList implements List, Iterable {

    public Vector(){ super(); }
    public Vector(int size){ super(size); }
    public Vector(Collection c){ super(c); }

    public Vector sum(Vector other){
        assert this.size() == other.size() : "Vector size mismatch: " + this.size() + " <-> " + other.size();

        Vector result = new Vector(this.size());

        // Java needs Type Classes! Have to use Double to ensure accuracy
        for(Tuple2<Double,Double> tup : Util.zip(this, other)){
            result.add(tup._1() + tup._2());
        }
        return result;
    }

    public double distance (Vector other) {
        assert this.size() == other.size() : "Vector size mismatch: " + this.size() + " <-> " + other.size();

        DistanceFunc distancef = new Vector_DistanceAlgorithm();
        return distancef.params(this, other).call();
    }
}
