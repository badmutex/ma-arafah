/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author badi
 */
public class Foo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        eq();
    }

    public static void copy() {
        List l1 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            l1.add(i);
        }
        System.out.println("L_1: " + l1);

        List l2 = l1;
        System.out.println("L_2: " + l2);

        l1 = null;
        System.out.println("\nL_1: " + l1);
        System.out.println("L_2: " + l2);
    }

    public static void eq() {
        List l1 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            l1.add(i);
        }

        List l2 = new LinkedList();
        for (int i = 0; i < 10; i++)
            l1.add(i);

        System.out.println(l1.equals(l2));
    }
}
