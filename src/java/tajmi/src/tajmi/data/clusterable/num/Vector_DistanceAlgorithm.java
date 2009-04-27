/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tajmi.data.clusterable.num;

import java.util.ArrayList;
import java.util.List;
import scala.Tuple2;
import tajmi.Util;
import tajmi.data.clusterable.DistanceAlgorithm;

/**
 *
 * @author badi
 */
public class Vector_DistanceAlgorithm implements DistanceAlgorithm<Vector> {

    /**
     * Returns the Euclidean distance between two vectors
     * @param me the first vector
     * @param you the second vector
     * @return the distance
     */
    public double distance(Vector me, Vector you) {

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
