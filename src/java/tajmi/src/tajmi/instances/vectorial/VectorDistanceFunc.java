
package tajmi.instances.vectorial;

import java.util.ArrayList;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;
import tajmi.functional.interfaces.Fun;
import tajmi.interfaces.DistanceFunc;

/**
 * d(v1,v2) = √(∑ (v1_i - v2_i)^2)
 * @author badi
 */
public class VectorDistanceFunc implements DistanceFunc<Vector> {

    Vector first, second;

    public VectorDistanceFunc params(Vector first, Vector second) {
        assert first.size() == second.size() : "Vector size mismatch: " + first.size() + " <-> " + second.size();

        this.first = first;
        this.second = second;

        return this;
    }

    public Double call () {
        List<Tuple2> zipped = Util.zip(first, second);
        List<Double> intermediate = new ArrayList<Double>(zipped.size());

        for(Tuple2<Double,Double> tup : zipped){
            intermediate.add( Math.pow( tup._1() - tup._2(), 2) );
        }

        // reduce intermediate
        double result = 0;
        for(Double acc : intermediate)
            result += acc;

        return Math.sqrt(result);
    }

    public Fun copy() {
        return new VectorDistanceFunc().curry(first).curry(second);
    }

    public Fun curry(Object arg) {
        if (first == null)
            first = (Vector) arg;
        else if (second == null)
            second = (Vector) arg;

        return this;
    }

}
