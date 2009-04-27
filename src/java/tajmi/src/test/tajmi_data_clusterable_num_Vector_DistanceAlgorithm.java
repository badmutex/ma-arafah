/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import tajmi.data.clusterable.num.Vector;
import tajmi.data.clusterable.num.Vector_DistanceAlgorithm;

/**
 *
 * @author badi
 */
public class tajmi_data_clusterable_num_Vector_DistanceAlgorithm {

    public static void main(String[] args){
        distance();
    }

    public static void distance(){
        Vector v1 = new Vector(10),
                v2 = new Vector(10);
        for (int i = 0; i < 10; i++) {
            v1.add((double) i);
            v2.add((double) i + 1);
        }

        System.out.println("distance(v1,v2): " + new Vector_DistanceAlgorithm().distance(v1, v2));
    }
}
