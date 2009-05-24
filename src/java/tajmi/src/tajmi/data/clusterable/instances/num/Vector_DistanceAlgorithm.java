
package tajmi.data.clusterable.instances.num;

import java.util.ArrayList;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;
import tajmi.data.clusterable.interfaces.DistanceFunc;

/**
 *
 * @author badi
 */
public class Vector_DistanceAlgorithm implements DistanceFunc<Vector> {

    Vector me, you;

    public Vector_DistanceAlgorithm params(Vector me, Vector you) {
        this.me = me;
        this.you = you;

        return this;
    }

    public Double call () {
        List<Tuple2> zipped = Util.zip(me, you);
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

}
