/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tajmi.data.clusterable.num;

import java.util.Iterator;
import tajmi.data.clusterable.CenterOfMassAlgorithm;

/**
 *
 * @author badi
 */
public class Vector_CenterOfMassAlgorithm implements CenterOfMassAlgorithm<Vector> {

    /**
     * c_i = 1/|C_i| SUM_{x in C_i} x
     * @param cluster
     * @return
     */
    public Vector center(Iterable<Vector> cluster) {
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
