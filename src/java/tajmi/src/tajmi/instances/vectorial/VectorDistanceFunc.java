
package tajmi.instances.vectorial;

import java.util.ArrayList;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;
import tajmi.abstracts.DistanceFunc;

/**
 * d(v1,v2) = √(∑ (v1_i - v2_i)^2)
 * @author badi
 */
public class VectorDistanceFunc extends DistanceFunc<Vector> {

    public Double call () {
        Vector first = getFirst(),
                second = getSecond();

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
}
