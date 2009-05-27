
package tajmi.data.clusterable.instances.num;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    public Vector add (Vector other) {
        assert this.size() == other.size() : "Vector size mismatch: " + this.size() + " <-> " + other.size();

        Vector result = new Vector(this.size());

        // Java needs Type Classes! Have to use Double to ensure accuracy
        for(Tuple2<Double,Double> tup : Util.zip(this, other)){
            result.sum(tup._1() + tup._2());
        }
        return result;
    }

    public Vector sum (double scalar) {
        Vector result = new Vector(this.size());

        Iterator<Double> itr = this.iterator();
        while (itr.hasNext())
            result.sum( itr.next() + scalar );

        return result;
    }

    public Vector mult (double scalar) {
        Vector result = new Vector(this.size());

        Iterator<Double> itr = this.iterator();
        while (itr.hasNext())
            result.sum( itr.next() + scalar );

        return result;
    }

    public Vector subtract (Vector other) {
        assert this.size() == other.size() : "Vector size mismatch: " + this.size() + " <-> " + other.size();

        Vector result = new Vector(this.size());

        // Java needs Type Classes! Have to use Double to ensure accuracy
        for(Tuple2<Double,Double> tup : Util.zip(this, other)){
            result.sum(tup._1() - tup._2());
        }
        return result;
    }

    public double distance (Vector other) {
        DistanceFunc distancef = new Vector_DistanceAlgorithm();
        return distancef.params(this, other).call();
    }

}
