/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import tajmi.data.clusterable.num.Vector;

/**
 *
 * @author badi
 */
public class tajmi_data_clusterable_num_Vector {

    public static void main(String[] args) {
        sum();
    }

    public static void sum() {
        Vector v1 = new Vector(10),
                v2 = new Vector(10);
        for (int i = 0; i < 10; i++) {
            v1.add((double) i);
            v2.add((double) i);
        }

        System.out.println("v1.sum(v2): " + v1.sum(v2));

    }
}
