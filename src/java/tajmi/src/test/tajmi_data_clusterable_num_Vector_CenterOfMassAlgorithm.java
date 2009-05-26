/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import tajmi.data.clusterable.instances.num.Vector;
import tajmi.data.clusterable.instances.num.Vector_CenterOfMassAlgorithm;

/**
 *
 * @author badi
 */
public class tajmi_data_clusterable_num_Vector_CenterOfMassAlgorithm {

    public static void main(String[] args) {
        center();
    }

    public static void center() {
        Vector v1 = new Vector(10),
                v2 = new Vector(10);
        for (int i = 0; i < 10; i++) {
            v1.add((double) i);
            v2.add((double) i + 10);
        }

        List<Vector> vs = new ArrayList(2);
        vs.add(v1);
        vs.add(v2);
        System.out.println("Center(v1,v2): " + new Vector_CenterOfMassAlgorithm().params(vs));

    }
}
